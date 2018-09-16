package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import kotlinx.android.synthetic.main.activity_bind_success_and_withdraw.*

/**
 * Created by 11191 on 2018/6/1.
 */
class BindSuccessAndwithDrawActivity : NetBaseActivity() {
    override fun getContentLayout(): Int = R.layout.activity_bind_success_and_withdraw

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        headerLayout.showTitle("绑定银行卡")
        headerLayout.hideLeftBackButton()
        goWithDraw.setOnClickListener { val intent = Intent(ctx, WithdrawActivity::class.java)
            skipActivity(ctx, intent) }
    }
}