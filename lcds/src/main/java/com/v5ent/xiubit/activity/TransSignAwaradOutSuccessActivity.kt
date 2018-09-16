package com.v5ent.xiubit.activity

import android.os.Bundle
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import kotlinx.android.synthetic.main.activity_trans_sign_award_out_success.*

/**
 * Created by hasee-pc on 2017/11/23.
 */
class TransSignAwaradOutSuccessActivity : NetBaseActivity() {
    override fun getContentLayout(): Int = R.layout.activity_trans_sign_award_out_success

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val money = intent.getStringExtra("money")
        headerLayout.showTitle("转出说明")
        headerLayout.hideLeftBackButton()
        headerLayout.showRightTextButton("完成",{ finish() })

        tranSuccessTv.text = "成功转出${money}元至貅比特账户余额"
    }
}