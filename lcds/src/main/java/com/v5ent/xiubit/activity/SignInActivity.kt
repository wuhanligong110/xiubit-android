package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.ShareContent
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.service.ShareService
import com.toobei.common.utils.TextDecorator
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.SignInData
import com.v5ent.xiubit.entity.SignInfoData
import com.v5ent.xiubit.entity.SignShareInfoData
import com.v5ent.xiubit.network.httpapi.SignInApi
import com.v5ent.xiubit.service.CardCheckAndSetManager
import com.v5ent.xiubit.service.LoginService
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.dialog.SignInSuccessDialog
import com.v5ent.xiubit.view.dialog.SignShareAwardDialog
import com.v5ent.xiubit.view.popupwindow.SignSharePopupWindow
import com.umeng.analytics.MobclickAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in_start.*
import org.xsl781.utils.Logger
import java.net.URLEncoder


/**
 * Created by hasee-pc on 2017/11/17.
 */
class SignInActivity : NetBaseActivity() {
    private var startView: View? = null
    lateinit var signDayNumTv: TextView
    lateinit var goldCoinIv: ImageView
    private var successView: View? = null


    override fun getContentLayout(): Int {
        return R.layout.activity_sign_in
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        getSignInfo()
    }

    override fun onResume() {
        super.onResume()
        if (shouldShareAwardDialog) {
            Handler().postDelayed({
                if (shouldShareAwardDialog) {
                    showShareAwardDialog()
                    shouldShareAwardDialog = false
                }
            }, 500)
        }
    }

    private fun initView() {
        headerLayout.visibility = View.GONE
        setHeadViewCoverStateBar(statueBarView)
        backIv.setOnClickListener { finish() }
        calendarIv.setOnClickListener { showActivity(ctx, SignCalendarActivity::class.java) }// 跳转签到日历
        ruleTv.setOnClickListener { WebActivityCommon.showThisActivity(ctx, C.SIGN_RULE, "") }  //活动规则

        if (startView == null) startView = startVs.inflate()

    }

    override fun onLoadFailRefresh() {
        super.onLoadFailRefresh()
        getSignInfo()
    }

    private fun showNoSignView(data: SignInfoData?) {
        if (startView == null) startView = startVs.inflate()
        successView?.visibility = View.INVISIBLE
        startView?.visibility = View.VISIBLE
        signDayNumTv = startView!!.findViewById<TextView>(R.id.signDayNumTv)
        goldCoinIv = startView!!.findViewById<ImageView>(R.id.goldCoinIv)
        val lookAwardMoneyEntry = startView!!.findViewById<View>(R.id.lookAwardMoneyEntry)

        TextDecorator.decorate(signDayNumTv, "连续签到 ${data?.consecutiveDays ?: 0} (天)")
                .setAbsoluteSize(resources.getDimension(R.dimen.w18).toInt(), "${data?.consecutiveDays ?: 0}")
                .build()
        //签到
        signBtn.setOnClickListener {
            MobclickAgent.onEvent(ctx, "S_7_2")
            scrollView.smoothScrollTo(0, 0)
            signIn()

        }

        lookAwardMoneyEntry.setOnClickListener {
            var intent = Intent(ctx, AwardMoneyActivity::class.java)
            intent.putExtra("hideSignTab", true)
            startActivity(intent)
        } //查看累计奖励
    }

