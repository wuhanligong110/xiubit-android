package com.toobei.common.entity;


/**
 * 公司: tophlc
 * 类说明：消息 实体
 *
 * @date 2015-10-26
 */
public class MsgDetail extends BaseEntity {


    private static final long serialVersionUID = 8800953195818031535L;

//公告消息分页-陈春燕（已实现）

    public String link;//	string	链接
    public String message;//	string	内容

    //------------------------------------------------------------------------------//
    //个人消息分页-陈春燕（已实现）
    private String appType;           //      appType		number	端口（理财师，投资人）
    private String content;           //	        content		string	消息内容
    private String crtTime;           //	        crtTime		string	创建时间
    private String id;                //	        id		number	消息ID
    private String modifyTime;        //	        modifyTime		string	更新时间
    private String read;              //	        read		number	是否已读 1已读,0未读
    private String startTime;         //	        startTime		string	生效时间(前端显示该时间)
    private String status;            //	        status		number	状态
    private String userNumber;        //	        userNumber		string	用户ID


    private String linkBtnTxt;//		string	跳转按钮显示文案
    private String linkUrlKey;//		string	跳转地址key   myCfp_product 跳转我的理财师->理财师推荐产品   myCfp_platform->推荐平台


    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getLinkBtnTxt() {
        return linkBtnTxt;
    }

    public void setLinkBtnTxt(String linkBtnTxt) {
        this.linkBtnTxt = linkBtnTxt;
    }

    public String getLinkUrlKey() {
        return linkUrlKey;
    }

    public void setLinkUrlKey(String linkUrlKey) {
        this.linkUrlKey = linkUrlKey;
    }
}