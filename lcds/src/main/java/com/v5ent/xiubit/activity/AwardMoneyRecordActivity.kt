package com.v5ent.xiubit.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.PageListBase
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.AwardMoneyRecordAdapter
import com.v5ent.xiubit.entity.AwardMoneyRecordDetial
import com.v5ent.xiubit.network.httpapi.AwardMoneyApi
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_award_money_record.*

/**
 * Created by yangLin on 2018/4/10.
 */
class AwardMoneyRecordActivity : NetBaseActivity() {
    var pageIndex = 1
    var adapter : AwardMoneyRecordAdapter = AwardMoneyRecordAdapter()
    override fun getContentLayout(): Int = R.layout.activity_award_money_record

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    override fun initStatusBarStyle() {
        super.initStatusBarStyle()
//        StatusBarUtil.StatusBarLightMode(this);  //白底黑字
    }

    private fun initView() {
//        headerLayout.setWhiteTheme()
        awardRecordRv.layoutManager = LinearLayoutManager(ctx)
        headerLayout.showTitle("奖励金记录")
        awardRecordRv.adapter = adapter

        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            initData()
            refreshViewSrl.finishRefresh()
        }

        adapter.setOnLoadMoreListener({
            initData()
        },awardRecordRv)

        ////空视图设定
        val emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank, "暂无奖励金明细")
        adapter.setEmptyView(emptyView)
    }

    private fun initData() {
        RetrofitHelper.getInstance().retrofit.create(AwardMoneyApi::class.java)
                .awardMoneyRecordDetial(ParamesHelp().put("pageIndex","$pageIndex").build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<PageListBase<AwardMoneyRecordDetial>>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<PageListBase<AwardMoneyRecordDetial>>) {
                        if (pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)

                        bindLoadMoreView(response.data,adapter)
                        pageIndex ++
                    }

                })
    }

}