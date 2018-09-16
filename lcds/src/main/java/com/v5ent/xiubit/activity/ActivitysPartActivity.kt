package com.v5ent.xiubit.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.toobei.common.entity.ActivityListDatasDataEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.ActivityAndNewsApi
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.ActivityPartAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_activitys_part.*

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class ActivitysPartActivity : NetBaseActivity() {
    var pageIndex = 1
    lateinit var adapter: ActivityPartAdapter



    override fun getContentLayout(): Int {
        return R.layout.activity_activitys_part
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getNetData()
    }

    private fun initView() {
        headerLayout.showTitle("活动中心")
        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getNetData()
            refreshViewSrl.finishRefresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = ActivityPartAdapter()
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getNetData()
        }, recyclerView)

    }


    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(ActivityAndNewsApi::class.java)
                .activityList(ParamesHelp()
                        .put("pageIndex","$pageIndex")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<ActivityListDatasDataEntity>() {
                    override fun bindViewWithDate(response: ActivityListDatasDataEntity) {
                        if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)
                        bindLoadMoreView(response.data,adapter)
                        pageIndex ++
                    }

                })
    }


}