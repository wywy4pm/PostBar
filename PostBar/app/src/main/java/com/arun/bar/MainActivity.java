package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.arun.bar.adapter.MainAdapter;
import com.arun.bar.common.Constant;
import com.arun.bar.fragment.MainFragment;
import com.arun.bar.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String barName;

    public static void jumpToMain(Context context, String barName) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constant.INTENT_BAR_NAME, barName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(Constant.INTENT_BAR_NAME)) {
                barName = getIntent().getExtras().getString(Constant.INTENT_BAR_NAME);
            }
        }
    }

    public void initView() {
        setTitleName(barName);
        viewPager = findViewById(R.id.viewPager);
        MainFragment mainFragment = MainFragment.newInstance(barName);
        MineFragment mineFragment = new MineFragment();
        fragmentList.add(mainFragment);
        fragmentList.add(mineFragment);

        FragmentManager fm = getSupportFragmentManager();
        MainAdapter mainAdapter = new MainAdapter(fm, fragmentList);
        viewPager.setAdapter(mainAdapter);
        viewPager.setCurrentItem(Constant.TAB_INDEX_MAIN);
    }

    public void showMain(View view) {
        viewPager.setCurrentItem(Constant.TAB_INDEX_MAIN);
    }

    public void showMine(View view) {
        viewPager.setCurrentItem(Constant.TAB_INDEX_MINE);
    }

    public void showPost(View view) {

    }
}
