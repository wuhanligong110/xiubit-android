package com.toobei.common.entity;

import java.util.List;

public class PageListBase<T> extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2077248468987251051L;

	private int pageIndex;//第几页
	private int pageSize; //页面大小
	private int pageCount;//总共几页
	private int totalCount; //总共数据
	private List<T> datas;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

}