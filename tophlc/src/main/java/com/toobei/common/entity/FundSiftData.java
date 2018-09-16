package com.toobei.common.entity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/17
 */

public class FundSiftData extends BaseEntity {
    private static final long serialVersionUID = 2396719089194540962L;

    public String accumulateNav;
    public String day1Performance;
    public String fundCode;
    public String fundFullName;
    public String fundHouse;
    public String fundHouseCode;
    public FundManagersBean fundManagers;
    public String fundName;
    public String fundSize;
    public String fundStatus;
    public String fundStatusMsg;
    public String fundType;
    public String fundTypeMsg;
    public String geographicalSector;
    public String isBuyEnable;
    public String isBuyEnableMsg;
    public String isMMFund;
    public String isMMFundMsg;
    public String isQDII;
    public String isQDIIMsg;
    public String isRecommended;
    public String isRecommendedMsg;
    public String navDate;
    public String riskRate;
    public String riskRateMsg;
    public String specializeSector;
    public String earningsPer10000;
    public String month3;
    public String nav;
    public String sinceLaunch;
    public String year1;
    public String year3;
    public String year5;
   

    public String earningsPer10000Msg;
    public String month3Msg;
    public String navMsg;
    public String sinceLaunchMsg;
    public String year1Msg;
    public String year3Msg;
    public String year5Msg;
    public String sevenDaysAnnualizedYield;

    public static class FundManagersBean {
    }
    
    public String getYield(String periodType){
        switch (periodType){
            case "earningsPer10000":
                return earningsPer10000Msg;
            case "month3":
                return month3Msg + "%";
            case "year1":
                return year1Msg + "%";
            case "year3":
                return year3Msg + "%";
            case "year5":
                return year5Msg + "%";
            case "nav":
                return navMsg;
            case "sinceLaunch":
                return sinceLaunchMsg + "%";
            case "sevenDaysAnnualizedYield":
                return sevenDaysAnnualizedYield + "%";
                
        }
        return "";
    }


}
