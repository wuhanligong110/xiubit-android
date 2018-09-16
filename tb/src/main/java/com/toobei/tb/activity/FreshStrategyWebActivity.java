package com.toobei.tb.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toobei.common.service.TopAppObject;
import com.toobei.tb.MyApp;
import com.toobei.tb.util.C;

import org.xsl781.utils.Logger;

/**
 * 公司: tophlc
 * 类说明:  新手攻略
 *
 * @author qingyechen
 * @time 2016/12/22 0022 下午 3:46
 */
public class FreshStrategyWebActivity extends WebActivityCommon {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mWebView.setRedirectUsable(false);
    }

    @Override
    public TopAppObject getAppObject() {
        return new FreshStrategyAppObject();
    }

    protected class FreshStrategyAppObject extends MyTopAppObject {




        @JavascriptInterface
        public void register(){
            showActivity(ctx,RegisterPhone.class);
        }

        /**
         * 功能： //  2016/12/7 0007  产品详情页自己购买产品
         * 产品详情页面（新手标收益最高产品）
         *
         * @param orgNoJson {"url":"产品url";"productId":"productId"}
         */
        @Override
        @JavascriptInterface
        public void buyTBProduct(String orgNoJson) {

            JSONObject jsonObject = JSON.parseObject(orgNoJson);
            String url = jsonObject.getString("url");
            String productId = jsonObject.getString("productId");
            Intent intent = new Intent(ctx, ProductInfoWebActivity.class);
            intent.putExtra("productId", productId);
            intent.putExtra("url", url);

//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        /**
         * V2.0.3
         * 新手攻略->绑卡
         */

        @Override
        @JavascriptInterface
        public void bindCardAuthenticate() {
            Logger.d("bindCardAuthenticate");
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
            if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) {
                showActivity(ctx, LoginActivity.class);
            } else {

                showActivity(ctx, MyQRCodeActivity.class);
            }


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK) {
            mWebView.reload(); //绑卡成功后刷新
        }
    }
}
