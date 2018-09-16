package com.v5ent.xiubit.data;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.toobei.common.data.MyBasePagerAdapter;
import com.v5ent.xiubit.entity.ProfixType;
import com.v5ent.xiubit.fragment.FragmentMyIncomeDetail;

import java.util.List;

/**
 * Created by qingTX007 on 2016/9/20.
 */
public class IncomeDetailPagerAdapter extends MyBasePagerAdapter<ProfixType> {

    private final String date;

    public IncomeDetailPagerAdapter(Activity activity, FragmentManager fm, List<ProfixType> datas, String date) {
        super(activity, fm, datas);
        this.date = date;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            FragmentMyIncomeDetail fragment = new FragmentMyIncomeDetail();
            Bundle bundle = new Bundle();
            bundle.putString("profixType", datas.get(position).getProfixType());
            bundle.putString("monthDate",date);
            fragment.setArguments(bundle);
            fragments[position] = fragment;
        }
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).getProfixTypeName();
    }

    @Override
    public void refresh(List<ProfixType> datas) {
        this.datas = datas;
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                FragmentMyIncomeDetail frag = (FragmentMyIncomeDetail) fragment;
                frag.refresh();
            }
        }
    }
}
