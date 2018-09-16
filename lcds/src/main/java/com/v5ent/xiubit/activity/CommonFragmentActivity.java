package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.fragment.FragmentProductSortList;
import com.v5ent.xiubit.fragment.RedPacketFragment;
import com.v5ent.xiubit.util.C.FragmentTag;

import static com.v5ent.xiubit.util.C.FragmentTag.KEY_TAG;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/26
 */

public class CommonFragmentActivity extends MyTitleBaseActivity {

    private int fragmentType;
    private String topTitle = "";
    private Bundle mParamsBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentType = getIntent().getIntExtra(KEY_TAG,-1);
        topTitle = getIntent().getStringExtra(FragmentTag.KEY_TOP_TITLE);
        mParamsBundle = getIntent().getBundleExtra(FragmentTag.KEY_PARAMS_BUNDLE);
        super.onCreate(savedInstanceState);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        Fragment fragment = getFragment();
        if (mParamsBundle != null){
            fragment.setArguments(mParamsBundle);
        }
        initToptitle();
        if (fragment != null) {
            trans.add(R.id.fragmentFl, fragment);
        }
        trans.commit();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_fragment_common;
    }

    public Fragment getFragment() {
        switch (fragmentType) {
            case FragmentTag.PRODUCT_LIST:
                topTitle = "全部产品";
//                headerLayout.setWhiteTheme();
                return new FragmentProductSortList();
            case FragmentTag.REDPACKETS_LIST:
                return new RedPacketFragment();
        }
        return null;
    }

    protected void initToptitle() {
        headerLayout.showTitle(topTitle);
        headerLayout.showLeftBackButton();
    }
}
