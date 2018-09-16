package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import com.toobei.common.utils.SystemFunctionUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import kotlinx.android.synthetic.main.activity_third_org_bind.*

/**
 * Created by 11191 on 2018/6/6.
 */
class ThirdOrgBindFaileActivity : NetBaseActivity() {
    override fun getContentLayout(): Int = R.layout.activity_third_org_bind

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orgName = intent.getStringExtra("orgName")?:""
        headerLayout.showTitle("绑定账户")
        remindTv.text = "很抱歉，你已在${orgName}注册过账户，${orgName}暂不支持老用户绑定。"
        btn.setOnClickListener {
            var intent = Intent(ctx,MainActivity::class.java)
            intent.putExtra("skipTab","p1t0")
            skipActivity(ctx,intent)
        }

        phoneTv.setOnClickListener { SystemFunctionUtil.CallServicePhone(ctx,"400-888-6987") }
    }
}