package com.toobei.common.entity;

public class ErrorResponse implements java.io.Serializable {

	/** serialVersionUID*/
	private static final long serialVersionUID = -6819881285899869677L;

	private String code;

	private String msg;

	public ErrorResponse() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
