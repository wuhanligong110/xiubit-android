package com.toobei.tb.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.toobei.common.data.MyBasePagerAdapter;
import com.toobei.common.entity.ProductClassifyStatisticsDetail;
import com.toobei.tb.fragment.FragmentProductClassifyDetail;

import java.util.List;

/**
 * Created by qingTX007 on 2016/9/20.
 */
public class ProductsPagerAdapter extends MyBasePagerAdapter<ProductClassifyStatisticsDetail> {

    public ProductsPagerAdapter(Activity activity, FragmentManager fm, List<ProductClassifyStatisticsDetail> datas) {
        super(activity, fm, datas);
    }

    @Override
    public Fragment getItem(int position) {

        if (fragments[position] == null) {
            ProductClassifyStatisticsDetail proClassifyDetail = datas.get(position);
            fragments[position] = new FragmentProductClassifyDetail(proClassifyDetail);
        }
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ProductClassifyStatisticsDetail proClassifyDetail = (ProductClassifyStatisticsDetail) datas.get(position);
        String cateName = proClassifyDetail.getCateName();
        if (cateName.endsWith("产品")) {
            cateName = cateName.substring(0, cateName.length()  - "产品".length()); //去掉产品俩字
        }
        return cateName;
    }
}
