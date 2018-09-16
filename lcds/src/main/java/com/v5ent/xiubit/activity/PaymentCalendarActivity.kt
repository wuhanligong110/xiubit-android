package com.v5ent.xiubit.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.RepamentCalendarEntity
import com.toobei.common.entity.RepamentCalendarStatisticsData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.RecordApi
import com.toobei.common.view.CalendarSlidingTabLayout
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.PaymentRecordAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import com.umeng.analytics.MobclickAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_payment_calendar.*
import kotlinx.android.synthetic.main.activity_payment_calendar_header.*

/**
 * Created by hasee-pc on 2017/11/16.
 */
class PaymentCalendarActivity : NetBaseActivity() {
    var pageIndex = 1
    lateinit var adapter: PaymentRecordAdapter
    lateinit var headView: View
    lateinit var backMoneyTv: TextView
    lateinit var waitbackMoneyTv: TextView
    lateinit var backMontyExplainTv: TextView
    lateinit var waitbackMoneyExplainTv: TextView
    val schemeDates = ArrayList<Calendar>()
    val paymentCalendarMap = HashMap<String, RepamentCalendarStatisticsData>()
    var selectCalendar: Calendar? = null
    var currentPageCalendar: Calendar? = null

    override fun getContentLayout(): Int = R.layout.activity_payment_calendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initNetData()
    }

    private fun initView() {

        headerLayout.showTitle("回款日历")
        headerLayout.showRightImageButton(R.drawable.icon_payment_list, {
            MobclickAgent.onEvent(ctx,"W_3_3_1")
            skipActivity(ctx, PaymentCalendarListActivity::class.java) })

        headView = LayoutInflater.from(ctx).inflate(R.layout.activity_payment_calendar_header, null)
        backMoneyTv = headView.findViewById<TextView>(R.id.backMoneyTv)
        waitbackMoneyTv = headView.findViewById<TextView>(R.id.waitbackMoneyTv)
        backMontyExplainTv = headView.findViewById<TextView>(R.id.backMontyExplainTv)
        waitbackMoneyExplainTv = headView.findViewById<TextView>(R.id.waitbackMontyExplainTv)

        initCalendar()
//        val tabList = ArrayList<CustomTabEntity>()
//        tabList.add(CommonTabEntity("网贷"))
//        tabList.add(CommonTabEntity("保险"))
//        listTabLayout.setTabData(tabList)

        //下拉刷新全部
        refreshViewSrl.setOnRefreshListener({
            paymentCalendarMap.clear()
            schemeDates.clear()
            initNetData()
            refreshViewSrl.finishRefresh()
        })

        adapter = PaymentRecordAdapter()
        adapter.setHeaderView(headView)

        adapter.setHeaderAndEmpty(true)
        recyclerView.post {
            val emptyHeight = MyApp.displayHeight - headerLayout.headHeight - headView.height
            adapter.emptyView = EmptyView(ctx).setHeight(emptyHeight).showEmpty("今日没有回款项目")
        }

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            loadMoreData()
        }, recyclerView)


    }

    private fun initCalendar() {
        val calendarTabLayout = headView.findViewById<CalendarSlidingTabLayout>(R.id.calendarTabLayout)
        val calendarView = headView.findViewById<CalendarView>(R.id.calendarView)
        val yearTv = headView.findViewById<TextView>(R.id.yearTv)

        val now = java.util.Calendar.getInstance()
        val nowYear = now.get(java.util.Calendar.YEAR)
        val nowMonth = now.get(java.util.Calendar.MONTH)
        calendarView.setCalendarRange(nowYear - 3, 1, nowYear + 3, 12)
        //顶部月份滑动条
        val monthList = calendarView.monthList
        val monthStrList = calendarView.monthStrList
        monthStrList.forEachIndexed({index,str ->
            monthStrList[index] = "${str}月"
        })

        val monthArr = monthStrList.toArray(arrayOfNulls<String>(monthStrList.size))
        calendarTabLayout.setViewPager(calendarView.viewPager, monthArr)

        calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onPageChange(position: Int) {
            }

            override fun onDateChange(calendar: Calendar?) {  //日历翻页只加载对应月份标记以及顶部统计
                yearTv.text = calendar?.year.toString()+"年"
                currentPageCalendar = calendar
                getPaymentCalendar()
            }

        }).setOnDateSelectedListener { calendar ->
            //选择某日之刷新列表
            selectCalendar = calendar
            pageIndex = 1
            getPaymentList()
        }
        yearTv.text = calendarView.curYear.toString()+"年"
        currentPageCalendar = calendarView.mCurrentPageCalendar
        selectCalendar = calendarView.mNowCalendar
        monthList.forEachIndexed { index, it ->
            if (it.year == calendarView.mCurrentYear && it.month == calendarView.mCurrentMonth) {
                calendarTabLayout.currentTab = index
            }
        }

}

private fun initNetData() {
    pageIndex = 1
    getPaymentCalendar()
    getPaymentList()
}

private fun loadMoreData() {
    getPaymentList()
}


private fun getPaymentList() {
    val repamentTime = selectCalendar.toString()
    RetrofitHelper.getInstance().retrofit.create(RecordApi::class.java)
            .repamentCalendar(ParamesHelp()
                    .put("pageIndex", "$pageIndex")
                    .put("pageSize", "20")
                    .put("repamentTime", repamentTime)
                    .build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NetworkObserver<RepamentCalendarEntity>() {
                override fun bindViewWithDate(response: RepamentCalendarEntity) {
                    if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                    else adapter.addData(response.data.datas)
                    bindLoadMoreView(response.data, adapter)
                    pageIndex++
                }

            })
}

private fun getPaymentCalendar() {

    fun refreshTopView(data: RepamentCalendarStatisticsData?) {
        backMoneyTv.text = data?.havaRepaymentAmtTotal ?: "--"
        waitbackMoneyTv.text = data?.waitRepaymentAmtTotal ?: "--"
    }

    val monthTime = currentPageCalendar?.toString()?.substring(0, 7) ?: "0"
    backMontyExplainTv.text = "${currentPageCalendar?.month ?: ""}月已回款金额(元)"
    waitbackMoneyExplainTv.text = "${currentPageCalendar?.month ?: ""}月待回款金额(元)"

    if (paymentCalendarMap.containsKey(monthTime)) {
        refreshTopView(paymentCalendarMap[monthTime])
        return
    }

    RetrofitHelper.getInstance().retrofit.create(RecordApi::class.java)
            .repamentCalendarStatistics(ParamesHelp()
                    .put("rePaymentMonth", monthTime)
                    .build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NetworkObserver<BaseResponseEntity<RepamentCalendarStatisticsData>>() {
                override fun bindViewWithDate(response: BaseResponseEntity<RepamentCalendarStatisticsData>) {
                    paymentCalendarMap.put(monthTime, response.data)
                    response.data.calendarStatisticsResponseList?.forEach {
                        schemeDates.add(Calendar(it.calendarTime, it.calendarNumber.toString()))
                    }
                    calendarView.setSchemeDate(schemeDates)
                    refreshTopView(response.data)

                }

            })
}
}