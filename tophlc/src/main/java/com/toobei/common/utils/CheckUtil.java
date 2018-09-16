package com.toobei.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/9/11
 */

public class CheckUtil {
    /**
     * 判断输入字符是否为有效汉字
     * @param str 字符
     * @return 是否合法汉字
     */
    public static boolean isValidHan(String str) {
        String regex = "[\u4e00-\u9fa5]*";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static boolean isNumer(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
