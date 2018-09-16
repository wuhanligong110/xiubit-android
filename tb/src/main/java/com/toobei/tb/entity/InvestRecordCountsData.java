package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 我的投资数量-陈衡-已实现
 * Created by hasee-pc on 2017/1/3.
 */
public class InvestRecordCountsData extends BaseEntity {


    private static final long serialVersionUID = 2287628317075878721L;
    /**
     * hkwc : 0  回款完成
     * hkz : 0  	回款中
     * qt : 2 其他
     * tzz : 4 	投资中
     */

    private int hkwc;
    private int hkz;
    private int qt;
    private int tzz;

    public int getHkwc() {
        return hkwc;
    }

    public void setHkwc(int hkwc) {
        this.hkwc = hkwc;
    }

    public int getHkz() {
        return hkz;
    }

    public void setHkz(int hkz) {
        this.hkz = hkz;
    }

    public int getQt() {
        return qt;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }

    public int getTzz() {
        return tzz;
    }

    public void setTzz(int tzz) {
        this.tzz = tzz;
    }
}
