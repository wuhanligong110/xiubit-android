package com.v5ent.xiubit.activity

import android.os.Bundle
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.RepamentCalendarStatisticsData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.RecordApi
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.viewpageadapter.PaymentPageAdapter
import com.v5ent.xiubit.event.PaymentNumEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.umeng.analytics.MobclickAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_payment_calendar_list.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

/**
 * 回款日历
 */
class PaymentCalendarListActivity : NetBaseActivity() {
    lateinit var adapter: PaymentPageAdapter
    private var defIndex: Int = 0
    var title = arrayOf("已回款", "待回款")

    override fun getContentLayout(): Int {
        return R.layout.activity_payment_calendar_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        defIndex = intent.getIntExtra("type", 0)
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {

        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1;// 获取当前月份
        val format = SimpleDateFormat("yyyy-MM", Locale.CHINA)
        val monthTime = format.format(calendar.time)

        backMontyExplainTv.text = "${month}月已回款金额(元)"
        waitbackMontyExplainTv.text = "${month}月待回款金额(元)"

        RetrofitHelper.getInstance().retrofit.create(RecordApi::class.java)
                .repamentCalendarStatistics(ParamesHelp()
                        .put("rePaymentMonth", monthTime)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<RepamentCalendarStatisticsData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<RepamentCalendarStatisticsData>) {
                        backMoneyTv.text = response.data?.havaRepaymentAmtTotal?:"--"
                        waitbackMoneyTv.text = response.data?.waitRepaymentAmtTotal?:"--"

                    }

                })
    }

    private fun initView() {
        headerLayout.showTitle("回款日历")
        headerLayout.showRightImageButton(R.drawable.icon_payment_calendar,{
            MobclickAgent.onEvent(ctx,"W_3_3_1")
            skipActivity(ctx,PaymentCalendarActivity::class.java) })

        adapter = PaymentPageAdapter(ctx, supportFragmentManager)
        vPager.adapter = adapter

        tabLayout.setViewPager(vPager, title)
        tabLayout.currentTab = defIndex

        refreshViewSrl.setOnRefreshListener {
            initData()
            adapter.refresFragmentData()
            refreshViewSrl.finishRefresh()
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    fun refreshPaymentNum(event: PaymentNumEvent) {
        //0-待回款 1-已回款
        when (event.type){
            1 -> tabLayout.getTitleView(0).text = "已回款 (${event.num})"
            0 -> tabLayout.getTitleView(1).text = "待回款 (${event.num})"
        }
    }


}