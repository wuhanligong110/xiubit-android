package com.toobei.common.utils;

import com.toobei.common.view.timeselector.Utils.TextUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by yanglin on 2017/2/22.
 * 文本格式化工具类
 */

public class TextFormatUtil {

    /**
     * 将字符串保留小数点后两位 0.00 格式
     *
     * @param str
     * @return
     */
    public static String formatFloat2zero(String str) {
        String tempStr = str;
        if (TextUtil.isEmpty(str)) {
            return "0.00";
        }
        try {
            double d = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("0.00");
            tempStr = String.valueOf(df.format((d)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return tempStr;
    }

    /**
     * 将字符串保留小数点后1位 0.0 格式
     *
     * @param str
     * @return
     */
    public static String formatFloat1zero(String str) {
        if (TextUtil.isEmpty(str)) {
            return "0.0";
        }
        double d = Double.parseDouble(str);
        DecimalFormat df = new DecimalFormat("0.0");
        return String.valueOf(df.format((d)));

    }

    /**
     * 保留整型
     *
     * @param str
     * @return
     */
    public static String formatInt(String str) {
        if (TextUtil.isEmpty(str)) {
            return "0";
        }
        double d = Double.parseDouble(str);
        DecimalFormat df = new DecimalFormat("0");
        return String.valueOf(df.format((d)));

    }


    /**
     * 功能：空为0.00 大于万，以万计 小于万，保留两位小数
     *
     * @param number
     * @return
     */
    public static String formatToWan(String number) {
        if (number == null || number.length() == 0) {
            return "0.00";
        }
        Double d = 0.00;
        try {
            d = Double.parseDouble(number);

            if (d >= 10000) {
                return String.format("%.2f", d / 10000) + "万";
            } else if (d > 0) {
                return String.format("%.2f", d);
            } else {
                return String.format("%.2f", d);
            }

        } catch (Exception e) {
            return number;
        }
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        String str = s;
        return NumberFormat.getInstance().format(Double.parseDouble(str)).replace(",", "");
    }
}
