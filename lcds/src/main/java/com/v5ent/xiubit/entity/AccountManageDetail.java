package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 平台账户管理列表 -钟灵-已实现
 */
public class AccountManageDetail extends BaseEntity {


    private static final long serialVersionUID = 258842879350196194L;
    //orgName=人人贷orgNumber=7777777
    private String orgName;// 平台名称	string
    private String orgNumber;//	机构编码	string




    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }


}
