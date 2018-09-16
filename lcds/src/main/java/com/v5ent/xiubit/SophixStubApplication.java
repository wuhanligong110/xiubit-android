package com.v5ent.xiubit; /**
17. * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
18. * 此类必须继承自SophixApplication，onCreate方法不需要实现。
19. * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
20. * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
21. * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
22. * 如有其它自定义改造，请咨询官方后妥善处理。
23. */

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * 稳健接入

 Sophix最新版本引入了新的初始化方式。

 原来的初始化方式仍然可以使用。只是新方式可以提供更全面的功能修复支持，将会带来以下优点：初始化与应用原先业务代码完全隔离，使得原先真正的Application可以修复，并且减少了补丁预加载时间等等。另外，新方式能够更完美地兼容Android 8.0以后版本。
 https://help.aliyun.com/document_detail/61082.html?spm=a2c4g.11186623.2.4.5dYVQU
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApp.class)
    static class RealApplicationStub {}

    static int patchVersion = 0;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = BuildConfig.VERSION_NAME;
        String IDSECRET = BuildConfig.hotfix_idSecret;
        String APPSECRET = BuildConfig.hotfix_appSecret;
        String RSASECRET = BuildConfig.hotfix_rsaSecret;
//        try {
//            appVersion = this.getPackageManager()
//                             .getPackageInfo(this.getPackageName(), 0)
//                             .versionName;
//        } catch (Exception e) {
//        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(IDSECRET, APPSECRET, RSASECRET)
                .setEnableDebug(true)
                .setUsingEnhance()
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch "+handlePatchVersion+" success!");
                            patchVersion = handlePatchVersion;
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch "+handlePatchVersion+" success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}
