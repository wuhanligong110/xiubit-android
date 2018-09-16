package com.toobei.tb.adapter;

import android.view.View;

import org.xsl781.data.BasePagerAdapter;

import java.util.List;

/**
 * 用于SlidingTabs可动态设置tabTitles的pagerAdapter
 * Created by hasee-pc on 2017/1/8.
 */

public class SlidingTabsTitlesPageAdapter extends BasePagerAdapter {
    String[] titles;

    public SlidingTabsTitlesPageAdapter(List<View> views, String[] titles) {
        super(views);
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
