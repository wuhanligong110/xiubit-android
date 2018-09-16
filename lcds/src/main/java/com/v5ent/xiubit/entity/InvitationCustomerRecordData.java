package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/30
 */

public class InvitationCustomerRecordData extends BaseEntity{
    private static final long serialVersionUID = -4111104790806834900L;

            /**
             * isInvest : 测试内容tjpa
             * mobile : 15019447487
             * registerTime : 2016年09月18注册
             * userName : 罗荣
             */

            private String isInvest;
            private String mobile;
            private String registerTime;
            private String userName;

            public String getIsInvest() {
                return isInvest;
            }

            public void setIsInvest(String isInvest) {
                this.isInvest = isInvest;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
