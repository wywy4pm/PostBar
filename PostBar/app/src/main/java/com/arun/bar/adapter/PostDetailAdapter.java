package com.arun.bar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arun.bar.R;
import com.arun.bar.bean.post.PostTypeBean;
import com.arun.bar.bean.post.PostUserBean;
import com.arun.bar.bean.post.ReplyBean;
import com.arun.bar.utils.DensityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

public class PostDetailAdapter extends BaseRecyclerAdapter<PostTypeBean> {
    private static final int VIEW_TYPE_TITLE = 1;
    private static final int VIEW_TYPE_AUTHOR = 2;
    private static final int VIEW_TYPE_CONTENT = 3;
    private static final int VIEW_TYPE_IMAGE = 4;
    private static final int VIEW_TYPE_REPLY_TITLE = 5;
    private static final int VIEW_TYPE_REPLY_CONTENT = 6;

    public static final String DATA_TYPE_TITLE = "post_title";
    public static final String DATA_TYPE_AUTHOR = "post_author";
    public static final String DATA_TYPE_CONTENT = "post_content";
    public static final String DATA_TYPE_IMAGE = "post_image";
    public static final String DATA_TYPE_REPLY_TITLE = "reply_title";
    public static final String DATA_TYPE_REPLY_CONTENT = "reply_content";

    private int imageSize;

    public PostDetailAdapter(Context context, List<PostTypeBean> list) {
        super(context, list);
        int screenWidth = DensityUtils.getScreenWidth(context);
        imageSize = screenWidth - 2 * DensityUtils.dp2px(context, 15);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TITLE) {
            View itemView = LayoutInflater.from(contexts.get()).inflate(R.layout.layout_post_title, parent, false);
            return new PostTitleHolder(itemView);
        } else if (viewType == VIEW_TYPE_AUTHOR) {
            View itemView = LayoutInflater.from(contexts.get()).inflate(R.layout.layout_post_author, parent, false);
            return new PostAuthorHolder(itemView);
        } else if (viewType == VIEW_TYPE_CONTENT) {
            View itemView = LayoutInflater.from(contexts.get()).inflate(R.layout.layout_post_content, parent, false);
            return new PostContentHolder(itemView);
        } else if (viewType == VIEW_TYPE_IMAGE) {
            View itemView = LayoutInflater.from(contexts.get()).inflate(R.layout.layout_post_image, parent, false);
            return new PostImageHolder(itemView);
        } else if (viewType == VIEW_TYPE_REPLY_TITLE) {
            View itemView = LayoutInflater.from(contexts.get()).inflate(R.layout.layout_comment_title, parent, false);
            return new ReplyTitleHolder(itemView);
        } else if (viewType == VIEW_TYPE_REPLY_CONTENT) {
            View itemView = LayoutInflater.from(contexts.get()).inflate(R.layout.layout_comment_item, parent, false);
            return new ReplyContentHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostTitleHolder) {
            ((PostTitleHolder) holder).setData(getItem(position));
        } else if (holder instanceof PostAuthorHolder) {
            ((PostAuthorHolder) holder).setData(getItem(position));
        } else if (holder instanceof PostContentHolder) {
            ((PostContentHolder) holder).setData(getItem(position));
        } else if (holder instanceof PostImageHolder) {
            ((PostImageHolder) holder).setData(contexts.get(), getItem(position), imageSize);
        } else if (holder instanceof ReplyContentHolder) {
            ((ReplyContentHolder) holder).setData(getItem(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        PostTypeBean bean = getItem(position);
        if (bean != null && !TextUtils.isEmpty(bean.type)) {
            if (DATA_TYPE_TITLE.equals(bean.type)) {
                type = VIEW_TYPE_TITLE;
            } else if (DATA_TYPE_AUTHOR.equals(bean.type)) {
                type = VIEW_TYPE_AUTHOR;
            } else if (DATA_TYPE_CONTENT.equals(bean.type)) {
                type = VIEW_TYPE_CONTENT;
            } else if (DATA_TYPE_IMAGE.equals(bean.type)) {
                type = VIEW_TYPE_IMAGE;
            } else if (DATA_TYPE_REPLY_TITLE.equals(bean.type)) {
                type = VIEW_TYPE_REPLY_TITLE;
            } else if (DATA_TYPE_REPLY_CONTENT.equals(bean.type)) {
                type = VIEW_TYPE_REPLY_CONTENT;
            }
        }
        return type;
    }

    private static class PostTitleHolder extends RecyclerView.ViewHolder {
        private TextView post_title;

        private PostTitleHolder(View itemView) {
            super(itemView);
            post_title = itemView.findViewById(R.id.post_title);
        }

        private void setData(PostTypeBean<String> bean) {
            if (bean != null) {
                post_title.setText(bean.content);
            }
        }
    }

    private static class PostAuthorHolder extends RecyclerView.ViewHolder {
        private TextView post_time, post_author;

        private PostAuthorHolder(View itemView) {
            super(itemView);
            post_time = itemView.findViewById(R.id.post_time);
            post_author = itemView.findViewById(R.id.post_author);
        }

        private void setData(PostTypeBean<PostUserBean> bean) {
            if (bean != null && bean.content != null) {
                post_time.setText(bean.content.post_time);
                post_author.setText(bean.content.post_nickname);
            }
        }
    }

    private static class PostContentHolder extends RecyclerView.ViewHolder {
        private TextView post_content;

        private PostContentHolder(View itemView) {
            super(itemView);
            post_content = itemView.findViewById(R.id.post_content);
        }

        private void setData(PostTypeBean<String> bean) {
            if (bean != null) {
                post_content.setText(bean.content);
            }
        }
    }

    private static class PostImageHolder extends RecyclerView.ViewHolder {
        private ImageView post_img;

        private PostImageHolder(View itemView) {
            super(itemView);
            post_img = itemView.findViewById(R.id.post_img);
        }

        private void setData(Context context, PostTypeBean<String> bean, int imageSize) {
            if (bean != null) {
                /*if (post_img.getLayoutParams() instanceof RecyclerView.LayoutParams) {
                    ((RecyclerView.LayoutParams) post_img.getLayoutParams()).height = imageSize;
                }*/
                Glide.with(context).load(bean.content).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        /*int width = resource.getWidth();
                        int height = resource.getHeight();
                        ((RecyclerView.LayoutParams) post_img.getLayoutParams()).width = width;
                        ((RecyclerView.LayoutParams) post_img.getLayoutParams()).height = height;*/
                        post_img.setImageBitmap(resource);
                    }
                });
            }
        }
    }

    private static class ReplyTitleHolder extends RecyclerView.ViewHolder {
        private ReplyTitleHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ReplyContentHolder extends RecyclerView.ViewHolder {
        private TextView reply_user, reply_time, reply_content;

        private ReplyContentHolder(View itemView) {
            super(itemView);
            reply_user = itemView.findViewById(R.id.reply_user);
            reply_time = itemView.findViewById(R.id.reply_time);
            reply_content = itemView.findViewById(R.id.reply_content);
        }

        private void setData(PostTypeBean<ReplyBean> item) {
            if (item != null && item.content != null) {
                reply_user.setText(item.content.user_nick_name+"：");
                reply_time.setText(item.content.build_date);
                reply_content.setText(item.content.main_content);
            }
        }
    }
}
