package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * home页金融超市平台信息
 */
public class HomeOrgInfoDetail extends BaseEntity {
//    {
//        "code": "0",
//            "data": {
//        "datas": [
//        {
//            "orgLink": "http://platfomr.com",
//                "orgLogo": "http://platfomr/logo.png",
//                "orgName": "人人贷"
//        },
//        {
//            "orgLink": "http://platfomr.com",
//                "orgLogo": "logo md5",
//                "orgName": "合盘贷"
//        }
//        ]
//    }
//    }
//{
//    "datas": [
//    {
//        "orgLink": "http://mchannel.xiaoniuapp.com/pages/activities/openChest.html?token=eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0Njg2NjMwNDE2MzUsInN1YiI6IjVlNTQzZDNhZGJjNDRjZTQ4OTFlNTAwOGMzY2I3NTNiIiwiaXNzIjoiaHR0cHM6XC9cL3d3dy5saW5rd2VlLmNvbSJ9.iy0kbneCY2A1gfu8-BYKVgIqx0l5acLe2cQa8ee8G-E&orgno=7777777",
//            "orgLogo": "http://mchannel.xiaoniuapp.com/images/logo/80x80.png",
//            "orgName": "人人贷"
//    },
//    {
//        "orgLink": "http://mchannel.xiaoniuapp.com/pages/activities/thanks.html?token=eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0Njg2NjMwNDE2MzUsInN1YiI6IjVlNTQzZDNhZGJjNDRjZTQ4OTFlNTAwOGMzY2I3NTNiIiwiaXNzIjoiaHR0cHM6XC9cL3d3dy5saW5rd2VlLmNvbSJ9.iy0kbneCY2A1gfu8-BYKVgIqx0l5acLe2cQa8ee8G-E&orgno=8888888",
//            "orgLogo": "http://mchannel.xiaoniuapp.com/images/logo/80x80.png",
//            "orgName": "陆金所"
//    }

    private static final long serialVersionUID = 7788350054565419055L;
    private String orgName;
    private String orgLogo;
    private String orgNumber;
    private String orgLink;

    public String getOrgLink() {
        return orgLink;
    }

    public void setOrgLink(String orgLink) {
        this.orgLink = orgLink;
    }


    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

}