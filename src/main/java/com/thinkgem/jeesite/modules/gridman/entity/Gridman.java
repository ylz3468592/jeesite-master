/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gridman.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 一、二级网格员关联表Entity
 * @author tzj
 * @version 2018-01-29
 */
public class Gridman extends DataEntity<Gridman> {
	
	private static final long serialVersionUID = 1L;
	private String sysU1Id;		// sys_u1_id
	private String sysU2Id;		// sys_u2_id
	private String empty1;		// empty_1
	private String empty2;		// empty_2
	
	public Gridman() {
		super();
	}

	public Gridman(String id){
		super(id);
	}

	@Length(min=0, max=32, message="sys_u1_id长度必须介于 0 和 32 之间")
	public String getSysU1Id() {
		return sysU1Id;
	}

	public void setSysU1Id(String sysU1Id) {
		this.sysU1Id = sysU1Id;
	}
	
	@Length(min=0, max=32, message="sys_u2_id长度必须介于 0 和 32 之间")
	public String getSysU2Id() {
		return sysU2Id;
	}

	public void setSysU2Id(String sysU2Id) {
		this.sysU2Id = sysU2Id;
	}
	
	@Length(min=0, max=32, message="empty_1长度必须介于 0 和 32 之间")
	public String getEmpty1() {
		return empty1;
	}

	public void setEmpty1(String empty1) {
		this.empty1 = empty1;
	}
	
	@Length(min=0, max=32, message="empty_2长度必须介于 0 和 32 之间")
	public String getEmpty2() {
		return empty2;
	}

	public void setEmpty2(String empty2) {
		this.empty2 = empty2;
	}
	
}