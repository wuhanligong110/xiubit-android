package com.v5ent.xiubit.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.v5ent.xiubit.BuildConfig
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.R
import kotlinx.android.synthetic.main.dialog_env_select.*

/**
 * Created by hasee-pc on 2017/3/20.
 * 用于测试版启动时选择测试环境
 */

class EnvironmentVersionsSelectDiolog : Dialog(MyApp.getInstance().applicationContext, com.toobei.common.R.style.customDialog), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_env_select)
        setCancelable(false)
        window?.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        initView()
    }

    private fun initView() {
        var environment = "获取环境失败"
        when (BuildConfig.FLAVOR) {
            "dev" -> environment = "开发环境"
            "instant", "pre" -> environment = "预发布环境"
            "rel" -> environment = "线上环境"
        }
        textTitleTV.text = "V" + BuildConfig.VERSION_NAME + "_" + BuildConfig.VERSION_CODE
        textEnvTv.text = environment
        negativeBtn.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        dismiss()
    }
}
