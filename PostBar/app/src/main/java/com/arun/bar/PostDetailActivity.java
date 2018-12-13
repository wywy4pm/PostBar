package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.arun.bar.adapter.PostDetailAdapter;
import com.arun.bar.bean.PostItem;
import com.arun.bar.bean.ReplyListData;
import com.arun.bar.bean.post.PostTypeBean;
import com.arun.bar.bean.post.PostUserBean;
import com.arun.bar.bean.post.ReplyBean;
import com.arun.bar.common.Constant;
import com.arun.bar.presenter.PostDetailPresenter;
import com.arun.bar.utils.KeyBoardUtils;
import com.arun.bar.utils.SharedPreferencesUtils;
import com.arun.bar.view.CommonView4;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends BaseActivity implements CommonView4<ReplyListData> {

    private RecyclerView recyclerView;
    private PostDetailAdapter postDetailAdapter;
    private RelativeLayout layout_add_comment;
    private List<PostTypeBean> list = new ArrayList<>();
    private PostDetailPresenter postDetailPresenter;
    private EditText edit_add_comment;
    private String post_uuid;
    private int replyCurrentPage;
    private static final int REPLY_PAGE_SIZE = 20;
    private boolean replyPagination;
    private int allReplyCount;
    private int currentAllCount;

    public static void jumpToPostDetail(Context context, PostItem postItem) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(Constant.INTENT_POST_ITEM, postItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initView();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        layout_add_comment = findViewById(R.id.layout_add_comment);
        edit_add_comment = findViewById(R.id.edit_add_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        postDetailAdapter = new PostDetailAdapter(this, list);
        recyclerView.setAdapter(postDetailAdapter);

        setBack();
        String barName = SharedPreferencesUtils.getConfigString(this, SharedPreferencesUtils.KEY_USER_BAR);
        setTitleName(barName);
    }

    private void initData() {
        postDetailPresenter = new PostDetailPresenter();
        postDetailPresenter.attachView(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(Constant.INTENT_POST_ITEM)) {
                PostItem postItem = (PostItem) getIntent().getExtras().getSerializable(Constant.INTENT_POST_ITEM);
                if (postItem != null) {
                    post_uuid = postItem.uuid;
                    formatData(postItem);
                    refreshReply();
                }
            }
        }
    }

    private void formatData(PostItem postItem) {
        if (postItem != null) {
            PostTypeBean<String> postTitle = new PostTypeBean<>();
            postTitle.type = PostDetailAdapter.DATA_TYPE_TITLE;
            postTitle.content = postItem.post_title;
            list.add(postTitle);

            PostTypeBean<PostUserBean> postAuthor = new PostTypeBean<>();
            postAuthor.type = PostDetailAdapter.DATA_TYPE_AUTHOR;
            postAuthor.content = new PostUserBean();
            postAuthor.content.post_nickname = postItem.post_owner_nick_name;
            postAuthor.content.post_time = postItem.post_time;
            list.add(postAuthor);

            PostTypeBean<String> postContent = new PostTypeBean<>();
            postContent.type = PostDetailAdapter.DATA_TYPE_CONTENT;
            postContent.content = postItem.main_content;
            list.add(postContent);

            if (!TextUtils.isEmpty(postItem.imgs)) {
                String[] images = postItem.imgs.split(",");
                if (images.length > 1) {
                    for (int i = 0; i < images.length; i++) {
                        PostTypeBean<String> postImage = new PostTypeBean<>();
                        postImage.type = PostDetailAdapter.DATA_TYPE_IMAGE;
                        postImage.content = images[i];
                        list.add(postImage);
                    }
                }
            }
            postDetailAdapter.notifyDataSetChanged();
        }
    }

    private void refreshReply() {
        replyCurrentPage = 0;
        replyPagination = false;
        allReplyCount = 0;
        currentAllCount = 0;
        getReplyData();
    }

    private void getReplyData() {
        if (postDetailPresenter != null) {
            if (allReplyCount == 0 || currentAllCount < allReplyCount) {
                postDetailPresenter.getReplyList(post_uuid, replyCurrentPage, REPLY_PAGE_SIZE, replyPagination);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (postDetailPresenter != null) {
            postDetailPresenter.detachView();
        }
    }

    private void formatReplyData(boolean isFirst, List<ReplyBean> replyList) {
        if (replyList != null && replyList.size() > 0) {
            if (isFirst) {
                PostTypeBean<String> replyTitleBean = new PostTypeBean<>();
                replyTitleBean.type = PostDetailAdapter.DATA_TYPE_REPLY_TITLE;
                list.add(replyTitleBean);
            }
            for (int i = 0; i < replyList.size(); i++) {
                PostTypeBean<ReplyBean> replyBean = new PostTypeBean<>();
                replyBean.type = PostDetailAdapter.DATA_TYPE_REPLY_CONTENT;
                replyBean.content = replyList.get(i);
                list.add(replyBean);
            }
        }
    }

    private void clearReplyData() {
        if (list != null && list.size() > 0) {
            List<PostTypeBean> replyTempList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                PostTypeBean bean = list.get(i);
                if (PostDetailAdapter.DATA_TYPE_REPLY_TITLE.equals(bean.type)
                        || PostDetailAdapter.DATA_TYPE_REPLY_CONTENT.equals(bean.type)) {
                    replyTempList.add(bean);
                }
            }
            if (replyTempList.size() > 0) {
                list.removeAll(replyTempList);
            }
        }
    }

    @Override
    public void refresh(ReplyListData replyListData) {
        if (replyListData != null) {
            clearReplyData();
            allReplyCount = replyListData.total;
            if (replyListData.data != null && replyListData.data.size() > 0) {
                formatReplyData(true, replyListData.data);
                currentAllCount += replyListData.data.size();
                postDetailAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void refreshMore(ReplyListData replyListData) {
        if (replyListData != null) {
            allReplyCount = replyListData.total;
            if (replyListData.data != null && replyListData.data.size() > 0) {
                formatReplyData(false, replyListData.data);
                currentAllCount += replyListData.data.size();
                postDetailAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void refresh(int type, Object data) {
        if (type == PostDetailPresenter.TYPE_REPLY_ADD) {
            showToast(R.string.reply_success);
            refreshReply();
        }
    }

    public void sendComment(View view) {
        if (!TextUtils.isEmpty(edit_add_comment.getText())) {
            String replyContent = edit_add_comment.getText().toString();
            if (postDetailPresenter != null) {
                postDetailPresenter.replyAdd(replyContent, post_uuid, getUserId());
            }
            //发送成功后关闭软键盘
            KeyBoardUtils.hideKeyBoard(this, edit_add_comment);
            edit_add_comment.getText().clear();
        } else {
            showToast(R.string.reply_empty_tips);
        }
    }
}
