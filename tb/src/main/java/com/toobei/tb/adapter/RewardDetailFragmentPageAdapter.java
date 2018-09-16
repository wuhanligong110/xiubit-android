package com.toobei.tb.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.toobei.common.data.MyBasePagerAdapter;
import com.toobei.tb.entity.TabTypeBean;
import com.toobei.tb.fragment.RewardDetailFragment;

import java.util.List;

public class RewardDetailFragmentPageAdapter extends MyBasePagerAdapter<TabTypeBean> {


    public RewardDetailFragmentPageAdapter(Activity activity, FragmentManager fm, List<TabTypeBean> datas) {
        super(activity, fm, datas);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            TabTypeBean incomeDetailType = datas.get(position);
            RewardDetailFragment fragment = new RewardDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("DateType",datas.get(position).getTabType());
            fragment.setArguments(bundle);
            fragments[position] = fragment;

        }
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).getTabName();
    }
}