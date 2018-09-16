package com.v5ent.xiubit.event;

/**
 * 公司: tophlc
 * 类说明: 完成所有新手任务
 *
 * @author qingyechen
 * @time 2016/12/9 0009 下午 6:57
 */
public class FinishAllFreshMisssionEvent {
    public boolean hasFinishAllMission=false;
     public FinishAllFreshMisssionEvent(boolean hasFinishAllMission) {
        this.hasFinishAllMission = hasFinishAllMission;
    }
}
