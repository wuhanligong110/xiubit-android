package com.v5ent.xiubit.data.viewpageadapter

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.toobei.common.data.MyBasePagerAdapter
import com.v5ent.xiubit.fragment.AvailableAddFeeCouponFragment
import com.v5ent.xiubit.fragment.AvailableRedpacketFragment

/**
 * 公司: tophlc
 * 类说明: 可用优惠券
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class AvailableCouponPageAdapter : MyBasePagerAdapter<Any> {

    constructor(activity: Activity?, fm: FragmentManager?,bundle : Bundle?) : super(activity, fm,null){
        var f1  = AvailableRedpacketFragment()
        f1.arguments = bundle
        fragments[0] = f1

        var f2  = AvailableAddFeeCouponFragment()
        f2.arguments = bundle
        fragments[1] = f2
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


}