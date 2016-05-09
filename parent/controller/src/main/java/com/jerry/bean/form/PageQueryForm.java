package com.jerry.bean.form;

import com.jerry.common.Page;

public class PageQueryForm {

	private int pageSize;
	
	private int pageNumber;

	public int getPageSize() {
		return pageSize==0?Page.DEFAULT_PAGE_SIZE:pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber==0?Page.DEFAULT_PAGE_NUMBER:pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
	
}
