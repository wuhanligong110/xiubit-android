package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;
import com.toobei.common.entity.ShareContent;

/**
 * 邀请客户
 */

public class InviteCfpOutCreateQrData extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7560857764507620663L;

    private String mobile;
    private String url;
    private String allowanceRule;//被推荐人每笔销售佣金的15%”//直接推荐收益规则
    private String childrenAllowanceRule;//被推荐人每笔销售佣金的15%"间接接推荐收益规则

    private ShareContent shareContent;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ShareContent getShareContent() {
        return shareContent;
    }

    public void setShareContent(ShareContent shareContent) {
        this.shareContent = shareContent;
    }

    public String getAllowanceRule() {
        return allowanceRule;
    }

    public void setAllowanceRule(String allowanceRule) {
        this.allowanceRule = allowanceRule;
    }

    public String getChildrenAllowanceRule() {
        return childrenAllowanceRule;
    }

    public void setChildrenAllowanceRule(String childrenAllowanceRule) {
        this.childrenAllowanceRule = childrenAllowanceRule;
    }

}