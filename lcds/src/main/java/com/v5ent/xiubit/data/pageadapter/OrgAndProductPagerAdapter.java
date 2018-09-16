package com.v5ent.xiubit.data.pageadapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.toobei.common.data.MyBasePagerAdapter;
import com.v5ent.xiubit.fragment.APartFragment;
import com.v5ent.xiubit.fragment.FundListFragment;
import com.v5ent.xiubit.fragment.InsuranceFragment;
import com.v5ent.xiubit.fragment.NetLoanFragment;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/21
 */

public class OrgAndProductPagerAdapter extends MyBasePagerAdapter<Object> {

    private FragmentManager fm;

    public OrgAndProductPagerAdapter(Activity activity, FragmentManager fm) {
        super(activity, fm, null);
        this.fm = fm;
//        fragments[0] = new FragmentAllOrg();
//        fragments[1] = new FragmentProductSortList();
        fragments[0] = new NetLoanFragment();
        fragments[1] = new APartFragment();
//        fragments[2] = new FundListFragment();
//        fragments[3] = new InsuranceFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

  
    
    
}
