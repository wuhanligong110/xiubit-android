package com.toobei.tb.event;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class RefreshMineUiEvent {
    public boolean isLogin = false; //true-> login ; false->logOut

    public RefreshMineUiEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

}
