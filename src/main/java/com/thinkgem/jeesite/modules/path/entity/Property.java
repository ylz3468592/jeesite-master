/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.path.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 存放路径Entity
 * @author tzj
 * @version 2018-02-06
 */
public class Property extends DataEntity<Property> {
	
	private static final long serialVersionUID = 1L;
	private String key;		// key
	private String value;		// value
	
	public Property() {
		super();
	}

	public Property(String id){
		super(id);
	}

	@Length(min=0, max=500, message="key长度必须介于 0 和 500 之间")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Length(min=0, max=500, message="value长度必须介于 0 和 500 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}