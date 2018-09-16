package com.v5ent.xiubit.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.toobei.common.entity.AddFeeCouponEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CouponApi
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.AddFeeCouponAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_fee_coupon.*

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class AddFeeCouponFragment : FragmentBase() {
    var pageIndex = 1
    lateinit var adapter: AddFeeCouponAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_add_fee_coupon, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getNetData()
    }

    private fun initView() {
        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getNetData()
            refreshViewSrl.finishRefresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = AddFeeCouponAdapter()
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getNetData()
        }, recyclerView)

        var emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank,"当前没有加佣券")
        adapter.emptyView = emptyView
    }


    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(CouponApi::class.java)
                .addFeeCoupon(ParamesHelp().put("pageIndex","$pageIndex")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<AddFeeCouponEntity>() {
                    override fun bindViewWithDate(response: AddFeeCouponEntity) {
                        if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)
                        bindLoadMoreView(response.data,adapter)
                        pageIndex ++
                    }

                })
    }


}