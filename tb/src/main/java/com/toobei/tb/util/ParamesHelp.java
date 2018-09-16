package com.toobei.tb.util;

import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.toobei.tb.MyApp;

import java.util.HashMap;
import java.util.Map;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/17
 */

public class ParamesHelp {

    private Map<String, String> mMap;

    public ParamesHelp() {
        mMap = new HashMap<>();
    }

    public ParamesHelp put(String key, String value) {
        if (!TextUtil.isEmpty(value)) mMap.put(key, value);
        return this;
    }

    public Map<String, String> build(boolean isHttpPostParames) {
        if (isHttpPostParames) {
            MyApp.getInstance().getHttpService().initSign(mMap);
        }
        return mMap;
    }
    
    
    public Map<String, String> build() {
        return build(true);
    }

}