package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.toobei.common.TopBaseActivity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.utils.ToastUtil
import com.toobei.common.view.dialog.PromptDialogCommon
import com.v5ent.xiubit.BuildConfig
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.SignBounsTransferData
import com.v5ent.xiubit.entity.SignStatisticsData
import com.v5ent.xiubit.network.httpapi.SignInApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.umeng.analytics.MobclickAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_award_money.*

/**
 * Created by yangLin on 2018/4/9.
 */
class AwardMoneyActivity : NetBaseActivity(), View.OnClickListener {
    var canTranMoney = "0.00"
    var hideSignTab = false

    override fun getContentLayout(): Int = R.layout.activity_award_money

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSignTab = intent.getBooleanExtra("hideSignTab",false)
        initView()
        initData()
    }

    private fun initData() {
        RetrofitHelper.getInstance().retrofit.create(SignInApi::class.java)
                .signStatistics(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<SignStatisticsData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<SignStatisticsData>) {
                        leftBounsTv.text = response.data.leftBouns
                        canTranTV.text = response.data.transferBouns
                        totalMoneyTv.text = response.data.totalBouns
                        canTranMoney = response.data.transferBouns
//                        if (!MyApp.getInstance().getCurUserSp().isAwardPromptHasShow()) {
//                            val promptDialogCommon = PromptDialogCommon(ctx, "奖励金转出规则变化", "可转出奖励金已按照最优的存量奖励金规则转化" +
////                                    "\n" +
//                                    "您目前可转出奖励金为：${canTranMoney}元",
//                                    "确认",null)
//                            promptDialogCommon.setBtnPositiveClickListener { promptDialogCommon.dismiss() }
//                            promptDialogCommon.show()
//                            MyApp.getInstance().getCurUserSp().setAwardPromptHasShow()
//                        }
                    }


                })
    }

    private fun initView() {
        headerLayout.showTitle("我的奖励金")
        headerLayout.showRightTextButton("明细", {
            startActivity(Intent(ctx,AwardMoneyRecordActivity::class.java))
        })

        signTab.visibility =if (hideSignTab) View.GONE else View.VISIBLE
        gotoChoujiang.setOnClickListener(this)
        shareDanTv.setOnClickListener(this)
//        gotoFaTie.setOnClickListener(this)
        gotoSign.setOnClickListener(this)
        gotoTranOut.setOnClickListener(this)
        questIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        var intent: Intent
        when (v?.id) {
            R.id.shareDanTv -> { //去晒单
                var shareDanUrl = "http://declare.bethye.top/postDetail?order=1"
                if (!BuildConfig.FLAVOR.equals("rel")){
                    shareDanUrl = "http://predeclare.bethye.top/postDetail?order=1"
                }

                WebActivityCommon.showThisActivity(ctx,shareDanUrl,"")
            }
            R.id.questIv -> { //转出说明
                WebActivityCommon.showThisActivity(ctx as TopBaseActivity?, C.SIGN_TRANSFER,"")
            }
            R.id.gotoChoujiang -> {  //抽奖
                MobclickAgent.onEvent(ctx, "W_10_3_1")
                WebActivityCommon.showThisActivity(ctx as TopBaseActivity, C.BOUNTY, "")
            }
//            R.id.gotoFaTie -> {  //发帖
//
//            }
            R.id.gotoSign -> { //签到
                startActivity(Intent(ctx, SignInActivity::class.java))
            }
            R.id.gotoTranOut -> {  //转出
                if (canTranMoney.toFloatOrNull() ?: 0f > 0) {
                    val promptDialogCommon = PromptDialogCommon(ctx, "您本次即将转出奖励金", "${canTranMoney}元", "确认转出","取消")
                    promptDialogCommon.setBtnPositiveClickListener {
                        RetrofitHelper.getInstance().retrofit.create(SignInApi::class.java)
                                .signBounsTransfer(ParamesHelp().build())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : NetworkObserver<BaseResponseEntity<SignBounsTransferData>>() {
                                    override fun bindViewWithDate(response: BaseResponseEntity<SignBounsTransferData>) {
                                        initData()
                                        val intent = Intent(ctx, TransSignAwaradOutSuccessActivity::class.java)
                                        intent.putExtra("money", response.data.transferBouns ?: "0")
                                        startActivity(intent)
                                    }

                                    override fun onNetSystemException(error: String?) {
                                        super.onNetSystemException(error)
                                        ToastUtil.showCustomToast("转出失败，请重试")
                                    }


                                })
                    }
                    promptDialogCommon.show()
                } else {
                    val promptDialogCommon = PromptDialogCommon(ctx, "您可转出的奖励金为0元", "单笔投资3万(期限≥30天)奖励金可转出10元", "立即投资", "确认")
                    promptDialogCommon.setBtnPositiveColor(R.color.text_blue_common)
                    promptDialogCommon.setBtnNegativeColor(R.color.text_gray_common)
                    promptDialogCommon.setBtnPositiveClickListener { //跳转投资
                        intent = Intent(ctx, MainActivity::class.java)
                        intent.putExtra("skipTab", "p1t0")
                        startActivity(intent) }
                    promptDialogCommon.setBtnNegativeClickListener {
                        promptDialogCommon.dismiss()

                    }
                    promptDialogCommon.show()
                }
            }

        }

    }

}