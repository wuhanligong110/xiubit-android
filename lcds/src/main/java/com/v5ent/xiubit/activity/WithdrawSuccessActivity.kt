package com.v5ent.xiubit.activity

import android.os.Bundle
import com.toobei.common.entity.BankCardInfo
import com.toobei.common.utils.TextFormatUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import kotlinx.android.synthetic.main.activity_withdraw_success_new.*

/**
 * Created by 11191 on 2018/5/11.
 */
class WithdrawSuccessActivity : NetBaseActivity() {
    override fun getContentLayout(): Int = R.layout.activity_withdraw_success_new
    lateinit var bankCardInfo : BankCardInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bankCardInfo = intent.getSerializableExtra("bankCardInfo") as BankCardInfo
        initView()
    }

    private fun initView() {
        headerLayout.showTitle("提现")
        inAccountDateTv.text = "预计${bankCardInfo?.withdrawRemark?:""}"
        withdrawMoneyTv.text = "¥${TextFormatUtil.formatFloat2zero(intent.getStringExtra("withdrawMonty" ))}"
        chargeMoneyTv.text = "¥${bankCardInfo?.fee?:"0.00"}"

        var bankCardNo = bankCardInfo.bankCard
        var bankCardDescribe = ""
        if (!bankCardNo.isNullOrBlank() && bankCardNo.length > 6) {
            bankCardDescribe = "${bankCardInfo.bankName} (${bankCardNo.substring(bankCardNo.length - 4)})";
        }
        bankinfoTv.text = bankCardDescribe

        finishBtn.setOnClickListener {finish() }
    }
}
