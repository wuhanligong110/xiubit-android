package com.v5ent.xiubit.data.viewpageadapter

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.toobei.common.data.MyBasePagerAdapter
import com.v5ent.xiubit.entity.PopularPosterTypeData.TypeListBean
import com.v5ent.xiubit.fragment.PopularImgClassifyFragment

/**
 * Created by hasee-pc on 2017/12/26.
 */
class PopularPosterPageAdapter(val activity: Activity?, val fm: FragmentManager?, val categorys: ArrayList<TypeListBean> = arrayListOf()) : MyBasePagerAdapter<TypeListBean>(activity, fm, categorys) {

    init {
        categorys.forEachIndexed({ index, category ->
            fragments[index] = PopularImgClassifyFragment()
            var bundle = Bundle()
            bundle.putString("category", datas[index].typeValue)
            bundle.putString("categoryName", datas[index].name)
            fragments[index].arguments = bundle
        })
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


    override fun getPageTitle(position: Int): CharSequence {
        return datas[position].name
    }

}