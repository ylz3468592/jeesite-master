/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.term.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 终端Entity
 * @author 田卓军
 * @version 2018-01-09
 */
public class Term extends DataEntity<Term> {
	
	private static final long serialVersionUID = 1L;
	private String teNo;		// 终端编号
	private String simNo;		// sim卡号
	private String teTel;		// 终端电话号码
	private User user;		// 所属网格员的id
	private String teAddress;		// 终端地址
	private Date teCreateTime;		// 创建时间
	private Date teProductTime;		// 出厂时间
	private String teStatus;		// 终端状态
	private String empty1;		// 党员编号
	private String empty2;		// empty_2预留字段
	
	public Term() {
		super();
	}

	public Term(String id){
		super(id);
	}

	@Length(min=1, max=50, message="te_no长度必须介于 1 和 50 之间")
	public String getTeNo() {
		return teNo;
	}

	public void setTeNo(String teNo) {
		this.teNo = teNo;
	}
	
	@Length(min=1, max=50, message="sim_no长度必须介于 1 和 50 之间")
	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	
	@Length(min=0, max=11, message="te_tel长度必须介于 0 和 11 之间")
	public String getTeTel() {
		return teTel;
	}

	public void setTeTel(String teTel) {
		this.teTel = teTel;
	}
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Length(min=1, max=100, message="te_address长度必须介于 1 和 100 之间")
	public String getTeAddress() {
		return teAddress;
	}

	public void setTeAddress(String teAddress) {
		this.teAddress = teAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTeCreateTime() {
		return teCreateTime;
	}

	public void setTeCreateTime(Date teCreateTime) {
		this.teCreateTime = teCreateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTeProductTime() {
		return teProductTime;
	}

	public void setTeProductTime(Date teProductTime) {
		this.teProductTime = teProductTime;
	}
	
	@Length(min=1, max=2, message="te_status长度必须介于 1 和 2 之间")
	public String getTeStatus() {
		return teStatus;
	}

	public void setTeStatus(String teStatus) {
		this.teStatus = teStatus;
	}
	
	
	public String getEmpty1() {
		return empty1;
	}

	public void setEmpty1(String empty1) {
		this.empty1 = empty1;
	}
	
	
	public String getEmpty2() {
		return empty2;
	}

	public void setEmpty2(String empty2) {
		this.empty2 = empty2;
	}
	
}