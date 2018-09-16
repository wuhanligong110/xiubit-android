package com.v5ent.xiubit.data.viewpageadapter

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.toobei.common.data.MyBasePagerAdapter
import com.v5ent.xiubit.fragment.AddFeeCouponFragment
import com.v5ent.xiubit.fragment.GradeExperCouponFragment
import com.v5ent.xiubit.fragment.RedPacketFragment

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class CouponPageAdapter : MyBasePagerAdapter<Any> {

    constructor(activity: Activity?, fm: FragmentManager?) : super(activity, fm,null){
        fragments[0] = RedPacketFragment()
        fragments[1] = GradeExperCouponFragment()
        fragments[2] = AddFeeCouponFragment()
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


}