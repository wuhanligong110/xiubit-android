package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/23
 */

public class HomeCfpBuyInfoData extends BaseEntity {
    private static final long serialVersionUID = 1160711847139029449L;




        public String commissionAmount;
        public String newcomerTaskStatus; //1：未完成(显示) ；0：已完成或者不存在该任务（不显示）
        public List<HotInvestListBean> hotInvestList;
        public List<OrderListBean> orderList;

        public static class HotInvestListBean {
            /**
             * amount : 94400
             * createTime : 2018-03-28 15:09:00
             * orgName : 玖富轻舟
             * productName :
             * term : 90
             * timeDesc :
             * type : 1
             * userName : 何荣兜
             */

            public String amount;
            public String createTime;
            public String orgName;
            public String productName;
            public String term;
            public String timeDesc;
            public String type;
            public String userName;
        }

        public static class OrderListBean {
            /**
             * mobile : 159****0878
             * orderMoney : 94,400.00
             */

            public String mobile;
            public String orderMoney;
        }
    }
