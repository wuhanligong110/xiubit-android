package com.toobei.common.entity;

public class AddressProvince extends BaseEntity {

    private static final long serialVersionUID = -4961603131893109450L;
    private String provinceCode;//省简称
    private String provinceName;//省名字
    private String provinceId;//省名字


    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
