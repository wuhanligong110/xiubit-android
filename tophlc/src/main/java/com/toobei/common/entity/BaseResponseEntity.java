package com.toobei.common.entity;

import com.toobei.common.TopApp;

import java.util.List;

@SuppressWarnings("serial")
public class BaseResponseEntity<T> extends BaseEntity {

	private String code;

	private String msg;

	private List<ErrorResponse> errors;

	private T data;

	public BaseResponseEntity() {

	}

	public BaseResponseEntity(int code, String msg) {
		this.code = code + "";
		this.msg = msg;
	}

	public BaseResponseEntity(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		//token失效时，退出
		if (code.equals("100003")) {
			TopApp.getInstance().logOutEndNoSikp(); 
			TopApp.getInstance().skipLogin();
		}
		return code;
	}

	public String getCodeNoCheck() {
		return getCode();
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

	public List<ErrorResponse> getErrors() {
		return errors;
	}

	public String getErrorsCodeStr() {
		if (code != null && code.equals("100003")) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		if (errors != null && errors.size() > 0) {
			for (ErrorResponse e : errors) {
				sb.append("" + e.getCode());
			}
		} else {
			sb.append(msg);
		}
		return sb.toString();
	}

	public String getErrorsMsgStr() {
		if (code != null && code.equals("100003")) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		if (errors != null && errors.size() > 0) {
			for (ErrorResponse e : errors) {
				sb.append("" + e.getMsg());
			}
		} else {
			sb.append(msg);
		}
		return sb.toString();
	}

	public void setErrors(List<ErrorResponse> errors) {
		this.errors = errors;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseResponseEntity [code=" + code + ", msg=" + msg + ", errors=" + errors
				+ ", data=" + data.toString() + "]";
	}

}
