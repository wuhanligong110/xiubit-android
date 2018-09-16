package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import com.toobei.common.entity.BankCardInfo
import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.ResetPayPwdToken
import com.toobei.common.event.CardBindSuccessEvent
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CardBindApi
import com.toobei.common.utils.ToastUtil
import com.toobei.common.view.dialog.PromptDialogMsg
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.RegisterPwdNew.GET_RESET_SMS_CODE
import com.v5ent.xiubit.service.CardCheckAndSetManager
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bind_card_vcode.*
import org.greenrobot.eventbus.EventBus
import org.xsl781.utils.SimpleTextWatcher

/**
 * 绑卡流程里的验证手机号步骤
 * Created by 11191 on 2018/5/31.
 */
class BindCardPhoneVcodeActivity : NetBaseActivity() {
    var sendNum = 0

    override fun getContentLayout(): Int = R.layout.activity_bind_card_vcode

    private lateinit var bankCardInfo: BankCardInfo
    private lateinit var vcodePhoneNum: String
    private var seconds = 60

    private val mHandler = object : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                GET_RESET_SMS_CODE -> {
                    seconds--
                    if (seconds <= 0) { // 恢复验证码输入
                        seconds = 60
                        btnResend.isEnabled = true
                        btnResend.setTextColor(ContextCompat.getColor(ctx, com.toobei.common.R.color.text_blue_common))
                    } else {
                        btnResend.isEnabled = false
                        btnResend.setTextColor(ContextCompat.getColor(ctx, com.toobei.common.R.color.text_gray_common_title))
                        this.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000)
                    }
                    btnResend.text = "重新发送(" + seconds + "s)"
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bankCardInfo = intent.getSerializableExtra("bankCardInfo") as BankCardInfo
        vcodePhoneNum = intent.getStringExtra("vcodePhoneNum")  //银行预留号码，用于发送验证码
        initView()
        sendVcode()
    }


    private fun sendVcode() {
        if (sendNum > 10) {
            ToastUtil.showCustomToast("当天验证发送已超过10次，请明日再试")
            return
        }

        RetrofitHelper.getInstance().getRetrofit(true).create(CardBindApi::class.java).sendVcode(ParamesHelp()
                .put("mobile", vcodePhoneNum)
                .put("type", "0")
                .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BaseEntity>>() {

                    override fun onNext(response: BaseResponseEntity<BaseEntity>) {
                        sendNum++
                        if ("0" == response.code) {
                            mHandler.sendEmptyMessage(GET_RESET_SMS_CODE)
                        } else {
                            btnResend.isEnabled = true
                            btnResend.setTextColor(ContextCompat.getColor(ctx, com.toobei.common.R.color.text_blue_common))
                            btnResend.text = "发 送"
                            com.toobei.common.utils.ToastUtil.showCustomToast(response
                                    .errorsMsgStr)
                        }
                    }

                    override fun bindViewWithDate(response: BaseResponseEntity<BaseEntity>) {}

                })
    }


    private fun checkVcode(vcode: String) {
        RetrofitHelper.getInstance().getRetrofit(true).create(CardBindApi::class.java).checkVcode(ParamesHelp()
                .put("vcode", vcode)
                .put("mobile", vcodePhoneNum)
                .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<ResetPayPwdToken>>() {
                    override fun onNext(response: BaseResponseEntity<ResetPayPwdToken>) {
                        if (response.code == "0") {
                            bindCard()
                        } else {
                            com.toobei.common.utils.ToastUtil.showCustomToast(response
                                    .errorsMsgStr)
                        }
                    }

                    override fun bindViewWithDate(response: BaseResponseEntity<ResetPayPwdToken>) {}

                })
    }

    private fun bindCard() {

        RetrofitHelper.getInstance().getRetrofit(true).create(CardBindApi::class.java).bindCard(ParamesHelp()
                .put("bankCard", bankCardInfo.bankCard)
                .put("city", bankCardInfo.city)
                .put("idCard", bankCardInfo.idCard)
                .put("kaihuhang", bankCardInfo.city)
                .put("mobile", bankCardInfo.mobile)
                .put("userName", bankCardInfo.userName)
                .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BankCardInfo>>() {

                    override fun bindViewWithDate(response: BaseResponseEntity<BankCardInfo>) {

                        val event = CardBindSuccessEvent()
                        //流程判断
                        var intent: Intent
                        when (CardCheckAndSetManager.state) {
                            CardCheckAndSetManager.NO_CARD_HAS_PAYPWD -> {
                                event.flow_tag = CardCheckAndSetManager.NO_CARD_HAS_PAYPWD
                                EventBus.getDefault().post(event)
                                finish()
                            }
                            CardCheckAndSetManager.NO_CARD_NO_PAYPWD -> {
                                event.flow_tag = CardCheckAndSetManager.NO_CARD_NO_PAYPWD
                                EventBus.getDefault().post(event)
                                intent = Intent(ctx, PwdManagerInitPay::class.java)
                                skipActivity(ctx, intent)
                            }

                            else -> {
                                EventBus.getDefault().post(event)
                                intent = Intent(ctx, CardAddResultActivity::class.java)
                                intent.putExtra("bankCardInfo", bankCardInfo)
                                skipActivity(ctx, intent)

                            }
                        }


                    }

                })
    }

    private fun initView() {
        headerLayout.showTitle("验证手机号")
        btnComplete.isEnabled = false
        btnResend.isEnabled = false
        btnResend.text = "重新获取(60s)"
        btnResend.setTextColor(ContextCompat.getColor(ctx, com.toobei.common.R.color.text_gray_common))
        btnResend.setOnClickListener { sendVcode() }
        var phoneStr = ""
        if (vcodePhoneNum.length == 11) {
            phoneStr = vcodePhoneNum.replace(vcodePhoneNum.substring(3, 7), "****")
        } else {
            phoneStr = vcodePhoneNum
        }

        textTitlePrompt.text = "绑定银行卡需短信验证，请输入手机号${phoneStr}收到的验证码"

        btnComplete.setOnClickListener {
            checkVcode(cedtVcode.text.toString())
        }

        cedtVcode.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence?, i: Int, i2: Int, i3: Int) {
                btnComplete.isEnabled = cedtVcode.text.toString().length >= 4
            }
        })

        questionTv.setOnClickListener {
            PromptDialogMsg(ctx, "收不到验证码", resources.getString(com.toobei.common.R.string.vcode_ques_str), "我知道了")
                    .show()
        }
    }
}