package com.v5ent.xiubit.activity

import android.os.Bundle
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.InvestCalendarStatisticsData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.RecordApi
import com.toobei.common.view.dialog.PromptDialogCommon
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.viewpageadapter.InvestRecordPageAdapter
import com.v5ent.xiubit.event.InvestRecordNumEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.umeng.analytics.MobclickAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_invest_record_calendar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by hasee-pc on 2017/11/29.
 */
class InvestRecordCalendarActivity : NetBaseActivity() {
    private var defIndex: Int = 0
    lateinit var adapter: InvestRecordPageAdapter
    val schemeDates = ArrayList<Calendar>()
    val investCalendarMap = HashMap<String, InvestCalendarStatisticsData>()
    var selectCalendar : Calendar? = null
    var currentPageCalendar : Calendar? = null

    override fun getContentLayout(): Int {
        return R.layout.activity_invest_record_calendar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        defIndex = intent.getIntExtra("type", 0)
        super.onCreate(savedInstanceState)
        initView()

    }

    private fun initView() {
        headerLayout.showTitle("投资记录")
        headerLayout.showRightImageButton(R.drawable.icon_payment_list,{
            MobclickAgent.onEvent(ctx,"W_3_2_1")
            skipActivity(ctx,InvestRecordActivity::class.java)})

        initCalendar()

        //下拉刷新全部
        refreshViewSrl.setOnRefreshListener({
            investCalendarMap.clear()
            schemeDates.clear()
            initNetData()
            refreshInvestList()
            refreshViewSrl.finishRefresh()
        })

        headerLayout.post {
            val emptyHeight = MyApp.displayHeight - headerLayout.headHeight - vPager.y
            val investTime = selectCalendar.toString()
            adapter = InvestRecordPageAdapter(ctx, supportFragmentManager,emptyHeight.toInt(), investTime)
            vPager.adapter = adapter
            listTabLayout.setViewPager(vPager,arrayOf("网贷", "保险"))
            listTabLayout.currentTab = defIndex
            initNetData()
        }

        questIv.setOnClickListener {
            PromptDialogCommon(ctx,"团队累计业绩","指你的所有理财师团队成员（包括：直推、二级、三级）的出单金额 + 所有客户投资金额。","我知道了",null).show()
        }
    }

    private fun initNetData(){
        getInvestCalendar()
    }

    private fun initCalendar() {
        val now = java.util.Calendar.getInstance()
        val nowYear = now.get(java.util.Calendar.YEAR)
        val nowMonth = now.get(java.util.Calendar.MONTH)
        calendarView.setCalendarRange(nowYear - 3,1,nowYear,nowMonth+1)
        //        //顶部月份滑动条
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
                getInvestCalendar()
            }

        }).setOnDateSelectedListener { calendar -> //选择某日之刷新列表
            selectCalendar = calendar
            refreshInvestList()
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

    private fun refreshInvestList() {
        val investTime = selectCalendar.toString()
        adapter.refresFragmentData(investTime)

    }

    private fun getInvestCalendar() {

        fun refreshTopView(data : InvestCalendarStatisticsData?){
            investAmtTotalTv.text = data?.investAmtTotal?:"--"
            feeAmountSumTotalTv.text = data?.feeAmountSumTotal?:"--"
        }

        val monthTime = currentPageCalendar?.toString()?.substring(0,7)?:"0"
        investAmtTotalExplainTv.text = "${currentPageCalendar?.month?:""}月团队累计业绩(元)"
        feeAmountSumTotalExplainTv.text = "${currentPageCalendar?.month?:""}月累计佣金(元)"

        if (investCalendarMap.containsKey(monthTime)) {
            refreshTopView(investCalendarMap[monthTime])
            return
        }

        RetrofitHelper.getInstance().retrofit.create(RecordApi::class.java)
                .investCalendarStatistics(ParamesHelp()
                        .put("investMonth", monthTime)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<InvestCalendarStatisticsData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<InvestCalendarStatisticsData>) {
                        investCalendarMap.put(monthTime,response.data)
                        response.data.calendarStatisticsResponseList?.forEach {
                            schemeDates.add(Calendar(it.calendarTime,it.calendarNumber.toString()))
                        }
                        calendarView.setSchemeDate(schemeDates)
                        refreshTopView(response.data)

                    }

                })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    fun refreshRecordNum(event: InvestRecordNumEvent) {
            when (event.type){
                0 -> listTabLayout.getTitleView(0).text = "网贷 (${event.num})"
                1 -> listTabLayout.getTitleView(1).text = "保险 (${event.num})"
            }
    }

}