package com.v5ent.xiubit.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.toobei.common.entity.InsuranceListEntiy
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.ThirdPartApi
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.InsuranceAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hasee-pc on 2017/12/26.
 */
class InsuranceClassifyFragment : FragmentBase() {
    var pageIndex = 1
    lateinit var mAdapter: InsuranceAdapter
    lateinit var category: String
    lateinit var categoryName: String
    lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        category = arguments?.getString("category") ?: ""
        categoryName = arguments?.getString("categoryName") ?: ""
        rootView = inflater?.inflate(R.layout.fragment_insurance_classify, null)!!
        pageIndex = 1
        initView()
        loadNetData()
        return rootView
    }


    private fun initView() {
        var refreshViewSrl = rootView.findViewById<SmartRefreshLayout>(R.id.refreshViewSrl)
        var recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)

        refreshViewSrl.setOnRefreshListener({
            pageIndex = 1
            loadNetData()
            refreshViewSrl.finishRefresh()
        })

        mAdapter = InsuranceAdapter()
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.adapter = mAdapter
        mAdapter.setOnLoadMoreListener({ loadNetData() }, recyclerView)

        mAdapter.emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank,"当前没有${categoryName}")
    }


    private fun loadNetData() {
        if (TextUtil.isEmpty(category)) {
            pageIndex = 1
            return
        }
        RetrofitHelper.getInstance().retrofit.create(ThirdPartApi::class.java)
                .insuranceList(ParamesHelp()
                        .put("pageIndex", "$pageIndex")
                        .put("insuranceCategory", "$category")
                        .put("pageSize","20")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<InsuranceListEntiy>() {
                    override fun bindViewWithDate(response: InsuranceListEntiy) {
                        if (response.data.pageIndex == 1) {
                            mAdapter.setNewData(response.data.datas)
                        } else {
                            mAdapter.addData(response.data.datas)
                        }
                        bindLoadMoreView(response.data, mAdapter)
                        pageIndex++
                    }
                })
    }
}