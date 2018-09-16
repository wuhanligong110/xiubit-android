package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import com.toobei.common.entity.AccountHomeData
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.event.CardBindSuccessEvent
import com.toobei.common.event.PayPwdSetSuccessEvent
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.WithDrawApi
import com.toobei.common.utils.TextFormatUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.event.WithDrawSuccessEvent
import com.v5ent.xiubit.service.CardCheckAndSetManager
import com.v5ent.xiubit.service.CardCheckAndSetManager.waitSkipWithDraw
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_balance.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by yangLin on 2018/5/9.
 */
class BalanceActivity : NetBaseActivity() {
    override fun getContentLayout(): Int = R.layout.activity_balance;
    lateinit var balanceMoney : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        balanceMoney = intent.getStringExtra("balanceMoney")?:"0.00"
        initView()
        loadData()
    }

   private fun initView(){
        headerLayout.showTitle("余额")
        headerLayout.showRightTextButton("提现记录",{
            showActivity(this, WithdrawList::class.java)

        })
        balanceTv.text = "${TextFormatUtil.formatFloat2zero(balanceMoney)}"
        withdrawTv.setOnClickListener {
            CardCheckAndSetManager.checkForWithDraw(ctx,object : CardCheckAndSetManager.CheckCallBack{
                override fun result(statue: Boolean) {
                    if(statue){
                        val intent = Intent(ctx, WithdrawActivity::class.java)
                        intent.putExtra("accountBalance", balanceMoney)
                        showActivity(ctx, intent)
                    }
                }

            })
        }
    }

    private fun loadData(){
        RetrofitHelper.getInstance().retrofit.create(WithDrawApi::class.java).myaccount(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<AccountHomeData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<AccountHomeData>) {
                        balanceMoney = "${response.data.totalAmount.toFloatOrNull() ?: 0.00f}"
                        balanceTv.text = "${TextFormatUtil.formatFloat2zero(balanceMoney)}"
                    }

                })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun withDrawSuccessEvent(event: WithDrawSuccessEvent) {
        loadData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun cardBindSuccess(event: CardBindSuccessEvent) {
        if (waitSkipWithDraw ) {
            when (event.flow_tag ){
                CardCheckAndSetManager.NO_CARD_HAS_PAYPWD -> {
                    startActivity(Intent(ctx,BindSuccessAndwithDrawActivity::class.java))
                    waitSkipWithDraw=false
                }
                CardCheckAndSetManager.NO_CARD_NO_PAYPWD -> {
                    //不做处理，等交易密码设置完成再跳转
                }
                else ->{
                    val intent = Intent(ctx, WithdrawActivity::class.java)
                    intent.putExtra("accountBalance", balanceMoney)
                    showActivity(ctx, intent)
                    waitSkipWithDraw=false
                }

            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun cardBindSuccess(event: PayPwdSetSuccessEvent) {
        if (waitSkipWithDraw  && CardCheckAndSetManager.state != CardCheckAndSetManager.NO_CARD_HAS_PAYPWD) {
            val intent = Intent(ctx, WithdrawActivity::class.java)
            intent.putExtra("accountBalance", balanceMoney)
            showActivity(ctx, intent)
            waitSkipWithDraw=false
        }
    }
}