package com.toobei.common.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/21
 */

public class FundInvestorHoldingsData extends BaseEntity {
    private static final long serialVersionUID = -855744810192572445L;
    
            public List<FundInvestorDetialDataBean> datas;

            public static class FundInvestorDetialDataBean {
                /**
                 * availableUnit : 测试内容2qsf
                 * chargeMode : 测试内容c0q7
                 * currentValue : 测试内容3l35
                 * dividendCash : 测试内容un7x
                 * dividendInstruction : 测试内容n69u
                 * investmentAmount : 测试内容28om
                 * nav : 测试内容z1eb
                 * previousProfitNLoss : 测试内容8dw8
                 * profitNLoss : 测试内容523t
                 * totalUnit : 测试内容e970
                 * undistributeMonetaryIncome : 测试内容7lhb
                 * fundCode : 080012
                 * fundName : 长盛电子信息产业混合
                 * intransitAssets : 1000.0
                 * navDate : 1501689600000
                 */

                public String availableUnit;
                public String chargeMode;
                public String currentValue;
                public String dividendCash;
                public String dividendInstruction;
                public String investmentAmount;
                public String nav;
                public String previousProfitNLoss;
                public String profitNLoss;
                public String totalUnit;
                public String undistributeMonetaryIncome;
                public String fundCode;
                public String fundName;
                public String intransitAssets;
                public String navDate;
            }
        }
