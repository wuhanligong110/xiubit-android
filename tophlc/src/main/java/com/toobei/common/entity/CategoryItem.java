package com.toobei.common.entity;

/**
 * 公司: tophlc
 * 类说明：类别选项
 * @date 2015-9-24
 */
public class CategoryItem {
	private int id;
	private String name;

	public CategoryItem(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
