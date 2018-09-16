package com.toobei.common.entity;

import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.Utils;

/**
 * 公司: tophlc
 * 类说明：联系人 实体
 *
 * @date 2015-12-25
 */
public class Contacts extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -883880721683681397L;

    private String contactId;
    private String name;
    private String mobile;

    private String sortLetters;//字母排序

    public Contacts() {
        super();
    }

    public Contacts(String contactId, String name, String mobile) {
        super();
        this.contactId = contactId;
        this.name = name;
        this.mobile = mobile;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getSortLetters() {
        if (sortLetters == null) {
            if (name != null && name.length() > 0) {
                String sortString = Utils.getPinYin(name.substring(0, 1));
                if (!StringUtil.isEmpty(sortString)) {
                    sortString = sortString.toUpperCase()
                            .substring(0, 1);
                    if (sortString.matches("[A-Z]")) {
                        setSortLetters(sortString.toUpperCase());
                    } else {
                        setSortLetters("#");
                    }
                } else {
                    setSortLetters("#");
                }
            } else {
                setSortLetters("#");
            }
        }
        return sortLetters;
    }

}
