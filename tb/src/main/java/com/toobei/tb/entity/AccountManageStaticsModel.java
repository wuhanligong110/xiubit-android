package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;


/**
 * 平台账户管理统计
 */
public class AccountManageStaticsModel extends BaseEntity {

    private static final long serialVersionUID = -8256196047385680387L;
    private String bindOrgAccountCount;//	已绑定账户数量	string
    private String unBindOrgAccountCount;//	未绑定账户数量	string

    public String getBindOrgAccountCount() {
        return bindOrgAccountCount;
    }

    public void setBindOrgAccountCount(String bindOrgAccountCount) {
        this.bindOrgAccountCount = bindOrgAccountCount;
    }

    public String getUnBindOrgAccountCount() {
        return unBindOrgAccountCount;
    }

    public void setUnBindOrgAccountCount(String unBindOrgAccountCount) {
        this.unBindOrgAccountCount = unBindOrgAccountCount;
    }
}