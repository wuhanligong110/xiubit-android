package com.toobei.common.entity;

public class AddressCity extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 4272882660043232651L;

	private String provinceId;//上级省份ID
	private String cityId;
	private String cityName;

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
