package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 机构info
 */


public class TeamInfosBean extends BaseEntity {

    private static final long serialVersionUID = -5362782648408747023L;

    private String orgDescribe;               //	      orgDescribe	描述	string
    private String orgIcon;                   //	      orgIcon	头像	string
    private String orgMemberGrade;            //	      orgMemberGrade	职位	string
    private String orgMemberName;             //              姓名

    public String getOrgDescribe() {
        return orgDescribe;
    }

    public void setOrgDescribe(String orgDescribe) {
        this.orgDescribe = orgDescribe;
    }

    public String getOrgIcon() {
        return orgIcon;
    }

    public void setOrgIcon(String orgIcon) {
        this.orgIcon = orgIcon;
    }

    public String getOrgMemberGrade() {
        return orgMemberGrade;
    }

    public void setOrgMemberGrade(String orgMemberGrade) {
        this.orgMemberGrade = orgMemberGrade;
    }

    public String getOrgMemberName() {
        return orgMemberName;
    }

    public void setOrgMemberName(String orgMemberName) {
        this.orgMemberName = orgMemberName;
    }
}