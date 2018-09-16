package com.toobei.common.entity;

/**
 * 新手福利
 * Created by hasee-pc on 2016/12/21.
 */

public class NewComerWelfarDetail extends BaseEntity {

        /**
         * bindCardAmount : 15  绑卡认证红包金额
         * bindCardStatus : 1  绑卡认证状态；0未完成，1已完成
         * finishAll : 1    全部任务：0未完成，1已完成
         * inviteCfplannerAmount : 110   全部任务：0未完成，1已完成
         * inviteCfplannerStatus : 1    全部任务：0未完成，1已完成
         * inviteCustomerAmount : 50    邀请客户红包金额
         * inviteCustomerStatus : 1    邀请客户：0未完成，1已完成
         * uploadHeadImageAmount : 10   上传头像红包金额
         * uploadHeadImageStatus : 1   上传头像：0未完成，1已完成
         */

        private String bindCardAmount;
        private String bindCardStatus;
        private String finishAll;
        private String inviteCfplannerAmount;
        private String inviteCfplannerStatus;
        private String inviteCustomerAmount;
        private String inviteCustomerStatus;
        private String uploadHeadImageAmount;
        private String uploadHeadImageStatus;

        public String getBindCardAmount() {
            return bindCardAmount;
        }

        public void setBindCardAmount(String bindCardAmount) {
            this.bindCardAmount = bindCardAmount;
        }

        public String getBindCardStatus() {
            return bindCardStatus;
        }

        public void setBindCardStatus(String bindCardStatus) {
            this.bindCardStatus = bindCardStatus;
        }

        public String getFinishAll() {
            return finishAll;
        }

        public void setFinishAll(String finishAll) {
            this.finishAll = finishAll;
        }

        public String getInviteCfplannerAmount() {
            return inviteCfplannerAmount;
        }

        public void setInviteCfplannerAmount(String inviteCfplannerAmount) {
            this.inviteCfplannerAmount = inviteCfplannerAmount;
        }

        public String getInviteCfplannerStatus() {
            return inviteCfplannerStatus;
        }

        public void setInviteCfplannerStatus(String inviteCfplannerStatus) {
            this.inviteCfplannerStatus = inviteCfplannerStatus;
        }

        public String getInviteCustomerAmount() {
            return inviteCustomerAmount;
        }

        public void setInviteCustomerAmount(String inviteCustomerAmount) {
            this.inviteCustomerAmount = inviteCustomerAmount;
        }

        public String getInviteCustomerStatus() {
            return inviteCustomerStatus;
        }

        public void setInviteCustomerStatus(String inviteCustomerStatus) {
            this.inviteCustomerStatus = inviteCustomerStatus;
        }

        public String getUploadHeadImageAmount() {
            return uploadHeadImageAmount;
        }

        public void setUploadHeadImageAmount(String uploadHeadImageAmount) {
            this.uploadHeadImageAmount = uploadHeadImageAmount;
        }

        public String getUploadHeadImageStatus() {
            return uploadHeadImageStatus;
        }

        public void setUploadHeadImageStatus(String uploadHeadImageStatus) {
            this.uploadHeadImageStatus = uploadHeadImageStatus;
        }
    }

