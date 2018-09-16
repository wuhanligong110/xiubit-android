package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.toobei.common.entity.AccountHomeData
import com.toobei.common.entity.BankCardInfo
import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.WithDrawApi
import com.toobei.common.utils.KeyboardVisibilityEvent
import com.toobei.common.utils.StatusBarUtil
import com.toobei.common.utils.TextFormatUtil
import com.toobei.common.utils.ToastUtil
import com.toobei.common.view.dialog.BottomDialog
import com.toobei.common.view.dialog.PromptDialogCommon
import com.toobei.common.view.dialog.PromptDialogMsg
import com.toobei.common.view.popupwindow.PayPasswordPopupWindow
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.event.WithDrawSuccessEvent
import com.v5ent.xiubit.service.CardCheckAndSetManager
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_withdraw_new.*
import org.greenrobot.eventbus.EventBus
import org.xsl781.utils.Logger
import org.xsl781.utils.SimpleTextWatcher

/**
 * Created by yangLin on 2018/5/10.
 */
class WithdrawActivity : NetBaseActivity() {
    private var balance = 0.00f
    private var balanceStr = "0.00"
    private var inputNum = 0.00f
    private var hasBankCardInfo = false //是否有提现银行卡信息
    private var bankCardInfo: BankCardInfo? = null
    private var bankCardDescribe: String? = ""
    private var pop: PayPasswordPopupWindow? = null
    private var fee = 0f
    override fun getContentLayout(): Int = R.layout.activity_withdraw_new

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        balanceStr = TextFormatUtil.formatFloat2zero(intent.getStringExtra("accountBalance") ?: "0.00")
        balance = balanceStr.toFloatOrNull() ?: 0.00f
        CardCheckAndSetManager.reSetStatue()
        initView()
        loadData()
    }

    override fun initStatusBarStyle() {
        StatusBarUtil.setColorNoTranslucent(ctx, getResources().getColor(com.toobei.common.R.color.top_title_bg));
    }

    private fun initView() {
        headerLayout.showTitle("提现")
        initBottomDialog()


        canWithdrawMoneyTv.text = "¥$balanceStr"
        withdrawEt.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                super.afterTextChanged(editable)
                //  禁止超过小数点后两位输入----------------------------------
                val ACCURACYNUM = 2  //允许的小数点后位数
                var temp = editable.toString().trim();
                //如果数据以小数点开头，就清空。因为手机的处理速度，怎么也比手动输入的速度快，所以给人感觉是：开头输小数点，输不进去
                if (editable.toString().startsWith(".")) {
                    withdrawEt.setText("");
                }

                var posDot = temp.indexOf(".");
                if (posDot > 0) {

                    if (temp.length - posDot - 1 > ACCURACYNUM) {
                        editable?.delete(posDot + ACCURACYNUM + 1, posDot + 2 + 2);
                    }
                }
//------------------------------------------------------------------
                if (editable.toString().length == 0) {
                    hideErrorMsg()
                    withdrawBtn.isEnabled = false
                    return
                }
                inputNum = editable.toString().toFloatOrNull() ?: 0.00f
                switchAllWithdrawVisible()
                if (inputNum > balance) {
                    showErrorMsg("输入金额大于可提现金额")
                } else if (inputNum < 20) {
                    showErrorMsg("输入金额小于最小提现金额￥20.00")
                } else {
                    hideErrorMsg()
                }

                switchAllWithdrawVisible()

            }
        })

        KeyboardVisibilityEvent.setEventListener(this) { isOpen ->
            if (isOpen) {
                upScrollView()
                Logger.e("KeyboardIsOpen" + isOpen)
            }
        }

        setupUIListenerAndCloseKeyboard(scrollV)
        clickEvent()

    }

    private fun initBottomDialog() {
        var quesDialog: BottomDialog? = null
        quesDialog = BottomDialog.create(supportFragmentManager)
                .setLayoutRes(R.layout.withactivity_bottom_dialog)
                .setViewListener { v: View? ->
                    v?.findViewById<View>(R.id.withdrawRecordTv)?.setOnClickListener {
                        showActivity(ctx, WithdrawList::class.java)
                        quesDialog?.dismiss()
                    }
                    v?.findViewById<View>(R.id.withdrawQuseTv)?.setOnClickListener {
                        ToastUtil.showCustomToast("提现常见问题")
                        quesDialog?.dismiss()
                    }
                    v?.findViewById<View>(R.id.cancleTv)?.setOnClickListener {
                        quesDialog?.dismiss()
                    }
                }

        headerLayout.showRightImageButton(R.drawable.img_question_white, { quesDialog.show() })
    }

    private fun switchAllWithdrawVisible() {
        allWithdraw.visibility = if (balance > 20 && inputNum < (balance - 1)) View.VISIBLE else View.GONE
    }

    private fun upScrollView() {
        Handler().post { scrollV.smoothScrollTo(0, scrollV.getHeight()) }
    }

    private fun hideErrorMsg() {
        errorMsgTv.visibility = View.GONE
        remindLl.visibility = View.VISIBLE
        withdrawBtn.isEnabled = true
    }

    private fun showErrorMsg(str: String) {
        remindLl.visibility = View.GONE
        errorMsgTv.visibility = View.VISIBLE
        errorMsgTv.text = str
        withdrawBtn.isEnabled = false
    }


    private fun loadData() {
        if (balance <= 0) {
            RetrofitHelper.getInstance().retrofit.create(WithDrawApi::class.java).myaccount(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<BaseResponseEntity<AccountHomeData>>() {
                        override fun bindViewWithDate(response: BaseResponseEntity<AccountHomeData>) {
                            balanceStr = response.data.totalAmount;
                            balance = balanceStr.toFloatOrNull() ?: 0.00f
                            canWithdrawMoneyTv.text = "¥$balanceStr"
                            if (balance < 20) allWithdraw.visibility = View.INVISIBLE
                            else allWithdraw.visibility = View.VISIBLE
                        }

                    })
        }
        RetrofitHelper.getInstance().retrofit.create(WithDrawApi::class.java).getWithdrawBankCard(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BankCardInfo>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<BankCardInfo>) {
                        bankCardInfo = response.data
                        var bankName = response.data.bankName
                        var bankCardNo = response.data.bankCard
                        withdrawRemarkTv.text = response.data.withdrawRemark
                        if (!bankCardNo.isNullOrBlank() && bankCardNo.length > 6) {
                            bankCardDescribe = "$bankName (${bankCardNo.substring(bankCardNo.length - 4)})";
                        }
                        bankCardInfoTv.text = bankCardDescribe
                        hasBankCardInfo = !response.data.isNeedkaiHuHang
                        fee = response.data.fee.toFloatOrNull() ?: 0f
                        if (fee == 0f) {
                            chargeMoneyTv.text = "免手续费"
                        } else {
                            chargeMoneyTv.text = "额外扣除手续费 ￥${response.data.getFee()}"
                        }
                    }

                })

    }


    private fun clickEvent() {
        var remindStr1 = "工作日当天下午2点前提现当天到账，2点后提现次日到账。周五(工作日)2点后和节假日提现顺延至下个工作日到账。"
        var remindStr2 = "每月可享受1次免费提现服务，超过1次，每次1元手续费。"
        var remindStr3 = "暂时只支持绑定一张银行卡，提现至其他银行卡，请联系客服（400-888-6987）更换银行卡。"

        fun outPop(str: String) {
            PromptDialogMsg(ctx, str, "我知道了").show()
        }
        remindIv1.setOnClickListener { outPop(remindStr1) }
        remindIv2.setOnClickListener { outPop(remindStr2) }
        remindIv3.setOnClickListener { outPop(remindStr3) }
        allWithdraw.setOnClickListener {
            allWithdraw()
        }
        withdrawBtn.setOnClickListener {
            fun checkAndInputPw() {
                inputPayPass()
            }
            /**用户输入的金额大于最大可提现金额-1元手续费，弹框提示：剩余余额不足以扣除提现手续费￥1.00，最大可提现金额xxxx。是否全部提现？
            点击全部提现，将金额修改为最大可提现金额，弹出密码输入框，弹出键盘，方便用户进行密码校验操作。**/
            if (inputNum > (balance - fee)) {
                var promptStr = "剩余余额不足以扣除提现手续费￥${TextFormatUtil.formatFloat2zero("$fee")}，最大可提现金额${TextFormatUtil.formatFloat2zero((balance - fee).toString())}。是否全部提现？"
                PromptDialogCommon(ctx, "", promptStr, "全部提现", "取消").setBtnPositiveClickListener {
                    allWithdraw()
                    checkAndInputPw()
                }.show()
            } else {
                checkAndInputPw()
            }


        }

    }

    private fun allWithdraw() {
        //全部提现，显示提现金额是去除了手续费之后的数值
        var inputStr = balanceStr
        if (fee > 0) {
            inputStr = (balanceStr.toFloat() - 1).toString()
        }
        withdrawEt.setText(inputStr)
    }

    private fun inputPayPass() {
        pop = PayPasswordPopupWindow(ctx, inputNum.toString(), fee, object : PayPasswordPopupWindow.CallBack {
            override fun OnPayCompleted(isPassed: Boolean) {
                if (isPassed) {  //确认交易密码成功
                    //有提现银行卡信息，则提现，成功后跳转成功页面，没有则跳转填写银行卡信息接口
                    if (hasBankCardInfo) {
                        withdrawMoney()
                    } else {
                        var intent = Intent(ctx, WithDrawCardInfoSetActivity::class.java)
                        intent.putExtra("bankCardInfo", bankCardInfo)
                        intent.putExtra("for_what", WithDrawCardInfoSetActivity.Companion.FOR_WITHDROW)
                        intent.putExtra("money", inputNum.toString())
                        startActivity(intent)
                        finish()
                    }
                    pop?.dismiss()
                }
            }

            override fun onBtnForgeLoginPasswdClick() {   //忘记交易密码
                showActivity(ctx, ResetPayPwdIdentity::class.java)
            }

        })
        pop?.showPopupWindow(withdrawBtn.rootView)
    }

    private fun withdrawMoney() {
        RetrofitHelper.getInstance().retrofit.create(WithDrawApi::class.java).userWithdrawRequest(
                ParamesHelp()
                        .put("bankCard", bankCardInfo?.bankCard ?: "")
                        .put("bankName", bankCardInfo?.bankName ?: "")
                        .put("amount", inputNum.toString())
                        .put("city", bankCardInfo?.city ?: "")
                        .put("kaihuhang", bankCardInfo?.kaiHuHang ?: "")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BaseEntity>>() {
                    override fun onNext(response: BaseResponseEntity<BaseEntity>) {
                        if (response.code == "0") {
                            EventBus.getDefault().post(WithDrawSuccessEvent())
                            var intent = Intent(ctx, WithdrawSuccessActivity::class.java)
                            intent.putExtra("bankCardInfo", bankCardInfo)
                            intent.putExtra("withdrawMonty", inputNum.toString())
                            startActivity(intent)
                            finish()
                        } else {
                            var errorMsg = "因${response.errorsMsgStr},提现失败，请稍后重试。"

                            val errorMsgDialog = PromptDialogCommon(ctx, "提现失败", "因${response.errorsMsgStr},提现失败，请稍后重试。", true)
                            errorMsgDialog.show()
                            headerLayout.postDelayed(Runnable { if (errorMsgDialog.isShowing) errorMsgDialog.dismiss() }, 3000)
                        }

                    }

                    override fun bindViewWithDate(response: BaseResponseEntity<BaseEntity>) {}

                })
    }

    override fun setupUIListenerAndCloseKeyboard(view: View) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                com.toobei.common.utils.KeyboardUtil.hideKeyboard(ctx)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup && view.getId() != R.id.inputViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUIListenerAndCloseKeyboard(innerView)
            }
        }
    }

}