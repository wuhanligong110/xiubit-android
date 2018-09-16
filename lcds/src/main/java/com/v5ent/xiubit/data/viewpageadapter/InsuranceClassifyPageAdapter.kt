package com.v5ent.xiubit.data.viewpageadapter

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.toobei.common.data.MyBasePagerAdapter
import com.toobei.common.entity.InsuranceCategoryEntiy
import com.v5ent.xiubit.fragment.InsuranceClassifyFragment

/**
 * Created by hasee-pc on 2017/12/26.
 */
class InsuranceClassifyPageAdapter(val activity: Activity?, val fm: FragmentManager?, val categorys: ArrayList<InsuranceCategoryEntiy.InsuranceCategoryBean> = arrayListOf()) : MyBasePagerAdapter<InsuranceCategoryEntiy.InsuranceCategoryBean>(activity, fm, categorys) {

    init {
        categorys.forEachIndexed({ index, category ->
            fragments[index] = InsuranceClassifyFragment()
            var bundle = Bundle()
            bundle.putString("category", datas[index].category)
            bundle.putString("categoryName", datas[index].message)
            fragments[index].arguments = bundle
        })
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return datas[position].message
    }

}