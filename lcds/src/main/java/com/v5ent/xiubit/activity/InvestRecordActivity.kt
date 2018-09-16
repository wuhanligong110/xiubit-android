package com.v5ent.xiubit.activity

import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_invest_record.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

/**
 * 公司: tophlc
 * 类说明:交易记录
 *
 * @author hasee-pc
 * @time 2017/6/26
 */

class InvestRecordActivity : NetBaseActivity(){
    lateinit var adapter: InvestRecordPageAdapter
    private var defIndex: Int = 0
    var title = arrayOf("网贷", "保险")
    override fun getContentLayout(): Int {
        return R.layout.activity_invest_record
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        defIndex = intent.getIntExtra("type", 0)
        super.onCreate(savedInstanceState)
        initView()

    }


    private fun initView() {
        headerLayout.showTitle("投资记录")
        headerLayout.showRightImageButton(R.drawable.icon_payment_calendar,{
            MobclickAgent.onEvent(ctx,"W_3_2_1")
            skipActivity(ctx,InvestRecordCalendarActivity::class.java) })

        refreshViewSrl.setOnRefreshListener {
            initData()
            adapter.refresFragmentData()
            refreshViewSrl.finishRefresh()
        }

        headerLayout.post {
            val emptyHeight = MyApp.displayHeight - headerLayout.headHeight - vPager.y
            adapter = InvestRecordPageAdapter(ctx, supportFragmentManager,emptyHeight.toInt() )
            vPager.adapter = adapter
            tabLayout.setViewPager(vPager, title)
            tabLayout.currentTab = defIndex
            initData()
        }

        questIv.setOnClickListener {
            PromptDialogCommon(ctx,"团队累计业绩","指你的所有理财师团队成员（包括：直推、二级、三级）的出单金额 + 所有客户投资金额。","我知道了",null).show()
        }

    }

    private fun initData() {

        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1;// 获取当前月份
        val format = SimpleDateFormat("yyyy-MM", Locale.CHINA)
        val monthTime = format.format(calendar.time)

        investAmtTotalExplainTv.text = "${month}月团队累计业绩(元)"
        feeAmountSumTotalExplainTv.text = "${month}月累计佣金(元)"

        RetrofitHelper.getInstance().retrofit.create(RecordApi::class.java)
                .investCalendarStatistics(ParamesHelp()
                        .put("investMonth", monthTime)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<InvestCalendarStatisticsData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<InvestCalendarStatisticsData>) {
                        investAmtTotalTv.text = response.data?.investAmtTotal?:"--"
                        feeAmountSumTotalTv.text = response.data?.feeAmountSumTotal?:"--"

                    }

                })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    fun refreshRecordNum(event: InvestRecordNumEvent) {
        when (event.type){
            0 -> tabLayout.getTitleView(0).text = "网贷 (${event.num})"
            1 -> tabLayout.getTitleView(1).text = "保险 (${event.num})"
        }
    }

}
