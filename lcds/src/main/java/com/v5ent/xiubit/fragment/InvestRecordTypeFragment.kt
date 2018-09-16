package com.v5ent.xiubit.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.toobei.common.entity.InvestCalendarEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.RecordApi
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.InvestRecordRecylerAdpter
import com.v5ent.xiubit.event.InvestRecordNumEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_red_packets.*
import org.greenrobot.eventbus.EventBus

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class InvestRecordTypeFragment : FragmentBase() {
    var pageIndex = 1
    lateinit var adapter: InvestRecordRecylerAdpter
    var recordType : Int = 0
    var investTime : String? = null
    var emptyViewHeight = -1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recordType = arguments?.getInt("recordType",0)?:0
        emptyViewHeight = arguments?.getInt("emptyViewHeight",-1)?:-1
        investTime = arguments?.getString("initDayTine")
        return inflater?.inflate(R.layout.fragment_invest_record_type, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getNetData()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = InvestRecordRecylerAdpter(recordType)
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getNetData()
        }, recyclerView)


        var emptyView = EmptyView(ctx).showEmpty(
                if (recordType == 0) "当前没有网贷投资记录" else "当前没有保险购买记录")
        if (emptyViewHeight >  0) emptyView.height = emptyViewHeight
        adapter.emptyView = emptyView
    }


    private fun getNetData() {
        var observable : Observable<InvestCalendarEntity>?
        if (recordType == 0){
            observable = RetrofitHelper.getInstance().retrofit.create(RecordApi::class.java)
                    .investCalendar(ParamesHelp()  //网贷
                            .put("pageIndex", "$pageIndex")
                            .put("pageSize", "20")
                            .put("investTime", investTime)
                            .build())
        }else{
            observable = RetrofitHelper.getInstance().retrofit.create(RecordApi::class.java)
                    .insuranceCalendar(ParamesHelp() //保险
                            .put("pageIndex", "$pageIndex")
                            .put("pageSize", "20")
                            .put("investTime", investTime)
                            .build())
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<InvestCalendarEntity>() {
                    override fun bindViewWithDate(response: InvestCalendarEntity) {
                        if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)
                        bindLoadMoreView(response.data,adapter)
                        pageIndex ++
                        EventBus.getDefault().post(InvestRecordNumEvent(recordType,response.data.totalCount))
                    }

                })
    }

    fun refreshData(timeStr: String? = null) {
        investTime = timeStr
        pageIndex = 1
        getNetData()
    }


}