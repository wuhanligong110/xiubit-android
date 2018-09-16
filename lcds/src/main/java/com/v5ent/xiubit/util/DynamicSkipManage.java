package com.v5ent.xiubit.util;

import android.content.Intent;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.view.timeselector.Utils.TextUtil;

import org.xsl781.utils.Logger;

import java.util.HashMap;
import java.util.Map;


/**
 * 公司: tophlc
 * 类说明: 用于管理动态跳转页面
 *
 * @author yangLin
 * @time 2017/6/7
 */

public class DynamicSkipManage {
    public final static int SKIPTAG_PRODUCT_LIST = 11;  //产品分类
    public final static int SKIP_HUNTER_INFORMATION = 13; //理财资讯




    /**
     * app内部网页页面跳转
     *
     * @param ctx
     * @param json
     */
    public static void skipActivity(TopBaseActivity ctx, String json) {
        Logger.e("skipActivityFromWeb" + json);
        try {
            Logger.e("smsJump=====>" + json);
            JSONObject android = JSON.parseObject(json);
            //分段加密
//            String activityName =new String(Base64.decode(android.getString("name").getBytes(), Base64.DEFAULT)) ;
//            String paramsKey = new String(Base64.decode(android.getString("paramsKey").getBytes(),Base64.DEFAULT));
//            String params = new String(Base64.decode(android.getString("params").getBytes(),Base64.DEFAULT));
            String activityName = android.getString("name");
            String paramsKey = android.getString("paramsKey");
            String params = android.getString("params");
            String[] keyArr = new String[]{};
            if (!TextUtil.isEmpty(paramsKey)) {
                keyArr = paramsKey.split(",");
            }
            String[] valueArr = new String[]{};
            if (!TextUtil.isEmpty(params)) {
                valueArr = params.split(",");
            }

            Map<String, String> paramsMap = new HashMap<>();
            for (int i = 0; i < keyArr.length; i++) {
                paramsMap.put(keyArr[i], valueArr[i]);
            }

            DynamicSkipManage.skipActivity(ctx, activityName, paramsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从外部网页跳转app
     *
     * @param paramsString lcds://lcdsApp?bmFtZT1PcmdJbmZvRGV0YWlsQWN0aXZpdHkmb3JnTnVtYmVyPU9QRU5fR0FPU09VWUlfV0VC
     */
    public static void skipActivityFromWeb(TopBaseActivity ctx, String paramsString) {
        String str = new String(Base64.decode(paramsString.getBytes(), Base64.DEFAULT));
        skipActivity(ctx, str);
       
        return;
        //链接键值对拼接格式
//        try {
//            String str =new String(Base64.decode(paramsString.getBytes(), Base64.DEFAULT));
//            Logger.e("smsJump" + str);
//            String[] params = str.split("&");
//            Map<String, String> paramsMap = new HashMap();
//            String activityName = "";
//            for (String param: params){
//                if (param.contains("name=")){
//                    activityName = param.replace("name=","");
//                }else {
//                    String[] split = param.split("=");
//                    paramsMap.put(split[0],split[1]);
//                }
//            }
//            skipActivity(ctx,activityName,paramsMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * @param activityName 根据activityName 获取activity的Class
     * @param paramesMap   跳转参数字符串，每个参数以“，”分隔
     */
    public static void skipActivity(TopBaseActivity ctx, String activityName, Map<String, String> paramesMap) {
        Class activityClass;
        try {
            activityClass = Class.forName("com.v5ent.xiubit.activity." + activityName);
            Intent intent = new Intent(ctx, activityClass);
            for (String key : paramesMap.keySet()) {
                String value = paramesMap.get(key);
                if (value.contains("Int_")) {
                    intent.putExtra(key, Integer.parseInt(value.replace("Int_", "").trim()));
                } else if (value.contains("Boolean_")) {
                    intent.putExtra(key, Boolean.parseBoolean(value.replace("Boolean_", "").trim()));
                } else {
                    intent.putExtra(key, value);
                }
            }
            ctx.startActivityForResult(intent,0x666);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
