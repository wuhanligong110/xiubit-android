package com.v5ent.xiubit.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.view.Util
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recyclerDecoration.GridSpacingItemDecoration
import com.v5ent.xiubit.data.recylerapter.SelectImageAdapter
import com.v5ent.xiubit.entity.BrandPromotionData
import com.v5ent.xiubit.network.httpapi.PopularPosterApi
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hasee-pc on 2017/12/28.
 */
class PopularImgClassifyFragment : FragmentBase() {
    lateinit var category : String
    lateinit var categoryName : String
    lateinit var rootView : View
    lateinit var adapter : SelectImageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        category = arguments?.getString("category") ?: "1"
        categoryName = arguments?.getString("categoryName") ?: ""
        rootView = inflater!!.inflate(R.layout.fragment_popular_img_classify, null)
        initView()
        initData()
        return rootView
    }



    private fun initView() {
        val rv = rootView.findViewById<RecyclerView>(R.id.imageListRv)
        adapter = SelectImageAdapter()
        rv.layoutManager = GridLayoutManager(ctx, 3)
        rv.adapter = adapter
        rv.addItemDecoration(GridSpacingItemDecoration(3,Util.dip2px(ctx, 20f),true))
        adapter.emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank,"当前没有${categoryName}海报")
    }

    private fun initData() {
        RetrofitHelper.getInstance().retrofit.create(PopularPosterApi::class.java)
                .getPopularPoster(ParamesHelp()
                        .put("type", category)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BrandPromotionData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<BrandPromotionData>) {
                        adapter.setNewData(response.data.posterList)
                    }
                })
    }
}