/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arun.bar.matisse.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.arun.bar.MainActivity;
import com.arun.bar.PostActivity;
import com.arun.bar.R;
import com.arun.bar.matisse.internal.entity.Album;
import com.arun.bar.matisse.internal.entity.Item;
import com.arun.bar.matisse.internal.entity.SelectionSpec;
import com.arun.bar.matisse.internal.model.DevicePhotoAlbumCollection;
import com.arun.bar.matisse.internal.model.SelectedItemCollection;
import com.arun.bar.matisse.internal.ui.BasePreviewActivity;
import com.arun.bar.matisse.internal.ui.PhotoSelectionFragment;
import com.arun.bar.matisse.internal.ui.adapter.AlbumPhotosAdapter;
import com.arun.bar.matisse.internal.ui.adapter.AlbumsAdapter;
import com.arun.bar.matisse.internal.ui.widget.AlbumsSpinner;
import com.arun.bar.matisse.internal.utils.MediaStoreCompat;

import java.util.ArrayList;

/**
 * Main Activity to display albums and photos in each album and also support photo selecting
 * operations.
 */
public class MatisseActivity extends AppCompatActivity implements
        DevicePhotoAlbumCollection.DevicePhotoAlbumCallbacks, AdapterView.OnItemSelectedListener,
        PhotoSelectionFragment.SelectionProvider, View.OnClickListener,
        AlbumPhotosAdapter.CheckStateListener, AlbumPhotosAdapter.OnPhotoClickListener,
        AlbumPhotosAdapter.OnPhotoCapture {

    public static final String EXTRA_RESULT_SELECTION = "extra_result_selection";
    private static final int REQUEST_CODE_PREVIEW = 23;
    private static final int REQUEST_CODE_CAPTURE = 24;
    private final DevicePhotoAlbumCollection mAlbumCollection = new DevicePhotoAlbumCollection();
    private MediaStoreCompat mMediaStoreCompat;
    private SelectedItemCollection mSelectedCollection = new SelectedItemCollection(this);

    private AlbumsSpinner mAlbumsSpinner;
    private AlbumsAdapter mAlbumsAdapter;
    private TextView mButtonPreview;
    private TextView mButtonApply;
    private View mContainer;
    private View mEmptyView;
    public static final String KEY_BACK_MODE = "mode";
    private String backMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // programmatically set theme before super.onCreate()
        SelectionSpec spec = SelectionSpec.getInstance();
        setTheme(spec.themeId);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matisse);

        if (spec.needOrientationRestriction()) {
            setRequestedOrientation(spec.orientation);
        }

        if (spec.capture) {
            mMediaStoreCompat = new MediaStoreCompat(this);
            mMediaStoreCompat.setCaptureStrategy(spec.captureStrategy);
        }

        /*setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        mButtonPreview = (TextView) findViewById(R.id.button_preview);
        mButtonApply = (TextView) findViewById(R.id.button_apply);
        mButtonPreview.setOnClickListener(this);
        mButtonApply.setOnClickListener(this);
        mContainer = findViewById(R.id.container);
        mEmptyView = findViewById(R.id.empty_view);

        mSelectedCollection.onCreate(savedInstanceState, spec);
        updateBottomToolbar();

        mAlbumsAdapter = new AlbumsAdapter(this, null, false);
        mAlbumsSpinner = new AlbumsSpinner(this);
        mAlbumsSpinner.setOnItemSelectedListener(this);
        mAlbumsSpinner.setSelectedTextView((TextView) findViewById(R.id.selected_album));
        mAlbumsSpinner.setPopupAnchorView(findViewById(R.id.toolbar));
        mAlbumsSpinner.setAdapter(mAlbumsAdapter);
        mAlbumCollection.onCreate(this, this);
        mAlbumCollection.onRestoreInstanceState(savedInstanceState);
        mAlbumCollection.loadAlbums();

        getMode();
    }

    private void getMode() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(KEY_BACK_MODE)) {
                backMode = getIntent().getExtras().getString(KEY_BACK_MODE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSelectedCollection.onSaveInstanceState(outState);
        mAlbumCollection.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAlbumCollection.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
        back();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_PREVIEW) {
            ArrayList<Item> selected = data.getParcelableArrayListExtra(BasePreviewActivity.EXTRA_RESULT_SELECTED);
            if (data.getBooleanExtra(BasePreviewActivity.EXTRA_RESULT_APPLY, false)) {
                Intent result = new Intent();
                ArrayList<Uri> selectedUris = new ArrayList<>();
                for (Item item : selected) {
                    selectedUris.add(item.getContentUri());
                }
                result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selectedUris);
                setResult(RESULT_OK, result);
                finish();
            } else {
                mSelectedCollection.overwrite(selected);
                Fragment photoSelectionFragment = getSupportFragmentManager().findFragmentByTag(
                        PhotoSelectionFragment.class.getSimpleName());
                if (photoSelectionFragment instanceof PhotoSelectionFragment) {
                    ((PhotoSelectionFragment) photoSelectionFragment).refreshPhotoGrid();
                }
                updateBottomToolbar();
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE) {
            // Just pass the data back to previous calling Activity.
            Uri contentUri = mMediaStoreCompat.getCurrentPhotoPath();
            ArrayList<Uri> selected = new ArrayList<>();
            selected.add(contentUri);
            Intent result = new Intent();
            result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selected);
            setResult(RESULT_OK, result);
            finish();
        }
    }

    private void updateBottomToolbar() {
        int selectedCount = mSelectedCollection.count();
        if (selectedCount == 0) {
            mButtonPreview.setEnabled(false);
            mButtonApply.setText(getString(R.string.button_apply_disable));
            mButtonApply.setEnabled(false);
        } else {
            mButtonPreview.setEnabled(true);
            mButtonApply.setEnabled(true);
            mButtonApply.setText(getString(R.string.button_apply, selectedCount));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_preview) {
            /*Intent intent = new Intent(this, SelectedPreviewActivity.class);
            intent.putExtra(BasePreviewActivity.EXTRA_DEFAULT_SELECTED, (ArrayList<Item>) mSelectedCollection.asList());
            startActivityForResult(intent, REQUEST_CODE_PREVIEW);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);*/
        } else if (v.getId() == R.id.button_apply) {
            Intent result = null;
            if (PostActivity.BACK_MODE_SEND_POST.equals(backMode)) {
                result = new Intent(MatisseActivity.this, PostActivity.class);
            } else {//如果出错，跳回首页
                result = new Intent(MatisseActivity.this, MainActivity.class);
            }

            ArrayList<Uri> selectedUris = (ArrayList<Uri>) mSelectedCollection.asListOfUri();
            result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selectedUris);
            setResult(RESULT_OK, result);
            startActivity(result);
            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            finish();
        } else if (v.getId() == R.id.image_back) {
            back();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mAlbumCollection.setStateCurrentSelection(position);
        mAlbumsAdapter.getCursor().moveToPosition(position);
        Album album = Album.valueOf(mAlbumsAdapter.getCursor());
        if (album.isAll() && SelectionSpec.getInstance().capture) {
            album.addCaptureCount();
        }
        onAlbumSelected(album);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAlbumLoad(final Cursor cursor) {
        mAlbumsAdapter.swapCursor(cursor);
        // select default album.
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                cursor.moveToPosition(mAlbumCollection.getCurrentSelection());
                mAlbumsSpinner.setSelection(MatisseActivity.this,
                        mAlbumCollection.getCurrentSelection());
                Album album = Album.valueOf(cursor);
                if (album.isAll() && SelectionSpec.getInstance().capture) {
                    album.addCaptureCount();
                }
                onAlbumSelected(album);
            }
        });
    }

    @Override
    public void onAlbumReset() {
        mAlbumsAdapter.swapCursor(null);
    }

    private void onAlbumSelected(Album album) {
        if (album.isAll() && album.isEmpty()) {
            mContainer.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mContainer.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            Fragment fragment = PhotoSelectionFragment.newInstance(album);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, PhotoSelectionFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onUpdate() {
        // notify bottom toolbar that check state changed.
        updateBottomToolbar();
    }

    @Override
    public void onPhotoClick(Album album, Item item, int adapterPosition) {
        /*Intent intent = new Intent(this, AlbumPreviewActivity.class);
        intent.putExtra(AlbumPreviewActivity.EXTRA_ALBUM, album);
        intent.putExtra(AlbumPreviewActivity.EXTRA_ITEM, item);
        intent.putExtra(BasePreviewActivity.EXTRA_DEFAULT_SELECTED, (ArrayList<Item>) mSelectedCollection.asList());
        startActivityForResult(intent, REQUEST_CODE_PREVIEW);*/
    }

    @Override
    public SelectedItemCollection provideSelectedItemCollection() {
        return mSelectedCollection;
    }

    @Override
    public void capture() {
        if (mMediaStoreCompat != null) {
            mMediaStoreCompat.dispatchCaptureIntent(this, REQUEST_CODE_CAPTURE);
        }
    }

    public void back() {
        if (PostActivity.BACK_MODE_SEND_POST.equals(backMode)) {
            backToSendPost();
        }
    }

    public void backToSendPost() {
        Intent intent = new Intent(MatisseActivity.this, PostActivity.class);
        startActivity(intent);
        //overridePendingTransition(0, R.anim.slide_out_right);
    }
}
