package com.arun.bar.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.arun.bar.InvitePosterActivity;
import com.arun.bar.InvitePriceActivity;
import com.arun.bar.R;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    @Override
    protected int preparedCreate(Bundle savedInstanceState) {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        TextView text_invite_member = findViewById(R.id.text_invite_member);
        text_invite_member.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void openInviteMember() {
        InvitePriceActivity.jumpToInvitePrice(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_invite_member:
                openInviteMember();
                break;
        }
    }
}
