package com.v5ent.xiubit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.service.TopAppObject;
import com.toobei.common.view.MyShowTipsView;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.service.JumpJiuhuService;
import com.v5ent.xiubit.service.JumpThirdPartyService;
import com.v5ent.xiubit.service.LoginService;
import com.v5ent.xiubit.service.ProductService;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.view.popupwindow.RecommendSharePopupWindow;

import org.xsl781.utils.Logger;
import org.xsl781.utils.StringUtils;

/**
 * 公司: tophlc
 * 类说明：Activity-产品详情页-H5
 *
 * @date 2015-10-28
 */
public class ProductInfoWebActivity extends WebActivityCommon {
    private boolean isSetRecommend = false;
    private ProductDetail product;
    private View showTipV;
    private MyShowTipsView myShowTipsView;
    private String productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        product = (ProductDetail) getIntent().getSerializableExtra("product");
        productId = getIntent().getStringExtra("productId");

        mWebView.setRedirectUsable(false);//禁止重定向
        showTipV = findViewById(R.id.showTipV);
        //initShowTipsView();
        headerLayout.showRightTextButton("推荐", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtil.isEmpty(MyApp.getInstance().getLoginService().token)) {
                    MyApp.loginAndStay = true;
                    showActivity(ctx, LoginActivity.class);
                    return;
                }

