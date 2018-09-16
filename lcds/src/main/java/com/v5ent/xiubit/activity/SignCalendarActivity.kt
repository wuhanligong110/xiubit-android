package com.v5ent.xiubit.activity

import android.os.Bundle
import android.view.View
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.SignCalendarData
import com.v5ent.xiubit.network.httpapi.SignInApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_calendar.*



/**
 * Created by hasee-pc on 2017/11/22.
 */
class SignCalendarActivity : NetBaseActivity() {
    private var currentYear : Int = 2017
    private var currentMonth : Int = 11
    override fun getContentLayout(): Int = R.layout.activity_sign_calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }


    private fun initView() {
        headerLayout.visibility = View.GONE
        setHeadViewCoverStateBar(statueBarView)
        backIv.setOnClickListener { finish() }
        ruleTv.setOnClickListener { WebActivityCommon.showThisActivity(ctx, C.SIGN_RULE, "") } //  活动规则

        val now = java.util.Calendar.getInstance()
        calendarView.setCalendarRange(2017,12,now.get(java.util.Calendar.YEAR),now.get(java.util.Calendar.MONTH)+1)

        calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener{
            override fun onPageChange(position: Int) {
            }

            override fun onDateChange(calendar: Calendar?) {
                headDataTv.text = "${calendar?.year}年${calendar?.month}月"
                getNetData("${calendar?.year}-${calendar?.month}")
                currentYear = calendar!!.year
                currentMonth = calendar!!.month
            }


        })
        currentYear = calendarView.curYear
        currentMonth = calendarView.curMonth
        headDataTv.text = "${calendarView.curYear}年${calendarView.curMonth}月"
        getNetData("${calendarView.curYear}-${calendarView.curMonth}")
    }

    val signDates = ArrayList<Calendar>()
    val signDataString = ArrayList<String>()  //用来记录已经添加的标记日期
    /**
     * @param monthStr 格式 ： 2010-12
     */
    private fun getNetData(monthStr: String) {
        RetrofitHelper.getInstance().retrofit.create(SignInApi::class.java)
                .signCalendar(ParamesHelp().put("signTime", monthStr).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<SignCalendarData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<SignCalendarData>) {
                        val datas = response.data.datas

                        datas.forEach {
                            val calendar = Calendar(it)
                            if (!signDataString.contains(calendar.toString())) {
                                signDataString.add(calendar.toString())
                                signDates.add(Calendar(it))
                            }
                        }
                        calendarView.setSchemeDate(signDates)
                    }


                })
    }

}