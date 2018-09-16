package org.xsl781.data;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class BaseFragmentPagerAdapter<T> extends FragmentPagerAdapter {
    List<T> list;
    Fragment[] fragments;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<T> list) {
        super(fm);
        this.list = list;
        fragments = new Fragment[list.size()];
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
