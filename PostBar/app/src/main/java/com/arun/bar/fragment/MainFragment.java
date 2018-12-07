package com.arun.bar.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.arun.bar.R;
import com.arun.bar.adapter.PostAdapter;
import com.arun.bar.bean.PostItem;
import com.arun.bar.common.Constant;
import com.arun.bar.presenter.MainPresenter;
import com.arun.bar.view.CommonView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment implements CommonView<List<PostItem>> {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<PostItem> postList = new ArrayList<>();
    private MainPresenter mainPresenter;
    private String barName;
    private int currentPage;
    private static final int PAGE_SIZE = 10;
    private boolean pagination;

    public static MainFragment newInstance(String barName) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_BAR_NAME, barName);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    protected int preparedCreate(Bundle savedInstanceState) {
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
        if (!TextUtils.isEmpty(userId)) {
            mainPresenter = new MainPresenter();
            mainPresenter.attachView(this);
            pagination = false;
            getData();
        }
    }

    private void getMoreData() {
        Log.d("TAG", "getMoreData");
        currentPage++;
        pagination = true;
        getData();
    }

    private void getData() {
        if (mainPresenter != null) {
            mainPresenter.getPostList(userId, currentPage, PAGE_SIZE, true);
        }
    }

    @Override
    public void refresh(List<PostItem> data) {
        if (data != null && data.size() > 0) {
            postList.clear();
            postList.addAll(data);
            postAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshMore(List<PostItem> data) {
        if (data != null && data.size() > 0) {
            postList.addAll(data);
            postAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mainPresenter != null) {
            mainPresenter.detachView();
        }
    }
}
