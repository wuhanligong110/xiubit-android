package com.toobei.common.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/18
 */

public class FundBaseDefinedData extends BaseEntity {
    private static final long serialVersionUID = -5639549812932720436L;
    public String defaultFundType;	//默认基金类型	string	多个用,分割 如: MM,BOND
    public String defaultPeriod	;  //默认基金排序	string

    public List<FundTypeListBean> fundTypeList;
    public List<FundTypeListBean> periodList;

    public static class FundTypeListBean {

        public int delStatus;
        public String fundType;
        public String fundTypeKey;
        public String fundTypeName;
        public String fundTypeValue;
        public int id;
        public String orgNumber;
    }

}
