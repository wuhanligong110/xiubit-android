package com.toobei.tb

import android.content.Intent
import com.toobei.common.TopBaseActivity
import com.toobei.common.TopNetBaseActivity
import com.toobei.tb.activity.GestureActivity

/**
 * 公司: tophlc
 * 类说明:  用于网络请求，带状态切换，用来替代MyNetworkBaseActivity
 *
 * @author yangLin
 * @time 2017/10/12
 */
abstract class NetBaseActivity : TopNetBaseActivity() {

    override fun getGestureActivityIntent(activity: TopBaseActivity?): Intent {
            return Intent(ctx, GestureActivity::class.java)
    }
    
}