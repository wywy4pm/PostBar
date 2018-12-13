/*
 * Copyright (C) 2014 nohana, Inc.
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arun.bar.matisse.internal.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import com.arun.bar.matisse.internal.entity.Album;
import com.arun.bar.matisse.internal.entity.Item;
import com.arun.bar.matisse.internal.utils.MediaStoreCompat;

public class AlbumPhotoLoader extends CursorLoader {
    private static final String TAG = AlbumPhotoLoader.class.getSimpleName();
    private static final String[] PROJECTION = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE, MediaStore.Images.Media.SIZE};
    private static final String ORDER_BY = MediaStore.Images.Media._ID + " DESC";
    private final boolean mEnableCapture;

    private AlbumPhotoLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs,
                             String sortOrder, boolean capture) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        mEnableCapture = capture;
    }

    public static CursorLoader newInstance(Context context, Album album, boolean capture) {
        if (album.isAll()) {
            return new AlbumPhotoLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION, null, null,
                    ORDER_BY, capture);
        } else {
            return new AlbumPhotoLoader(context,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    PROJECTION,
                    MediaStore.Images.Media.BUCKET_ID + " = ?",
                    new String[]{album.getId()},
                    ORDER_BY,
                    false);
        }
    }

    @Override
    public Cursor loadInBackground() {
        Cursor result = super.loadInBackground();
        if (!mEnableCapture || !MediaStoreCompat.hasCameraFeature(getContext())) {
            return result;
        }
        MatrixCursor dummy = new MatrixCursor(PROJECTION);
        dummy.addRow(new Object[]{Item.ITEM_ID_CAPTURE, Item.ITEM_DISPLAY_NAME_CAPTURE, "", 0});
        return new MergeCursor(new Cursor[]{dummy, result});
    }

    @Override
    public void onContentChanged() {
        // FIXME a dirty way to fix loading multiple times
    }
}
