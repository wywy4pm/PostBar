package com.arun.bar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arun.bar.PostDetailActivity;
import com.arun.bar.R;
import com.arun.bar.bean.PostItem;
import com.arun.bar.utils.DensityUtils;
import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends BaseRecyclerAdapter<PostItem> {
    private int imgSize;
    private int imgEdge;

    public PostAdapter(Context context, List<PostItem> postList) {
        super(context, postList);
        int screenWidth = DensityUtils.getScreenWidth(contexts.get());
        int leftRightEdge = DensityUtils.dp2px(contexts.get(), 15);
        imgEdge = DensityUtils.dp2px(contexts.get(), 10);
        imgSize = (screenWidth - 2 * leftRightEdge - 2 * imgEdge) / 3;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexts.get()).inflate(R.layout.layout_post_list_item, parent, false);
        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostHolder) {
            PostHolder postHolder = (PostHolder) holder;
            postHolder.setData(contexts.get(), list.get(position), imgSize, imgEdge);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private static class PostHolder extends RecyclerView.ViewHolder {
        private TextView post_title, post_time, post_author, post_content;
        private LinearLayout imgLayout;
        private View itemView;

        private PostHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            post_title = itemView.findViewById(R.id.post_title);
            post_time = itemView.findViewById(R.id.post_time);
            post_author = itemView.findViewById(R.id.post_author);
            post_content = itemView.findViewById(R.id.post_content);
            imgLayout = itemView.findViewById(R.id.imgLayout);
        }

        private void setData(final Context context, final PostItem postItem, int imgSize, int imgEdge) {
            if (postItem != null) {
                post_title.setText(postItem.post_title);
                post_content.setText(postItem.main_content);
                post_time.setText(postItem.post_time);
                post_author.setText(postItem.post_owner_nick_name);
                if (!TextUtils.isEmpty(postItem.imgs)) {
                    String[] images = postItem.imgs.split(",");
                    if (images.length > 0) {
                        imgLayout.setVisibility(View.VISIBLE);
                        imgLayout.removeAllViews();
                        int loop = images.length > 3 ? 3 : images.length;
                        for (int i = 0; i < loop; i++) {
                            String imgUrl = images[i];
                            ImageView imageView = new ImageView(context);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imgSize, imgSize);
                            if (i < loop - 1) {
                                params.setMargins(0, 0, imgEdge, 0);
                            } else {
                                params.setMargins(0, 0, 0, 0);
                            }
                            imageView.setLayoutParams(params);
                            Glide.with(context).load(imgUrl).centerCrop().into(imageView);
                            imgLayout.addView(imageView);
                        }
                    } else {
                        imgLayout.setVisibility(View.GONE);
                    }
                } else {
                    imgLayout.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PostDetailActivity.jumpToPostDetail(context, postItem);
                    }
                });
            }
        }
    }
}
