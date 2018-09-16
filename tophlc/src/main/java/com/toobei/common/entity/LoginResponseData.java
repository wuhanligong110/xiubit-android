package com.toobei.common.entity;


public class LoginResponseData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 4320889048811653573L;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponseEntity [token=" + token + "]";
	}
}