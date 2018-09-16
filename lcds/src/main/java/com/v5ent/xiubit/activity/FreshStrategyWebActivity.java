package com.v5ent.xiubit.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toobei.common.service.TopAppObject;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.util.C;

import org.xsl781.utils.Logger;

/**
 * 公司: tophlc
 * 类说明:  Activity-新手攻略
 *
 * @author qingyechen
 * @time 2016/12/22 0022 下午 3:46
 */
public class FreshStrategyWebActivity extends WebActivityCommon {


    @Override
    public TopAppObject getAppObject() {
        return new FreshStrategyAppObject();
    }

    protected class FreshStrategyAppObject extends MyTopAppObject {


        /**
         * 功能： //  2016/12/7 0007  产品详情页自己购买产品
         * 产品详情页面（新手标收益最高产品）
         *
         * @param orgNoJson {"orgNo":"平台id"}
         */
        @Override
        @JavascriptInterface
        public void buyTBProduct(String orgNoJson) {
            JSONObject jsonObject = JSON.parseObject(orgNoJson);
            String url = jsonObject.getString("url");
            String orgName = jsonObject.getString("orgName");
            String productId = jsonObject.getString("productId");
            String orgNumber = jsonObject.getString("orgNo");
//            Logger.e("url = " + url + "orgName=" + orgName + "productId =" + productId + "orgNumber =" + orgNumber);
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);

            if (isInstallApp(ctx, "com.toobei.tb")) {
//                ToastUtil.showCustomToast("T呗已安装");
                Intent intent = new Intent();
                ComponentName comp = new ComponentName("com.toobei.tb", "com.toobei.tb.activity.MainActivity");
                intent.setComponent(comp);
                intent.putExtra("FromAppTag", "LCDS_TO_ORG_PRODECT");
                intent.putExtra("ProductInfoUrl", url);
                intent.putExtra("ProductId", productId);
                intent.putExtra("orgName", orgName);
                intent.putExtra("orgNum", orgNumber);
                intent.putExtra("userPhoneNum", MyApp.getInstance().getLoginService().curPhone);
                intent.putExtra("LcdsToken", MyApp.getInstance().getLoginService().token);
                intent.setAction("com.toobei.tb.activity.MAIN");
                startActivity(intent);
            } else {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }

        /**
         * V2.0.3
         * 新手攻略->绑卡
         */

        @Override
        @JavascriptInterface
        public void bindCardAuthenticate() {
            Logger.e("bindCardAuthenticate");
            if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) {
                showActivity(ctx, LoginActivity.class);
            } else {
            startActivityForResult(new Intent(ctx, CardManagerAdd.class), C.REQUEST_FRESH_STRATERY);
            }
        }

        /**
         * V2.0.3
         * 新手攻略->邀请用户
         */
        @Override
        @JavascriptInterface
        public void invitedCustomer() {
            Logger.e("invitedCustomer");
            showActivity(ctx, InviteCustomerQr.class);

        }

        /**
         * V2.0.3
         * 新手攻略->邀请理财师
         */
        @Override
        @JavascriptInterface
        public void invitedCfg() {
            Logger.e("invitedCfg");
            showActivity(ctx, InviteCfpQr.class);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_OK) {
            mWebView.reload(); //绑卡成功后刷新
        }
    }


    boolean isInstallApp(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
