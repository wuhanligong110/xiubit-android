package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.toobei.common.entity.InvestedPlatformData
import com.toobei.common.entity.InvestedPlatformEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.NetLoanApi
import com.toobei.common.utils.TextDecorator
import com.toobei.common.view.dialog.PromptDialogCommon
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.InvestListRecycleAdapter
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.popupwindow.NetInvestComposePopu
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_net_loan.*



class MyNetLoanActivity : NetBaseActivity() {
    var platformAdapter: InvestListRecycleAdapter? = null
    var data : InvestedPlatformData? = null
    var questDialog : PromptDialogCommon? = null

    override fun getContentLayout(): Int {
        return R.layout.activity_my_net_loan
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        showLoadProgress()
        initData()
    }

    private fun initView() {
        headerLayout.showTitle("网贷")
        refreshViewSrl.setOnRefreshListener(OnRefreshListener { 
            initData()
            refreshViewSrl.finishRefresh()
        })
        platformAdapter = InvestListRecycleAdapter(ctx)
        investOrgRv.layoutManager = LinearLayoutManager(ctx)
        investOrgRv.adapter = platformAdapter
        investOrgRv.isNestedScrollingEnabled = false
        investOrgRv.isFocusable = false

        btn_blank.setOnClickListener(View.OnClickListener {
            var intent = Intent(ctx, CommonFragmentActivity::class.java)
            intent.putExtra(C.FragmentTag.KEY_TAG, C.FragmentTag.PRODUCT_LIST)
            ctx.startActivity(intent)
        })
        
        shareTv.setOnClickListener {
            NetInvestComposePopu(ctx,data).showPopupWindow(headerLayout)
        }

        questIv.setOnClickListener {
            questDialog?:PromptDialogCommon(ctx, "综合年化收益率", getString(R.string.net_load_profie_indroduce), "我知道了",null)
                    .show()
        }

    }

    override fun onLoadFailRefresh() {
        super.onLoadFailRefresh()
        initData()
    }

    private fun initData() {
        if (TextUtil.isEmpty(MyApp.getInstance().loginService.token)) {
            showActivity(ctx,LoginActivity::class.java)
            return
        }

        RetrofitHelper.getInstance().retrofit.create(NetLoanApi::class.java)
                .MyInvestedPlatform(ParamesHelp().build())
               .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<InvestedPlatformEntity>(){
                    
                    override fun bindViewWithDate(response: InvestedPlatformEntity) {
                        data = response.data
                        investBaseMoneyTv.text = response.data.investingAmt?:"--"
                        investProfitTv.text = response.data.investingProfit?:"--"
                        totalProfitTv.text = response.data.totalProfit?:"--"
                        
                        val platformInvestList = response.data.platformInvestStatisticsList
                            if (platformInvestList.isNotEmpty()) {
                                blankLl.visibility = View.GONE
                                platformAdapter?.refresh(platformInvestList)
                            } else {
                                blankLl.visibility = View.VISIBLE
                            }
                        
                        TextDecorator.decorate(orgNumInfoTv,"在投平台：${response.data.investingPlatformNum?:0} 个")
                                .setTextColor(R.color.text_red_common,"${response.data.investingPlatformNum?:0}")
                                .build()
                        
                        TextDecorator.decorate(orgYearProfitTv,"综合年化收益率：${response.data.yearProfitRate?:0}%")
                                .setTextColor(R.color.text_red_common,"${response.data.yearProfitRate?:0}%")
                                .build()
                        chartPieCp.setChartData(response.data.investingStatisticList)

                        }

                    override fun onComplete() {
                        super.onComplete()
                        showLoadContent()
                    }

                    override fun onNetSystemException(error: String?) {
                        super.onNetSystemException(error)
                        showLoadFail()
                    }
                })
    }
}
