package com.toobei.common.entity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/30
 */

public class InvestedPlatformData extends BaseEntity{
    private static final long serialVersionUID = -8868196827468621623L;


        /**
         * investingAmt : 45,641,379.42
         * investingProfit : 358,574.49
         * platformInvestStatisticsList : [{"investingAmt":"27,099,900.00","investingProfit":"450.00","orgAccount":"DFH15915473248","orgKey":"FCAAEAC9C45C4DE5BDD3CCD405BFA7C1","orgName":"东方汇","orgNumber":"OPEN_DONGFANGHUI_WEB","orgUsercenterUrl":"http://dfjktest184.eastlending.cn/test-interface/jrcs/oms/personal","platformLogo":"a69bd6ee35c6045037eca58772d433a0","requestFrom":"","sign":"445D9D19E0F17A47572BB048DC1B01E3","timestamp":"2017-06-29 17:25:32"},{"investingAmt":"4,622,469.03","investingProfit":"129,105.90","orgAccount":"DFH15915473248","orgKey":"28BA028530A94D1FB4467800CC2596D7","orgName":"和信贷","orgNumber":"OPEN_HEXINDAI_WEB","orgUsercenterUrl":"https://suibian.hexindai.com/tbt/oms/personal","platformLogo":"ed6deb0327e6992e9629b93addb159f7","requestFrom":"","sign":"9898A6C881FE429121340253E76FB8BD","timestamp":"2017-06-29 17:25:32"},{"investingAmt":"1,486,008.00","investingProfit":"12,066.93","orgAccount":"DFH15915473248","orgKey":"9D81D94B7D064FBFB34B5150887DB4AE","orgName":"高搜易","orgNumber":"OPEN_GAOSOUYI_WEB","orgUsercenterUrl":"http://test.passport.gaosouyi.com/Login/lingh","platformLogo":"1db9f65347873343fb59b51893f5c2db","requestFrom":"","sign":"69D08126D8E860C9479906625160EC50","timestamp":"2017-06-29 17:25:32"},{"investingAmt":"7,522,602.39","investingProfit":"159,798.33","orgAccount":"DFH15915473248","orgKey":"01A3409EE06144CAAF34BC6BE257C4E6","orgName":"建工E贷","orgNumber":"OPEN_JIANGONGEDAI_WEB","orgUsercenterUrl":"http://www.jgedai.com:8080/member/linghui/personal","platformLogo":"be8ce9f75d8549f93d8fa2626b972077","requestFrom":"","sign":"4BEFC7ABE3D9186D32C12BA86197EDC3","timestamp":"2017-06-29 17:25:32"},{"investingAmt":"50,400.00","investingProfit":"273.33","orgAccount":"DFH15915473248","orgKey":"E63117E6BFEF4769A829E2A2312D7B91","orgName":"五星财富","orgNumber":"OPEN_WUXINGCAIFU_WEB","orgUsercenterUrl":"http://m.dm.wuxingjinrong.com/thirdParty/oms/product?utm_source=7A7D5DFF4074E795422EBA2B2DFD53F8","platformLogo":"428deb861bfcc5c702840ae82e051ea1","requestFrom":"","sign":"604DDD97089EFACA9035C3993373335A","timestamp":"2017-06-29 17:25:32"},{"investingAmt":"36,000.00","investingProfit":"43,200.00","orgAccount":"DFH15915473248","orgKey":"57DB9B51C6E54F1494B49ED74C19C12B","orgName":"粤财汇","orgNumber":"OPEN_YUECAIHUI_WEB","orgUsercenterUrl":"http://120.25.223.27:1313/oms/personal.html","platformLogo":"890128f55acf51c2c6cb458d8877703a","requestFrom":"","sign":"CD2352517861B30D426D5D47ADEAD327","timestamp":"2017-06-29 17:25:32"},{"investingAmt":"4,824,000.00","investingProfit":"13,680.00","orgAccount":"DFH15915473248","orgKey":"9B8D10A29A3C43149286A945355FB15A","orgName":"小牛在线","orgNumber":"OPEN_XIAONIUZAIXIAN_WEB","orgUsercenterUrl":"http://www.xiaoniu88.com/","platformLogo":"f41cdcc9b01846c54320bdd4990a3fe6","requestFrom":"","sign":"04017078D32A69D6882EC5C8CFEE88B4","timestamp":"2017-06-29 17:25:32"}]
         */

        public String investingAmt;
        public String investingProfit;
        public String investingPlatformNum; //	在投平台数量
        public String totalProfit; //累计收益	
        public String yearProfitRate; //综合年化收益率
        
        public List<PlatformInvestStatisticsListBean> platformInvestStatisticsList;
        public List<InvestingStatisticListBean> investingStatisticList; //投资组合

        public static class InvestingStatisticListBean {
            public InvestingStatisticListBean(String orgName, String totalPercent) {
                this.orgName = orgName;
                this.totalPercent = totalPercent;
            }

            public String orgName;  //机构名称	
            public String totalPercent;  //占比
        }

       
        public static class     PlatformInvestStatisticsListBean {
            /**
             * investingAmt : 27,099,900.00
             * investingProfit : 450.00
             * orgAccount : DFH15915473248
             * orgKey : FCAAEAC9C45C4DE5BDD3CCD405BFA7C1
             * orgName : 东方汇
             * orgNumber : OPEN_DONGFANGHUI_WEB
             * orgUsercenterUrl : http://dfjktest184.eastlending.cn/test-interface/jrcs/oms/personal
             * platformLogo : a69bd6ee35c6045037eca58772d433a0
             * requestFrom :
             * sign : 445D9D19E0F17A47572BB048DC1B01E3
             * timestamp : 2017-06-29 17:25:32
             */

            public String investingAmt;
            public String investingProfit;
            public String orgAccount;
            public String orgKey;
            public String orgName;
            public String orgNumber;
            public String orgUsercenterUrl;
            public String platformLogo;
            public String requestFrom;
            public String sign;
            public String timestamp;
            public String orgUrlSkipJumpType; //0-直接跳转第三方 1-跳转本地机构详情页
            public String orgProductUrlSkipBindType; //0-跳转前不需要绑卡 1-跳转前需要绑卡


        }
    }
