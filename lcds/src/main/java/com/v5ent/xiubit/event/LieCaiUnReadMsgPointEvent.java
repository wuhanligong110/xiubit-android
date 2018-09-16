package com.v5ent.xiubit.event;

import com.toobei.common.utils.UpdateViewCallBack;

/**
 * 公司: tophlc
 * 类说明: 理财未读消息
 *
 * @author qingyechen
 * @time 2016/12/15 0015 下午 2:45
 */
public class LieCaiUnReadMsgPointEvent {
    public UpdateViewCallBack callBack;

    public LieCaiUnReadMsgPointEvent(UpdateViewCallBack callBack) {
        this.callBack = callBack;
    }
}
