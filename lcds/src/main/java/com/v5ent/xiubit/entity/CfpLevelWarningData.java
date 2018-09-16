package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/5/5
 */

public class CfpLevelWarningData extends BaseEntity{

    private static final long serialVersionUID = -1128663230436902425L;

   private String cfpLevelContent;	//职级内容	string
   private String cfpLevelDetail;	//职级详情	string
   private String now;	//系统当前时间	string	yyyy-MM-dd HH:mm:ss
   private String cfpLevelTitle;  //标题
   private String cfpLevelTitle2;  //距离下月升级

    public String getCfpLevelTitle2() {
        return cfpLevelTitle2;
    }

    public void setCfpLevelTitle2(String cfpLevelTitle2) {
        this.cfpLevelTitle2 = cfpLevelTitle2;
    }

    public String getCfpLevelContent() {
        return cfpLevelContent;
    }

    public void setCfpLevelContent(String cfpLevelContent) {
        this.cfpLevelContent = cfpLevelContent;
    }

    public String getCfpLevelDetail() {
        return cfpLevelDetail;
    }

    public void setCfpLevelDetail(String cfpLevelDetail) {
        this.cfpLevelDetail = cfpLevelDetail;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getCfpLevelTitle() {
        return cfpLevelTitle;
    }

    public void setCfpLevelTitle(String cfpLevelTitle) {
        this.cfpLevelTitle = cfpLevelTitle;
    }
}
