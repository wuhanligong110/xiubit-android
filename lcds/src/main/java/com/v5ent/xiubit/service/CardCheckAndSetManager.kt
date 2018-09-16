package com.v5ent.xiubit.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.BundBankcardData
import com.toobei.common.entity.CheckResponseData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CardBindApi
import com.toobei.common.view.dialog.PromptDialogCommon
import com.v5ent.xiubit.activity.CardManagerAdd
import com.v5ent.xiubit.activity.PayPwdVerifyActivity
import com.v5ent.xiubit.activity.ResetPayPwdIdentity
import com.v5ent.xiubit.entity.IsBindOrgAcctData
import com.v5ent.xiubit.network.httpapi.ThirdAccountPartApi
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 11191 on 2018/5/30.
 * 负责整个省份证、银行卡、交易密码绑定逻辑
 */
object CardCheckAndSetManager{
    val NO_CARD_NO_PAYPWD = 1   //未绑卡，未设置交易密码 填写银行卡信息-填写银行卡支行地址-验证手机号-设置交易密码
    val NO_CARD_HAS_PAYPWD = 2   //未绑卡，已设置交易密码  验证交易密码-填写银行卡信息-填写银行卡支行地址-验证手机号-绑定银行卡成功
    val HAS_CARD_NO_PAYPWD = 3  // 已绑卡，未设置交易密码  验证身份证-验证手机号-设置交易密码
    val DEFAULT_STATE = 0
    var state = DEFAULT_STATE
    var waitSkipWithDraw = false  //是否等待跳转提现
    fun reSetStatue(){
        state = DEFAULT_STATE
    }


    fun checkForWithDraw(ctx : Context,callback : CheckCallBack){
        state = DEFAULT_STATE  //重置状态
        waitSkipWithDraw = false


        checkCardBindStatue(object : CheckCallBack{
            override fun result(statue: Boolean) {
                if (statue) {  //已绑卡
                    checkPayPwdStatue(object : CheckCallBack{
                        override fun result(statue: Boolean) {
                            if (statue) {  //已存在交易密码
                                callback.result(true)
                            }else{  //用户还未设置交易密码
                                state = HAS_CARD_NO_PAYPWD
                                nextSkipForWithDraw(ctx)
                            }
                        }
                    })
                }else{ //未绑卡
                    checkPayPwdStatue(object : CheckCallBack{
                        override fun result(statue: Boolean) {
                            if (statue) {  //已存在交易密码
                                state = NO_CARD_HAS_PAYPWD
                                nextSkipForWithDraw(ctx)
                            }else{  //用户还未设置交易密码
                                state = NO_CARD_NO_PAYPWD
                                nextSkipForWithDraw(ctx)
                            }
                        }
                    })
                }
            }
        })

    }

    private fun nextSkipForWithDraw(ctx: Context) {
        when(state){
            HAS_CARD_NO_PAYPWD ->{
                PromptDialogCommon(ctx as Activity?,"","交易密码未设置，请设置交易密码。","设置交易密码","取消")
                        .setBtnPositiveClickListener {
                            waitSkipWithDraw = true
                            var intent = Intent(ctx, ResetPayPwdIdentity::class.java)
                            intent.putExtra("forWhate","forSetPayPwd")
                            ctx.startActivity(intent)
                        }.setBtnNegativeClickListener { waitSkipWithDraw = false }
                        .show()
            }
            NO_CARD_HAS_PAYPWD -> {
                PromptDialogCommon(ctx as Activity?,"","当前无可用银行卡，请添加银行卡。","添加银行卡","取消")
                        .setBtnPositiveClickListener {
                            waitSkipWithDraw = true
                            val intent = Intent(ctx, PayPwdVerifyActivity::class.java)
                            intent.putExtra("for_what",PayPwdVerifyActivity.for_bindcard)
                            ctx.startActivity(intent)
                        }.setBtnNegativeClickListener { waitSkipWithDraw = false }
                        .show()

            }
            NO_CARD_NO_PAYPWD -> {
                PromptDialogCommon(ctx as Activity?, "", "当前无可用银行卡，请添加银行卡，并完成提现交易密码设置。", "添加银行卡", "取消")
                        .setBtnPositiveClickListener {
                            waitSkipWithDraw = true
                            ctx.startActivity(Intent(ctx, CardManagerAdd::class.java))
                        }.setBtnNegativeClickListener { waitSkipWithDraw = false }
                        .show()
            }

        }
    }

    //检查是否已经设置交易密码
    private fun checkPayPwdStatue(callback : CheckCallBack){
        RetrofitHelper.getInstance().getRetrofit(true).create(CardBindApi::class.java).verifyPayPwdState(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<CheckResponseData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<CheckResponseData>) {
                        callback.result("true" == response.data.rlt)

                    }

                })
    }


    //检查银行卡是否绑定
    public fun checkCardBindStatue(callback : CheckCallBack){
        RetrofitHelper.getInstance().retrofit.create(CardBindApi::class.java).cardBindState(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BundBankcardData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<BundBankcardData>) {
                        callback.result(response.data.isBundBankcard)

                    }

                })
    }


    //检查是否绑定第三方机构账户
    public fun isBindOrgAcc(orgNum : String,callback : CheckCallBack){
        RetrofitHelper.getInstance().retrofit.create(ThirdAccountPartApi::class.java)
                .isBindOrgAcc(ParamesHelp()
                        .put("platFromNumber",orgNum)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<IsBindOrgAcctData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<IsBindOrgAcctData>) {
                        callback.result(response.data.isBind)
                    }

                })
    }

    interface CheckCallBack{
        fun result(statue : Boolean)

    }

}