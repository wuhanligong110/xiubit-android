package com.toobei.common.entity;


public class CheckResponseData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -3331749923279325208L;
	private String rlt;

	public String getRlt() {
		return rlt;
	}

	public void setRlt(String rlt) {
		this.rlt = rlt;
	}

	@Override
	public String toString() {
		return "CheckResponseData [rlt =" + rlt + "]";
	}
}