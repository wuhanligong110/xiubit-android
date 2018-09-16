package com.toobei.tb.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.toobei.common.data.MyBasePagerAdapter;
import com.toobei.common.entity.ProductClassifyStatisticsDetail;
import com.toobei.tb.R;
import com.toobei.tb.fragment.FragmentMyCfpRecommendOrg;
import com.toobei.tb.fragment.FragmentMyCfpRecommendProduct;

/**
 * 公司: tophlc
 * 类说明: 我的理财师adapter
 *
 * @author qingyechen
 * @time 2016/12/30 0030 下午 4:56
 */
public class MycfpPagerAdapter extends MyBasePagerAdapter<ProductClassifyStatisticsDetail> {

    public MycfpPagerAdapter(Activity activity, FragmentManager fm) {
        super(activity, fm, null);
        fragments=new Fragment[2];
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {

        if (position==0&&fragments[0] == null) {
            fragments[position] = new FragmentMyCfpRecommendProduct();
        }
        if (position==1&&fragments[1] == null) {
            fragments[position] = new FragmentMyCfpRecommendOrg();
        }

        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int[] titles = {R.string.mycfp_tab01, R.string.mycfp_tab02};
        return activity.getString(titles[position]);
    }
}
