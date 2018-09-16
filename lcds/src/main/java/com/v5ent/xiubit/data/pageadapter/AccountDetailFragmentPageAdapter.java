package com.v5ent.xiubit.data.pageadapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.toobei.common.data.MyBasePagerAdapter;
import com.v5ent.xiubit.entity.IncomeDetailType;
import com.v5ent.xiubit.fragment.AccountDetailFragment;

import java.util.List;

/**
 * Created by hasee-pc on 2017/2/13.
 */

public class AccountDetailFragmentPageAdapter extends MyBasePagerAdapter<IncomeDetailType> {


    public AccountDetailFragmentPageAdapter(Activity activity, FragmentManager fm, List<IncomeDetailType> datas) {
        super(activity, fm, datas);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            AccountDetailFragment fragment = new AccountDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("DateType", datas.get(position).getType());
            fragment.setArguments(bundle);
            fragments[position] = fragment;

        }
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).getTypeName();
    }

    @Override
    public void refresh(List<IncomeDetailType> datas) {
        this.datas = datas;
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                AccountDetailFragment frag = (AccountDetailFragment) fragment;
                frag.refresh();
            }
        }
    }
}