                RecommendSharePopupWindow popuWindow = new RecommendSharePopupWindow(ctx, shareContent, RecommendSharePopupWindow.RECOMMENDTYPE_PRODUCT, new RecommendSharePopupWindow.RecommendShareListener() {
                    @Override
                    public void onRecommendCfg() {
                        Intent intent = new Intent(ctx, RecommendToCfgActivity.class);
                        intent.putExtra("recommendType", C.RECOMMEND_TYPE_PRODUCT);
                        intent.putExtra("productOrgId", product.getProductId());
//                        intent.putExtra("product", product);
                        startActivity(intent);
                    }

                    @Override
                    public void onRecommendCustomer() {
                        Intent intent = new Intent(ctx, RecommendToCustomerActivity.class);
                        intent.putExtra("recommendType", C.RECOMMEND_TYPE_PRODUCT);
                        intent.putExtra("productOrgId", product.getProductId());
//                        intent.putExtra("product", product);
                        startActivity(intent);
                    }
                });
                popuWindow.showPopupWindow(headerLayout);
            }

        });
    }

    /**
     * 新手引导图片
     *
     * @param y
     * @param height
     */
    private void initShowTipsView(int y, int height) {

//        showTipV.setTop(PixelUtil.dip2px(ctx, y) /*+headerLayout.getHeight() + SystemTool.getStatusBarHeight(ctx) - 25*/);
//       showTipV.setBottom(showTipV.getTop() + PixelUtil.dip2px(ctx, height));
//        
//
//        MyShowTipsView myShowTipsView = new MyShowTipsView(ctx, showTipV, GuideViewType.FRAGMENT_HOME_PRODUCT.getValue(),//
//                PixelUtil.dip2px(ctx, 60), PixelUtil.dip2px(ctx,-height-10), GuideViewType.FRAGMENT_HOME_PRODUCT.toString());
////        myShowTipsView.setDisplayOneTime(false);
//        myShowTipsView.show(ctx);
    }


    @Override
    public TopAppObject getAppObject() {
        return new ProductInfoAppObject();
    }

    protected class ProductInfoAppObject extends MyTopAppObject {

        /**
         * 传递尺寸
         *
         * @param json {"left":0,"top":526,"width":360,"height":50}
         */
        @Override
        @JavascriptInterface
        public void getPositionCoordinate(String json) {
            String guildHeight = JSON.parseObject(json).getString("height");
            String guildTop = JSON.parseObject(json).getString("top");
            final int height = (int) StringUtils.toDouble(guildHeight);
            final int top = (int) StringUtils.toDouble(guildTop);
            Logger.d("showInvestCaseTip==>>height==" + height);


            ctx.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    initShowTipsView(top, height);
                }
            });

        }

        /**
         * 推荐产品动作|取消推荐
         */
        @Override
        @JavascriptInterface
        public void setApplhlcsProRecommendSucc() {
            isSetRecommend = true;
            //    org.xsl781.utils.Logger.d("isSetRecommend== =" + isSetRecommend);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ProductService.notifyUiAndData(product);
                }
            });

        }

        /**
         * 佣金计算
         */
        @Override
        @JavascriptInterface
        public void getApplhlcsCommissionCalc() {
            if (!LoginService.getInstance().isTokenExsit()) {
                startActivity(new Intent(ctx, LoginActivity.class));
                return;
            }
            Intent intent = new Intent(ProductInfoWebActivity.this, CalculatorActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        }

        /**
         * 自己购买
         *
         * @param orgNoJson {"url":"T呗产品详情url"}
         */
        @Override
        @JavascriptInterface
        public void buyTBProduct(final String orgNoJson) {  //自己购买


            JSONObject jsonObject = JSON.parseObject(orgNoJson);
            String url = jsonObject.getString("url");
            final String orgName = jsonObject.getString("orgName");
            final String productId = jsonObject.getString("productId");
            final String orgNumber = jsonObject.getString("orgNo");
            final String orgProductUrlSkipBindType = jsonObject.getString("orgProductUrlSkipBindType");  //跳转第三方是否需要先绑卡
            final boolean skipNeedCardBinded = !"0".equals(orgProductUrlSkipBindType);
            if (("OPEN_JIUFUQINGZHOU_WEB").equals(orgNumber)) {
                final String jfqzProductDetailUrl = jsonObject.getString("jfqzProductDetailUrl");
                final String thirdProductId = jsonObject.getString("thirdProductId");
                //跳转玖富产品详情页
                runOnUiThread(new Runnable() {  //在某些极垃圾山寨手机上，如果不是UI线程会崩溃 报错 Can't create handler inside thread that has not called Looper.prepare()
                    @Override
                    public void run() {
                        new JumpJiuhuService(skipNeedCardBinded,jfqzProductDetailUrl, thirdProductId, "", ctx).skipDetialPage();
                    }
                });
            }else {
                runOnUiThread(new Runnable() {  //在某些极垃圾山寨手机上，如果不是UI线程会崩溃 报错 Can't create handler inside thread that has not called Looper.prepare()
                    @Override
                    public void run() {
                        new JumpThirdPartyService(JumpThirdPartyService.JUMP_TYPE_BUY_PRODUCT, skipNeedCardBinded, ctx, orgName, orgNumber, productId).run();
                    }
                });
            }
        }

        /**
         * 跳转机构平台
         *
         * @param orgNoJson {"orgNo":"平台id"}
         */
        @Override
        @JavascriptInterface
        public void getAppPlatfromDetail(String orgNoJson) {

            Intent intent = new Intent(ctx, OrgInfoDetailActivity.class);
            intent.putExtra("orgName", product.getOrgName());  //机构名
            intent.putExtra("orgNumber", product.getOrgNumber());
            startActivity(intent);
        }

        /**
         * 推荐产品
         */
        @Override
        @JavascriptInterface
        public void productRecommend() {
            if (TextUtil.isEmpty(MyApp.getInstance().getLoginService().token)) {
                showActivity(ctx, LoginActivity.class);
                return;
            }
            Intent intent = null;
            if (product.getOrgIsstaticproduct().equals("1")) { //1 没有对接    0已经对接
                intent = new Intent(ctx, RecommendProductOrOrgNoBindActivity.class);

            } else {

                intent = new Intent(ctx, RecommendProductOrOrgActivity.class);
            }
            intent.putExtra("recommendType", C.RECOMMEND_TYPE_PRODUCT);
            intent.putExtra("productId", product.getProductId());
            intent.putExtra("product", product);
            ctx.startActivityForResult(intent, C.RECOMMEND_TYPE_PRODUCT);

        }

        /**
         * 显示新手提示
         */
        @Override
        @JavascriptInterface
        public void showInvestCaseTip(String jsonStr) {

//            JSONObject jsonObject = JSON.parseObject(jsonStr); //jsonStr=={"x":0,"y":245,"width":360,"height":111}
//            Logger.d("showInvestCaseTip==>>jsonStr==" + jsonStr.toString());
//            final int y = (int) StringUtils.toDouble(jsonObject.getString("y"));
//            final int height = (int) StringUtils.toDouble(jsonObject.getString("height"));
//            Logger.d("showInvestCaseTip==>>height==" + height + " y=== " + y);
//
//
//            ctx.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    initShowTipsView(y, height);
//                }
//            });

        }


        /**
         * 右上角分享，从web传入分享内容
         */
        @Override
        @JavascriptInterface
        public void getSharedContent(final String json) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Logger.e("product_share" + json);
                        shareContent = JSON.parseObject(json, ShareContent.class);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            isSetRecommend = true;
            finish();
        }
    }

    @Override
    public void finish() {
        if (isSetRecommend) {
            Intent intent = new Intent();
            intent.putExtra("product", product);
            setResult(RESULT_OK, intent);
        }
        super.finish();
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
