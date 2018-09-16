package com.v5ent.xiubit.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/7/5
 */

public class CommonTabEntity implements CustomTabEntity{
    private String title;
    private int selectIconResId;
    private int unselectedIconResId;

    public CommonTabEntity(String title){
        this.title = title;
    }

    public CommonTabEntity(String title , int selectIconResId, int unselectedIconResId){
        this.title = title;
        this.selectIconResId = selectIconResId;
        this.unselectedIconResId = unselectedIconResId;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return unselectedIconResId;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unselectedIconResId;
    }
}
