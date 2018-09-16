package com.v5ent.xiubit.data.viewpageadapter

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.toobei.common.data.MyBasePagerAdapter
import com.v5ent.xiubit.fragment.SendCfgFragment
import com.v5ent.xiubit.fragment.SendCustomerFragment

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class SendRedpacketPageAdapter : MyBasePagerAdapter<Any> {

    constructor(activity: Activity?, fm: FragmentManager?) : super(activity, fm,null){

        fragments[0] = SendCfgFragment()
        fragments[1] = SendCustomerFragment()


    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


}