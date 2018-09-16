package com.toobei.tb.util;

public class C {

    public static final String URL_DEFAULT = ""; //
    public static final String URL_IMAGE_SERVER_DEFAULT = "http://image.toobei.com/";
    public static final String str_tophlcCustomerServiceImAccount = "tophlcCustomerServiceImAccount";
    public static final String URL_bankLimitDetail_DEFAULT = "http://minvestor.xiaoniuapp.com/pages/agreement/chongzhixiane.html";

    /**
     * 系统剪切图片请求
     */
    public static final int REQUEST_CROP_SYSTEM = 0X111;

    public static final int REFRESH_DATA = 0X112;

    /**
     * 用户头像更改标识
     */
    public static final String TAG_USER_FACE_CHANGED = "userFaceChanged";

    public static final String TAG_FEEDBACK = "Feedback";

    /**
     * 客户列表加载时的默认排序，4表示注册时间
     */
    public static final int CUSTOMER_LIST_HEAD_DEFAULT_INDEX = 4;
    public static final int REQUEST_FRAGENT_MINE_SETTING_INFO = 0X124;
    /**
     * 登录
     */
    public static final int REQUEST_ACTIVITY_LOGIN = 0X125;
    /**
     * 新手攻略绑卡
     */
    public static final int REQUEST_FRESH_STRATERY = 0X136;
    /**
     * 统一使用这个字段的url 拼接Type 切换不同的页面
     */
    public static final String URL_FRAMEWEBURL = "/pages/frame/frame.html";
    /**
     * 推荐产品或者机构
     */
    public static final int RECOMMEND_TYPE_PRODUCT = 0X140;
    public static final int RECOMMEND_TYPE_ORG = 0X141;
    /**
     * 1机构动态 、2资讯  、3课堂详情  4公告   链接 类型的type
     */
    public static final int INFOMATION_URL_TYPE_ORG_DYNAMIC = 1;
    public static final int INFOMATION_URL_TYPE_NEWS = 2;
    public static final int INFOMATION_URL_TYPE_CLASSROOM = 3;
    public static final int INFOMATION_URL_TYPE_NOTICE = 4;


    /**
     * 微信分享logo
     */
    public static final String URL_WECHAR_SHARE_IMAGE = "https://image.toobei.com/74dd50c622b6f99ed0ad831b977708a9?f=png&q=100";
    /**
     * T呗投资攻略  2016/10/27 0027
     */
    public static final String URL_INVESTMENTSTRATEGY = "/pages/question/investmentStrategy.html";

    /**
     * 律盾
     */
    public static final String URL_SAFESHIELD = "/pages/activities/safeShield.html";
    /**
     * t呗小白训练营
     */
    public static final String URL_TOOBEI_TRAIN = "/pages/question/toobei_train.html";


    /**
     * 机构动态V2.0添加 机构动态 、资讯 、课堂详情链接  /pages/organization/organization_news.html?id=XXXX
     */
    public static final String URL_ORGNIZATION_NEWS = "https://liecai.toobei.com/pages/richText/detail.html";
    /**
     * 平台详情--->>> 平台动态
     */
    public static final String URL_ORG_ORGANIZATION_NEWS = "/pages/organization/organization_news.html";
    /**
     * 消息中心-->公告
     */
    public static final String URL_MSG_CENTER_MESSAGE_NOTICE = "/pages/mine/message_detail.html";
    /**
     * 首页->新手攻略
     */
    public static final String URL_NEWERSTRATEGY = "/pages/newerStrategy/newerStrategy.html";
    /**
     * 首页->了解我们
     */
    public static final String URL_HOME_LEARN_ABOUT_US = "/pages/understand/understand.html";
    /**
     * 更多->关于我们
     */
    public static final String URL_MINE_MORE_ABOUT_US = "/pages/about/aboutMe.html";

}
