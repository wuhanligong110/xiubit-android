package com.toobei.tb.util;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.tb.activity.FreshStrategyWebActivity;
import com.toobei.tb.activity.MineMsgCenter;
import com.toobei.tb.activity.MineRedPacketsActivity;
import com.toobei.tb.activity.OrgInfoDetailActivity;
import com.toobei.tb.activity.ProductInfoWebActivity;
import com.toobei.tb.activity.ProductViewPagerActivity;
import com.toobei.tb.activity.WebActivityCommon;


/**
 * 公司: tophlc
 * 类说明: 用于管理动态跳转页面
 *
 * @author yangLin
 * @time 2017/6/7
 */

public class DynamicSkipManage {
    public final static int SKIPTAG_RED_ENVELOPES = 1;   //我的红包
    public final static int SKIPTAG_MINE_MSGCENTER = 2;  //消息中心
    public final static int SKIPTAG_ORGINFODETAIL = 3;  //平台详情页
    public final static int SKIPTAG_PRODUCT_LIST = 4;  //产品分类
    public final static int SKIPTAG_PRODUCT_DETIL = 5;  //产品详情
    public final static int SKIPTAG_NEW_TACTICS = 6; //新手攻略
    public final static int SKIPTAG_UNDERSTANDING_US = 7; //了解我们

    /**
     * 动态跳转规则
     * app内部网页跳转本地app页面web传递给app的参数格式：
     *{pageClass:"",android:"",ios:""}
     *第三方源跳转app内部页面web传递给app的参数格式：
     *Action=NativeAppLINE类LINEandroid参数LINEios参数，参数之间以,相隔
     */

    /**
     * @param pageTag      页面标志，ActivitySpDao 的 getValue 函数可以通过这个标志获取skipTag
     * @param paramsString 跳转参数字符串，每个参数以“，”分隔
     */
    public static void skipActivity(TopBaseActivity ctx, String pageTag, String paramsString) {
        int skipTag = ActivitySpDao.getInstance(ctx).getValue(pageTag);  //第一个参数是跳转页面标志
        String[] params = paramsString.split(",");
        Intent intent;
        switch (skipTag) {

            case SKIPTAG_RED_ENVELOPES:       //我的红包
                intent = new Intent(ctx, MineRedPacketsActivity.class);
                ctx.startActivity(intent);
                break;
            case SKIPTAG_NEW_TACTICS:       //新手攻略
                intent = new Intent(ctx, FreshStrategyWebActivity.class);
                intent.putExtra("url", C.URL_NEWERSTRATEGY);
                ctx.startActivity(intent);
                break;
            case SKIPTAG_UNDERSTANDING_US:       //了解我们
                WebActivityCommon.showThisActivity(ctx, C.URL_HOME_LEARN_ABOUT_US, "");
                break;
            case SKIPTAG_MINE_MSGCENTER:       //消息中心
                intent = new Intent(ctx, MineMsgCenter.class);
//                intent.putExtra("type",TYPE_NOTICE);  // TODO: 2017/6/8 这里记得转换为int
                ctx.startActivity(intent);
                break;
            case SKIPTAG_ORGINFODETAIL:       //平台详情页
                intent = new Intent(ctx, OrgInfoDetailActivity.class);
//                intent.putExtra("orgName", "");
//                intent.putExtra("orgNumber", orgNum);
                ctx.showActivity(ctx, intent);
                break;
            case SKIPTAG_PRODUCT_LIST:       //产品分类
                intent = new Intent(ctx, ProductViewPagerActivity.class);
                if (params != null && params.length >= 1) {
                    intent.putExtra("cateId", params[0]);
                }
                intent.putExtra("listType", ProductViewPagerActivity.ProductType.PRODUCTTYPE_DATE_LIMIT_TYPE);
                ctx.startActivity(intent);
                break;
            case SKIPTAG_PRODUCT_DETIL:  //产品详情
                intent = new Intent(ctx, ProductInfoWebActivity.class);
//                    intent.putExtra("url", );
                ctx.startActivity(intent);
                break;
        }
    }
}
