package com.arun.bar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arun.bar.R;
import com.arun.bar.bean.PostItem;

import java.lang.ref.WeakReference;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter {

    private WeakReference<Context> weakReference;
    private List<PostItem> postList;

    public PostAdapter(Context context, List<PostItem> postList) {
        weakReference = new WeakReference<>(context);
        this.postList = postList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(weakReference.get()).inflate(R.layout.layout_post_list_item, parent, false);
        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostHolder) {
            PostHolder postHolder = (PostHolder) holder;
            postHolder.setData(postList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    private static class PostHolder extends RecyclerView.ViewHolder {
        private TextView post_title, post_time, post_author, post_content;
        private LinearLayout imgLayout;

        private PostHolder(View itemView) {
            super(itemView);
            post_title = itemView.findViewById(R.id.post_title);
            post_time = itemView.findViewById(R.id.post_time);
            post_author = itemView.findViewById(R.id.post_author);
            post_content = itemView.findViewById(R.id.post_content);
            imgLayout = itemView.findViewById(R.id.imgLayout);
        }

        private void setData(PostItem postBodyBean) {
            if (postBodyBean != null) {
                post_title.setText(postBodyBean.post_title);
                post_content.setText(postBodyBean.main_content);
                post_time.setText(postBodyBean.post_time);
                post_author.setText(postBodyBean.post_owner);
                /*if (postBodyBean.img_list != null && postBodyBean.img_list.size() > 0) {
                    int loop = postBodyBean.img_list.size() > 3 ? 3 : postBodyBean.img_list.size();
                    for (int i = 0; i < loop; i++) {
                        //ImageView img = new ImageView()
                    }
                }*/
            }
        }
    }
}
