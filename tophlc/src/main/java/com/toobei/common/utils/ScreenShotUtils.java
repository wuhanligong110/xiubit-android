package com.toobei.common.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

/**
 * 公司: tophlc
 * 类说明: 截屏工具类
 *
 * @author hasee-pc
 * @time 2017/7/3
 */

public class ScreenShotUtils {


    /**
     * 截取整个屏幕
     */
    public static Bitmap shot(Activity activity){
        View tempView = activity.getWindow().getDecorView();
        tempView.setDrawingCacheEnabled(true);

        Bitmap tempBitmap = Bitmap.createBitmap(tempView.getDrawingCache());
        tempView.destroyDrawingCache();
        return tempBitmap;
    }
    /**
     * 截取某个视图
     * @param targetView
     */
    public static Bitmap shot(Activity activity,View targetView){
        View tempView = null;
        if (targetView == null){
            tempView = activity.getWindow().getDecorView();
        }else {
            tempView = targetView;
        }
        tempView.setDrawingCacheEnabled(true);

        Bitmap tempBitmap = Bitmap.createBitmap(targetView.getDrawingCache());
        targetView.destroyDrawingCache();
        return tempBitmap;
    }
}
