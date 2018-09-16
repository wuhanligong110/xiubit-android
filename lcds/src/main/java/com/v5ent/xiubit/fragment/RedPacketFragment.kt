package com.v5ent.xiubit.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.toobei.common.entity.QueryRedPacketEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.CouponApi
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.RedPacketsAdapter
import com.v5ent.xiubit.event.RedPackageSendSucessEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_red_packets.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class RedPacketFragment : FragmentBase() {
    var pageIndex = 1
    lateinit var adapter: RedPacketsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_red_packets, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        showRefreshProgress()
        getNetData()
    }

    private fun initView() {
        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getNetData()
            refreshViewSrl.finishRefresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = RedPacketsAdapter(RedPacketsAdapter.TYPE_COUPON_REDPACKET)
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getNetData()
        }, recyclerView)

        var emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank,"当前没有红包")
        adapter.emptyView = emptyView
    }


    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(CouponApi::class.java)
                .queryRedPacket(ParamesHelp()
                        .put("pageIndex" ,"$pageIndex")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<QueryRedPacketEntity>() {
                    override fun bindViewWithDate(response: QueryRedPacketEntity) {
                        if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)
                        bindLoadMoreView(response.data,adapter)
                        pageIndex ++
                    }

                    override fun onFinish() {
                        super.onFinish()
                        dismissRefreshProgress()
                    }
                })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    fun refreshRedPackage(event: RedPackageSendSucessEvent) {
        pageIndex = 1
        getNetData()
    }


}