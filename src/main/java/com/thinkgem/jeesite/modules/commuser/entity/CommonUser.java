/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.commuser.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 普通用户Entity
 * @author tzj
 * @version 2018-01-09
 */
public class CommonUser extends DataEntity<CommonUser> {
	
	private static final long serialVersionUID = 1L;
	private String coName;		// co_name
	private String coAddress;		// co_address
	private String coTel;		// co_tel
	private String coUserNo;		// co_user_no
	private String coEmail;		// co_email
	private String coTermNo;		// co_term_no
	private Date createTime;		// create_time
	private String empty1;		// empty_1
	private String empty2;		// empty_2
	private String empty3;		// empty_3
	private String empty4;		// empty_4
	
	public CommonUser() {
		super();
	}

	public CommonUser(String id){
		super(id);
	}

	@Length(min=1, max=50, message="co_name长度必须介于 1 和 50 之间")
	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}
	
	@Length(min=0, max=100, message="co_address长度必须介于 0 和 100 之间")
	public String getCoAddress() {
		return coAddress;
	}

	public void setCoAddress(String coAddress) {
		this.coAddress = coAddress;
	}
	
	@Length(min=1, max=50, message="co_tel长度必须介于 1 和 50 之间")
	public String getCoTel() {
		return coTel;
	}

	public void setCoTel(String coTel) {
		this.coTel = coTel;
	}
	
	@Length(min=1, max=50, message="co_user_no长度必须介于 1 和 50 之间")
	public String getCoUserNo() {
		return coUserNo;
	}

	public void setCoUserNo(String coUserNo) {
		this.coUserNo = coUserNo;
	}
	
	@Length(min=0, max=50, message="co_email长度必须介于 0 和 50 之间")
	public String getCoEmail() {
		return coEmail;
	}

	public void setCoEmail(String coEmail) {
		this.coEmail = coEmail;
	}
	
	@Length(min=1, max=50, message="co_term_no长度必须介于 1 和 50 之间")
	public String getCoTermNo() {
		return coTermNo;
	}

	public void setCoTermNo(String coTermNo) {
		this.coTermNo = coTermNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=100, message="empty_1长度必须介于 0 和 100 之间")
	public String getEmpty1() {
		return empty1;
	}

	public void setEmpty1(String empty1) {
		this.empty1 = empty1;
	}
	
	@Length(min=0, max=100, message="empty_2长度必须介于 0 和 100 之间")
	public String getEmpty2() {
		return empty2;
	}

	public void setEmpty2(String empty2) {
		this.empty2 = empty2;
	}
	
	@Length(min=0, max=100, message="empty_3长度必须介于 0 和 100 之间")
	public String getEmpty3() {
		return empty3;
	}

	public void setEmpty3(String empty3) {
		this.empty3 = empty3;
	}
	
	@Length(min=0, max=100, message="empty_4长度必须介于 0 和 100 之间")
	public String getEmpty4() {
		return empty4;
	}

	public void setEmpty4(String empty4) {
		this.empty4 = empty4;
	}
	
}