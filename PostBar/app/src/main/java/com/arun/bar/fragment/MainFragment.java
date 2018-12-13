package com.arun.bar.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.arun.bar.R;
import com.arun.bar.adapter.PostAdapter;
import com.arun.bar.bean.PostItem;
import com.arun.bar.bean.PostListData;
import com.arun.bar.common.Constant;
import com.arun.bar.event.UpdateMainEvent;
import com.arun.bar.presenter.MainPresenter;
import com.arun.bar.utils.SharedPreferencesUtils;
import com.arun.bar.view.CommonView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment implements CommonView<PostListData> {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<PostItem> postList = new ArrayList<>();
    private MainPresenter mainPresenter;
    private String barName;
    private String bar_uuid;
    private int currentPage;
    private static final int PAGE_SIZE = 10;
    private boolean pagination;
    private int allCount;
    private int currentAllCount;

    public static MainFragment newInstance(String barName) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_BAR_NAME, barName);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    protected int preparedCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        postAdapter = new PostAdapter(getActivity(), postList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                        int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
                        int visibleCount = linearLayoutManager.getChildCount();
                        int totalCount = linearLayoutManager.getItemCount();
                        int limitLoadMore = 0;
                        if (totalCount > 2) {
                            limitLoadMore = totalCount - 2;
                        } else {
                            limitLoadMore = totalCount;
                        }
                        synchronized (MainFragment.this) {
                            if (firstVisiblePosition + visibleCount >= limitLoadMore) {
                                if (!isLoading) {
                                    getMoreData();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (getArguments() != null && getArguments().containsKey(Constant.INTENT_BAR_NAME)) {
            barName = getArguments().getString(Constant.INTENT_BAR_NAME);
        }
        if (getActivity() != null) {
            bar_uuid = SharedPreferencesUtils.getConfigString(getActivity(), SharedPreferencesUtils.KEY_USER_BAR_ID);
        }
        if (!TextUtils.isEmpty(userId)) {
            mainPresenter = new MainPresenter();
            mainPresenter.attachView(this);
            refreshData();
        }
    }

    private void refreshData() {
        allCount = 0;
        currentAllCount = 0;
        currentPage = 0;
        pagination = false;
        getData();
    }

    private void getMoreData() {
        Log.d("TAG", "getMoreData");
        currentPage++;
        pagination = true;
        getData();
    }

    private void getData() {
        if (mainPresenter != null) {
            if (allCount == 0 || currentAllCount < allCount) {
                mainPresenter.getPostList(bar_uuid, currentPage, PAGE_SIZE, pagination);
            }
        }
    }

    @Override
    public void refresh(PostListData postListData) {
        if (postListData != null) {
            allCount = postListData.total;
            if (postListData.data != null && postListData.data.size() > 0) {
                postList.clear();
                postList.addAll(postListData.data);
                currentAllCount += postListData.data.size();
                postAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void refreshMore(PostListData postListData) {
        if (postListData != null) {
            allCount = postListData.total;
            if (postListData.data != null && postListData.data.size() > 0) {
                postList.addAll(postListData.data);
                currentAllCount += postListData.data.size();
                postAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mainPresenter != null) {
            mainPresenter.detachView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateMainList(UpdateMainEvent updateMainEvent) {
        Log.d("TAG", "updateMainList");
        refreshData();
    }
}