    private fun signIn() {
        RetrofitHelper.getInstance().retrofit.create(SignInApi::class.java)
                .signIn(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<SignInData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<SignInData>) {
                        goldCoinIv.visibility = View.INVISIBLE
                        SignInSuccessDialog(ctx, response.data)
                                .setOnBtnClickListener(object : SignInSuccessDialog.BtnClickListener {

                                    override fun onConfirmClick() {
                                        getSignInfo()
                                        showSignSharePopu()
                                    }

                                    override fun onCloseClick() {
                                        getSignInfo()
                                    }

                                }).show()

                    }


                })
    }

    private fun getSignInfo() {
        RetrofitHelper.getInstance().retrofit.create(SignInApi::class.java)
                .signInfo(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<SignInfoData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<SignInfoData>) {
                        if (response.data.hasSigned) showHasSignView(response.data)
                        else showNoSignView(response.data)


                    }

                    override fun onNetSystemException(error: String?) {
                        super.onNetSystemException(error)
                        showLoadFail()
                    }

                })

    }


    fun showHasSignView(data: SignInfoData) {
        val signInfo = data.signInfo
        if (successView == null) successView = successVs.inflate()
        startView?.visibility = View.INVISIBLE
        successView?.visibility = View.VISIBLE

        val signAwardTv = successView?.findViewById<TextView>(R.id.signAwardTv)
        val redpacketAwardTv = successView?.findViewById<TextView>(R.id.redpacketAwardTv)
        val signDayNumResultTv = successView?.findViewById<TextView>(R.id.signDayNumResultTv)
        val signShareBtn = successView?.findViewById<Button>(R.id.signShareBtn)
        val lookAwardMoneySuccessEntry = successView?.findViewById<View>(R.id.lookAwardMoneySuccessEntry)

        val signAwardStr = "${signInfo.signAmount}元"
        //翻倍类型 1：分享翻倍 2：连续签到翻倍
        var extrlAwardStr = ""
        if (!(data.times.isNullOrBlank() || data.times == "0")) {
            extrlAwardStr = when (data.signInfo?.timesType) {
                "1" -> "分享翻倍${data.signInfo?.timesAmount}元"
                "2" -> "${signInfo.signAmount}元*${data.times}"
                else -> ""
            }
        }
        //分享红包奖励
        TextDecorator.decorate(redpacketAwardTv, "+分享奖励").setTextColor(R.color.text_black_common, "+").build()
        redpacketAwardTv?.visibility = if ((data.signInfo?.redpacketId.isNullOrBlank())) View.GONE else View.VISIBLE

        TextDecorator.decorate(signAwardTv, "签到奖励${signAwardStr}${if (extrlAwardStr.isNullOrBlank()) "" else "+$extrlAwardStr"}")
                .setTextColor(R.color.text_red_common, signAwardStr, extrlAwardStr)
                .build()
        signDayNumResultTv?.text = "今日签到已完成！连续签到${data.consecutiveDays}天"

        signShareBtn?.setOnClickListener { showSignSharePopu() }

        lookAwardMoneySuccessEntry?.setOnClickListener {
            var intent = Intent(ctx, AwardMoneyActivity::class.java)
            intent.putExtra("hideSignTab", true)
            startActivity(intent)
        }// 查看累计奖励

        //	分享状态 1：已分享 0：未分享
        when (data.signInfo.shareStatus) {
            "1" -> signShareBtn?.visibility = View.GONE
            "0" -> signShareBtn?.visibility = View.VISIBLE
            else -> signShareBtn?.visibility = View.VISIBLE
        }
    }

    var shouldShareAwardDialog = false
    private fun showSignSharePopu() {
        fun shareSign(hasBindCard: Boolean) {
            RetrofitHelper.getInstance().retrofit.create(SignInApi::class.java)
                    .signShareInfo(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<BaseResponseEntity<SignShareInfoData>>() {
                        override fun bindViewWithDate(response: BaseResponseEntity<SignShareInfoData>) {

                            val userName = URLEncoder.encode(LoginService.getInstance().curUser.userName, "UTF-8")
                            val mobile = LoginService.getInstance().curUser.mobile
                            val userNameStr = if (hasBindCard) "&name=${userName}" else ""
                            val shareLink = C.INVITE_REGISTER + "recommendCode=${mobile}${userNameStr}&source=forSign"
//                        val userId = MyApp.getInstance().defaultSp.getCurUserId()
//                        val shareLink = C.INVITE_REGISTER + "userId=${userId?:""}"
                            Logger.e("shareLink==" + shareLink)
                            SignSharePopupWindow(ctx, ShareContent("貅比特签到,领现金奖励"
                                    , "我在貅比特签到已领${response.data.shareDesc}元现金，你也跟我一起来吧！天天签到,天天领现金"
                                    , shareLink   //签到分享网址
                                    , "25c3a22ef87adf6964f04f858614f985")
                                    , object : ShareService.OnShareSuccessListener {
                                override fun onShareSuccessed(sharePlatform: ShareService.SharePlatform?) {} //实际没有成功回调

                                override fun onShareCancelOrFail(sharePlatform: ShareService.SharePlatform?) {
                                    signShareAwardDialog?.dismiss()
                                    shouldShareAwardDialog = false
                                }

                            })
                                    .setOnShareListener { shouldShareAwardDialog = true }
                                    .showPopupWindow(scrollView)
                        }
                    })
        }


        CardCheckAndSetManager.checkCardBindStatue(object : CardCheckAndSetManager.CheckCallBack {
            override fun result(statue: Boolean) {
                shareSign(statue)
            }
        })


    }

    var signShareAwardDialog: SignShareAwardDialog? = null
    private fun showShareAwardDialog() {
        signShareAwardDialog = SignShareAwardDialog(ctx).setOnBtnClickListener(object : SignShareAwardDialog.BtnClickListener {

            override fun onConfirmClick() {

            }

            override fun onCloseClick() {
                getSignInfo()
            }

        })
        signShareAwardDialog?.show()
    }


}