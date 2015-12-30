package com.jerry.common;

import java.util.List;
import java.util.Map;

public class Page<T> {
	public static final int DEFAULT_PAGE_SIZE = 10;
	private Map<String,String> titles;
	private List<T> result;
	private long total;
	private int pageNumber;
	private int pageSize;
	private long totalPage;
	
	public Map<String, String> getTitles() {
		return titles;
	}
	public void setTitles(Map<String, String> titles) {
		this.titles = titles;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getTotalPage(){
		return totalPage==0?(total/pageSize + (total%pageSize!=0?1:0)):totalPage;
	}
	@Override
	public String toString() {
		return BeanUtil.convertToJsonString(this);
	}
	
}
