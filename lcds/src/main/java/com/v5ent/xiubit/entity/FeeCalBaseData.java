package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/5/24
 */

public class FeeCalBaseData extends BaseEntity {

    private static final long serialVersionUID = 1930663378408339583L;


    private List<CrmCfpLevelListBean> crmCfpLevelList;
    private List<String> feeTypeList;

    public List<CrmCfpLevelListBean> getCrmCfpLevelList() {
        return crmCfpLevelList;
    }

    public void setCrmCfpLevelList(List<CrmCfpLevelListBean> crmCfpLevelList) {
        this.crmCfpLevelList = crmCfpLevelList;
    }

    public List<String> getFeeTypeList() {
        return feeTypeList;
    }

    public void setFeeTypeList(List<String> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }

    public static class CrmCfpLevelListBean {
        /**
         * id : 1
         * levelCode : TA
         * levelWeight : 	见习=10 | 顾问=20 | 经理=30 | 总监=40
         * levelName : 见习
         * levelRemark : 见习理财师
         * createTime : 2017-03-31 16:49:13
         */

        private int id;
        private String levelCode;
        private int levelWeight;
        private String levelName;
        private String levelRemark;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLevelCode() {
            return levelCode;
        }

        public void setLevelCode(String levelCode) {
            this.levelCode = levelCode;
        }

        public int getLevelWeight() {
            return levelWeight;
        }

        public void setLevelWeight(int levelWeight) {
            this.levelWeight = levelWeight;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getLevelRemark() {
            return levelRemark;
        }

        public void setLevelRemark(String levelRemark) {
            this.levelRemark = levelRemark;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

}
