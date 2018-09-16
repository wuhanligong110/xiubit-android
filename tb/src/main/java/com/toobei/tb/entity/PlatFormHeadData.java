package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 * 理财产品，机构筛选条件
 */
public class PlatFormHeadData extends BaseEntity {


    private static final long serialVersionUID = -4843149159008546438L;
    /**
     * key : 30天以内
     * value : min_dead_line <30
     */

    private List<DeadlineBean> deadline;
    /**
     * key : AAA
     * value : =6
     */

    private List<OrgLevelBean> orgLevel;
    /**
     * key : 20%以上
     * value : max_profit >20
     */

    private List<ProfitBean> profit;

    public List<DeadlineBean> getDeadline() {
        return deadline;
    }

    public void setDeadline(List<DeadlineBean> deadline) {
        this.deadline = deadline;
    }

    public List<OrgLevelBean> getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(List<OrgLevelBean> orgLevel) {
        this.orgLevel = orgLevel;
    }

    public List<ProfitBean> getProfit() {
        return profit;
    }

    public void setProfit(List<ProfitBean> profit) {
        this.profit = profit;
    }

    /**
     *
     */
    public static class DeadlineBean extends BaseEntity {


        private static final long serialVersionUID = 4475121635405228030L;
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class OrgLevelBean extends BaseEntity {

        private static final long serialVersionUID = 6335651879327660219L;
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ProfitBean extends BaseEntity {

        private static final long serialVersionUID = 5175655059632445435L;
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
