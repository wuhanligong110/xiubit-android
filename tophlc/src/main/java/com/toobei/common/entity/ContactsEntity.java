package com.toobei.common.entity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明：联系人网络通讯 实体
 * @date 2015-12-25
 */
public class ContactsEntity extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -1181474745490867717L;

	private String content;
	private List<Contacts> customers;

	public ContactsEntity() {
		super();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Contacts> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Contacts> customers) {
		this.customers = customers;
	}

}
