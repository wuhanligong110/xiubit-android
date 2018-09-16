package com.toobei.common.entity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/9/13
 */

public class InsuranceDetialData extends BaseEntity {
    private static final long serialVersionUID = 1625209760199659230L;


    /**
     * price : 55868
     * priceString : 测试内容ag7f
     * state : 38140
     * caseCode : QX000000002602
     * companyName : 华安保险
     * creatTime : 2017-09-12 13:54:09
     * fristCategory : 重大疾病保险
     * fullDescription : 小保费大保障|一年期重疾保障首选|三档计划适合不同需求
     * id : 1
     * orgNumber : OPEN_QIXIN_WEB
     * productBakimg :
     * productName : “欣享康健”重大疾病保险  经济版
     * secondCategory :
     * upTime :
     */

    public String price;
    public String priceString;
    public int state;
    public String caseCode;
    public String companyName;
    public String creatTime;
    public int fristCategory; //1-意外险 2-旅游险 3-家财险 4-医疗险 5-重疾险 6-年金险  7-寿险
    public String fullDescription;
    public int id;
    public String orgNumber;
    public String productBakimg; //保险背景图片
    public String productName;
    public String secondCategory;
    public String upTime;
    //v4.5.0
    public String feeRatio; //佣金率
}
