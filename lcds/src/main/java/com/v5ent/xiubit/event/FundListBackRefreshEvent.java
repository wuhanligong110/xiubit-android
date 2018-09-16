package com.v5ent.xiubit.event;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/24
 */

public class FundListBackRefreshEvent {
    public boolean doRefresh;
    public FundListBackRefreshEvent(boolean doRefresh){
       this.doRefresh = doRefresh;
   }
}
