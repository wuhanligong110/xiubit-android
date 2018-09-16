package com.v5ent.xiubit.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.toobei.common.entity.QueryRedPacketEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CouponApi
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.RedPacketsAdapter
import com.v5ent.xiubit.event.RedPackageSendSucessEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_avilable_red_packets.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 *
 */
class AvailableRedpacketFragment : FragmentBase() {
    var pageIndex = 1
    lateinit var adapter: RedPacketsAdapter
    lateinit var deadline: String
    lateinit var model: String
    lateinit var patform: String
    lateinit var productId: String
    lateinit var type: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.activity_avilable_red_packets,null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initParames()
        initView()
        getNetData()
    }



    private fun initParames() {
        var bundle = arguments
        deadline = bundle?.getString("deadline")?:""
        model = bundle?.getString("model")?:""
        patform = bundle?.getString("patform")?:""
        productId = bundle?.getString("productId")?:""
        type = bundle?.getString("type")?:""

    }


    private fun initView() {

        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getNetData()
            refreshViewSrl.finishRefresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = RedPacketsAdapter(RedPacketsAdapter.TYPE_AVAILABLE_REDPACKET)
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getNetData()
        }, recyclerView)

        adapter.emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank,"暂无可用红包")

    }


    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(CouponApi::class.java)
                .queryAvailableRedPacket(ParamesHelp()
                        .put("deadline",deadline)
                        .put("pageIndex","$pageIndex")
                        .put("model",model)
                        .put("patform",patform)
                        .put("productId",productId)
                        .put("type",type)
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

                })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    fun refreshRedPackage(event: RedPackageSendSucessEvent) {
        pageIndex = 1
        getNetData()
    }


}