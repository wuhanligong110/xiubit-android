package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/15.
 * 用于TabLayout的tabBean
 */
public class TabTypeBean extends BaseEntity{

    private static final long serialVersionUID = -6037540229178582397L;
    private String tabName;  //tab的text名称
    private String tabType;  //tab的值

    public TabTypeBean(String tabName, String tabType) {
        this.tabName = tabName;
        this.tabType = tabType;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }
}
