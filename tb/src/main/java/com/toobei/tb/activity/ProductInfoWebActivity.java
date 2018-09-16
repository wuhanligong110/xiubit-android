package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.entity.ShareContentEntity;
import com.toobei.common.service.TopAppObject;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.entity.ProductDetailedBuyModel;
import com.toobei.tb.service.JumpOrgService;

import org.xsl781.utils.Logger;

/**
 * 公司: tophlc
 * 类说明：通用网页
 *
 * @date 2015-10-28
 */
public class ProductInfoWebActivity extends WebActivityCommon {
    private long lastClickTime;
    private String mProductId;
    private String mOrgName;
    private String orgNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headerLayout.showRightImageButton(R.drawable.img_share, this);
        mProductId = getIntent().getStringExtra("productId");
        mOrgName = getIntent().getStringExtra("orgName");
        orgNum = getIntent().getStringExtra("orgNum");

        mWebView.setRedirectUsable(false);   //跳转其他网页时新起activity
        mWebView.setVerticalScrollbarOverlay(false);// 隐藏滑条
        String url = mWebView.getUrl();
        mWebView.setHorizontalScrollbarOverlay(false);
        // 设置腾讯webView 内核不显示滑动条
        // TODO: 2016/12/22 0022  why null??  因为android 7.0用不了x5 内核??
//        IX5WebViewExtension x5WebViewExtension = mWebView.getX5WebViewExtension();
//        if (x5WebViewExtension != null) {
//            x5WebViewExtension.setVerticalTrackDrawable(null); //隐藏滑块
//        }

        if (getIntent().getBooleanExtra("JumpToBuy",false)){  //直接自动购买
            JumpOrgService jumpOrgService = new JumpOrgService();
            Logger.e("ctx="+ctx+";orgNum="+orgNum+";mProductId="+mProductId+";mWebView="+mWebView+";mOrgName="+mOrgName);
            jumpOrgService.checkAndJumpToBuyProduct(ctx, orgNum, mProductId, mWebView, mOrgName);
        }
    }


    

    private void productShare(final String productId, final UpdateViewCallBack<ShareContent> callBack) {
        if (shareContent != null) {
            callBack.updateView(null, shareContent);
            return;
        }
        new MyNetAsyncTask(ctx, false) {
            ShareContentEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().productShare(MyApp.getInstance().getLoginService().token, productId);
            }

            @Override
            protected void onPost(Exception e) {
                // 成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        shareContent = response.getData().getShareContent();
                    } else {
                        ToastUtil.showCustomToast(response.getMsg());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
                callBack.updateView(e, shareContent);
            }
        }.execute();
    }

    @Override
    public TopAppObject getAppObject() {
        return new ProductInfoAppObject();
    }

    protected class ProductInfoAppObject extends MyTopAppObject {
        /**
         * 跳转机构平台
         *
         * @param orgNoJson {"orgNo":"平台id"}
         */
        @Override
        @JavascriptInterface
        public void getAppPlatfromDetail(String orgNoJson) {

            Intent intent = new Intent(ctx, OrgInfoDetailActivity.class);
            JSONObject jsonObject = JSON.parseObject(orgNoJson);
            String orgNumber = jsonObject.getString("orgNo");
            intent.putExtra("orgName", mOrgName);  //机构名
            intent.putExtra("orgNumber", orgNumber);
            startActivity(intent);
        }

        @Override
        @JavascriptInterface
        public void buyProduct(String str) {
            Long currentTime = System.currentTimeMillis();
            if ((currentTime - lastClickTime) > 1000) {
                lastClickTime = currentTime;
                ProductDetailedBuyModel model = JSON.parseObject(str, ProductDetailedBuyModel.class);
                if (model != null) {
                    JumpOrgService jumpOrgService = new JumpOrgService();
                    jumpOrgService.checkAndJumpToBuyProduct(ctx, model.getOrgNo(), model.getProductId(), mWebView, model.getOrgName());
                }
            }
        }
    }

    /**
     * 判断登录状态 true：已登录；false ：未登录
     *
     * @return
     */
    public boolean isLogined() {
        return MyApp.getInstance().getLoginService().isCachPhoneExist();
    }

}
