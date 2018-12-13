package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

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
    private TextView main_btn;
    private TextView mine_btn;

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
        main_btn = findViewById(R.id.main_btn);
        mine_btn = findViewById(R.id.mine_btn);
        MainFragment mainFragment = MainFragment.newInstance(barName);
        MineFragment mineFragment = new MineFragment();
        fragmentList.add(mainFragment);
        fragmentList.add(mineFragment);

        FragmentManager fm = getSupportFragmentManager();
        MainAdapter mainAdapter = new MainAdapter(fm, fragmentList);
        viewPager.setAdapter(mainAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == Constant.TAB_INDEX_MAIN) {
                    main_btn.setSelected(true);
                    mine_btn.setTextColor(getResources().getColor(R.color.charcoalgrey));
                    main_btn.setTextColor(getResources().getColor(R.color.pickerview_optionbtn_nor));
                } else if (position == Constant.TAB_INDEX_MINE) {
                    mine_btn.setSelected(true);
                    mine_btn.setTextColor(getResources().getColor(R.color.pickerview_optionbtn_nor));
                    main_btn.setTextColor(getResources().getColor(R.color.charcoalgrey));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setCurrentItem(Constant.TAB_INDEX_MAIN);
        main_btn.setSelected(true);
        mine_btn.setTextColor(getResources().getColor(R.color.charcoalgrey));
        main_btn.setTextColor(getResources().getColor(R.color.pickerview_optionbtn_nor));
    }

    public void showMain(View view) {
        viewPager.setCurrentItem(Constant.TAB_INDEX_MAIN);
    }

    public void showMine(View view) {
        viewPager.setCurrentItem(Constant.TAB_INDEX_MINE);
    }

    public void showPost(View view) {
        PostActivity.jumpToSendPost(this, barName);
    }
}
