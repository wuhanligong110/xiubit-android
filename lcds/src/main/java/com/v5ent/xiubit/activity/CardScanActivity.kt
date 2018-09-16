package com.v5ent.xiubit.activity

import android.content.Intent
import com.toobei.common.TopBaseActivity
import com.toobei.common.activity.TopCardScanActivity
import com.toobei.common.manage.CardScanHelper
import java.io.File

/**
 * Created by yangLin on 2018/1/4.
 */
class CardScanActivity : TopCardScanActivity() {
    override fun upData(file: File) {
        showLoadProgress(true)
        CardScanHelper.scan(file, scanType, object : CardScanHelper.CallBack {
            override fun onSuccess() {
                showLoadContent()
                finish()
            }

            override fun onFailed() {
                showLoadContent()
            }

        })
    }


    override fun getGestureActivityIntent(activity: TopBaseActivity?): Intent = Intent(ctx, GestureActivity::class.java)

}