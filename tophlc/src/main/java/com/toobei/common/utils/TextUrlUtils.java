package com.toobei.common.utils;

import com.toobei.common.TopApp;

import java.util.Map;

/**
 * 公司: tophlc
 * 类说明:  url 路径拼接
 *
 * @author qingyechen
 * @time 2016/11/16 0016 下午 5:35
 */
public class TextUrlUtils {
    /**
     * 拼接url参数
     *
     * @param //string
     * @return //
     */
    public static String addUrlStr(String oldStr, String newStr) {

        if (!oldStr.contains("?")) {
            return oldStr + "?" + newStr;
        } else {
            return oldStr + "&" + newStr;
        }
    }

    public static String addUrlStrAND(String oldStr, String newStr) {
        return oldStr + "&" + newStr;

    }

    /**
     * 取H5web的地址
     *
     * @param type   C文件中有常量类
     * @param params 其余参数 拼接在url后面
     */
    public static String getFrameWebUrl(String type, Map<String, String> params) {

        String domainUrl = TopApp.getInstance().getHttpService().getBaseH5urlByAppkind();
        StringBuffer url = new StringBuffer(domainUrl + TopApp.getInstance().getDefaultSp().getFrameWebUrl() + "?type=" + type);
        if (params != null) {
            for (String s : params.keySet()) {
                url.append("&" + s + "=" + params.get(s));
            }
        }
        return url.toString();
    }

    /**
     * 取H5web的地址
     *
     * @param type C文件中有常量类
     */
    public static String getFrameWebUrl(String type) {
        return getFrameWebUrl(type, null);
    }

    /**
     * 貅比特不同web的type
     */
    public interface LCDS_WebUrlKEY {
        /**
         * Leader 奖励说明
         */
        public static String TEAM_SALE_STAT_DIRECT_LEADER_REWARD = "teamSaleLeaderReward";
        /**
         * 团队销售统计直接推荐
         */
        public static String TEAM_SALE_STAT_DIRECT_RECOMMEND = "teamSaleStatDirectRecommend";
        /**
         * 团队销售统计间接推荐
         */
        public static String TEAM_SALE_STAT_INDIRE_CTRECOMMEND = "teamSaleStatIndirectRecommend";
        /**
         * 我的理财师团队介绍
         */
        public static String MYTEAM_INTRODUCE = "myTeamIntroduce";
        /**
         * 我的理财师->无团队成员说明
         */
        public static String MYTEAM_HAS_NO_CFP_INTRODUCE = "myTeamHasNoCfpIntroduce";
        /**
         * 我的理财师->更新日志
         */
        public static String UPDATE_LOG = "updateLog";
        /**
         * 了解我们
         */
        public static String LEARN_ABOUT_US = "learnAboutUs";
        /**
         * freshStrategy   新手攻略
         */
        public static String FRESH_STRATEGY = "freshStrategy";


    }

    /**
     * t呗不同web的type
     */
    public interface TOOBEI_WebUrlKEY {

        /**
         * 了解我们
         */
        public static String LEARN_ABOUT_US = "learnAboutUs_toobei";
        /**
         * freshStrategy   新手攻略
         */
        public static String FRESH_STRATEGY = "freshStrategy_toobei";
    }

}
