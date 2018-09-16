package com.v5ent.xiubit.data.viewpageadapter

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.toobei.common.data.MyBasePagerAdapter
import com.v5ent.xiubit.fragment.PaymentTypeFragment

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class PaymentPageAdapter : MyBasePagerAdapter<Any> {

    constructor(activity: Activity?, fm: FragmentManager?) : super(activity, fm,null){
//  repamentType      0-待回款 1-已回款 非必需 默认查所有
        fragments[0] = PaymentTypeFragment()
        var bundle1 = Bundle()
        bundle1.putInt("repamentType" , 1)
        fragments[0].arguments = bundle1

        fragments[1] = PaymentTypeFragment()
        var bundle2 = Bundle()
        bundle2.putInt("repamentType" , 0)
        fragments[1].arguments = bundle2

    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    fun refresFragmentData(){
        fragments.forEach {
            if (it != null && it is PaymentTypeFragment) it.refreshData()
        }
    }


}