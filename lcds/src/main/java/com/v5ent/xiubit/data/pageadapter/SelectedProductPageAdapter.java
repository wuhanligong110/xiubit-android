package com.v5ent.xiubit.data.pageadapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.toobei.common.data.MyBasePagerAdapter;
import com.v5ent.xiubit.fragment.SelectedProductedFragment;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/4
 */

public class SelectedProductPageAdapter extends MyBasePagerAdapter {
    
    public SelectedProductPageAdapter(Activity activity, FragmentManager fm) {
        super(activity, fm, null);
        Bundle bundle1 = new Bundle();
        bundle1.putString("type","1");
        SelectedProductedFragment fragment1 = new SelectedProductedFragment();
        fragment1.setArguments(bundle1);
        fragments[0] = fragment1;
        
        
        Bundle bundle2 = new Bundle();
        bundle2.putString("type","2");
        SelectedProductedFragment fragment2 = new SelectedProductedFragment();
        fragment2.setArguments(bundle2);
        fragments[1] = fragment2;

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
