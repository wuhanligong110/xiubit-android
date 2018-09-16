package com.v5ent.xiubit.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.toobei.common.entity.CustomerCfpmemberPageEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.CustomerAndCfgMemberApi
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.CfgMemberListAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cfg_type.*

class CfgTypeActivity : NetBaseActivity() {
    private lateinit var adapter: CfgMemberListAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_cfg_type
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attenInvestType = intent.getStringExtra("type") ?: "3"
        initView()
        getNetData()
    }

    private fun initView() {
        var title = when (attenInvestType) {
            "3" -> "未出单的直推理财师"
            "4" -> "我关注的直推理财师"
            else -> "未出单的直推理财师"
        }
        headerLayout.showTitle(title)

        cfgRv.layoutManager = LinearLayoutManager(ctx)

        adapter = CfgMemberListAdapter(if (attenInvestType == "3") CfgMemberListAdapter.TYPE_NOINVEST else CfgMemberListAdapter.TYPE_ATTENT)
        cfgRv.adapter = adapter

        //刷新
        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getNetData()
            refreshViewSrl.finishRefresh()
        }
        //加载更多
        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getNetData()
        }, cfgRv)

        var emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank,"当前没有$title")
        adapter.emptyView = emptyView
    }

    //	1未投资客户 2我关注的客户 3未出单的直推理财师 4我关注的直推理财师
    lateinit var attenInvestType: String
    private var pageIndex: Int = 1

    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(CustomerAndCfgMemberApi::class.java)
                .customerCfpmemberPage(ParamesHelp()
                        .put("type", "1")
                        .put("pageIndex", "$pageIndex")
                        .put("pageSize", "20")
                        .put("attenInvestType", attenInvestType)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CustomerCfpmemberPageEntity>() {
                    override fun bindViewWithDate(response: CustomerCfpmemberPageEntity) {
                        if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)

                        bindLoadMoreView(response.data, adapter)
                        pageIndex++
                    }

                })

    }


}