package com.toobei.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.igexin.sdk.GTServiceManager;

/**
 * 公司: tophlc
 * 类说明: 接收推送服务事件
 * <p>
 * 从2.9.5.0版本开始，为了解决小概率发生的Android广播丢失问题，
 * 我们推荐应用开发者使用新的IntentService方式来接收推送服务事件（包括CID获取通知、透传消息通知等）
 * 在项目源码中添加一个继承自com.igexin.sdk.GTIntentService的类，
 * 用于接收CID、透传消息以及其他推送服务事件。请参考下列代码实现各个事件回调方法：
 * <p>
 * <p>
 * 关于原有广播方式和新的IntentService方式兼容性说明：
 * 1. 如果调用了registerPushIntentService方法注册自定义IntentService，则SDK仅通过IntentService回调推送服务事件；
 * 2. 如果未调用registerPushIntentService方法进行注册，则原有的广播接收器仍然可以继续使用。
 *
 * @author qingyechen
 * @time 2017/1/18 0018 下午 1:19
 */
public class TooBeiPushService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        GTServiceManager.getInstance().onCreate(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return GTServiceManager.getInstance().onStartCommand(this, intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return GTServiceManager.getInstance().onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GTServiceManager.getInstance().onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GTServiceManager.getInstance().onLowMemory();
    }


}
