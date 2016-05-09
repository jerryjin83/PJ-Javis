package com.jerry.bean.model;

import com.jerry.common.BeanUtil;


public abstract class AbstractModel implements IModel{
	
	private String id;

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id){
		this.id = id;
	}

	@Override
	public String toString(){
		 return BeanUtil.convertToJsonString(this);
	}
}
