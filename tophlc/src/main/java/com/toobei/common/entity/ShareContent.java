package com.toobei.common.entity;

import android.text.TextUtils;

import com.toobei.common.TopApp;
import com.toobei.common.utils.TextUrlUtils;

import org.xsl781.utils.Logger;

public class ShareContent extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1480488379043255626L;

    public String rightItemName;// 右上角分享按钮文字
    private String shareDesc;// 分享描述
    private String shareImgurl;// 分享图标
    private String shareLink;//分享链接
    private String shareTitle;// 分享标题
    public ShareContent() {
        super();
    }

    public ShareContent(String shareTitle, String shareDesc, String shareLink, String shareImgurl) {
        super();
        this.shareTitle = shareTitle;
        this.shareDesc = shareDesc;
        setLink(shareLink);
        setShareImgurl(shareImgurl);

    }


    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getLink() {
        return shareLink;
    } public String getShareLink() {
        return shareLink;
    }

    public void setLink(String link) {
        if (link != null && !link.contains("http")) {  //如果只是一个没有域名的url则拼接域名
            link = TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + link;
        }
        if (link != null && !link.contains("&fromApp=liecai&os=Android")) {
            link = TextUrlUtils.addUrlStr(link, TopApp.getInstance().getHttpService().getShareUrlEndSuffix());
        }
        Logger.e("sharelink=="+link);
        this.shareLink = link;
    }
    public void setShareLink(String shareLink) {
        setLink(shareLink);
    }

    public String getShareImgurl() {
        return shareImgurl;
    }

    public void setShareImgurl(String shareImgurl) {
        if (TextUtils.isEmpty(shareImgurl)) shareImgurl = TopApp.getInstance().getShareDefaultImageUrl();
        this.shareImgurl =  TopApp.getInstance().getHttpService().getImageUrlFormMd5(shareImgurl);
        Logger.d("shareImgurl == "+ shareImgurl);
    }

    @Override
    public String toString() {
        return "ShareContent{" +
                "shareDesc='" + shareDesc + '\'' +
                ", shareImgurl='" + shareImgurl + '\'' +
                ", shareLink='" + shareLink + '\'' +
                ", shareTitle='" + shareTitle + '\'' +
                '}';
    }
}