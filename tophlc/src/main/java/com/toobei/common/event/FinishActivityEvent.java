package com.toobei.common.event;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/19
 */

public class FinishActivityEvent {
    public Class activityClass;
    public FinishActivityEvent(Class activityClass){
        this.activityClass = activityClass;
    }
}
