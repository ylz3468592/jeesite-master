/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.approved.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 审批流程Entity
 * @author tzj
 * @version 2018-01-09
 */
public class Approved extends DataEntity<Approved> {
	
	private static final long serialVersionUID = 1L;
	private String apInfoId;		// ap_info_id
	private String apSyIdFirst;		// ap_sy_id_first
	private String apSyIdSecond;		// ap_sy_id_second
	private String apProgress;		// ap_progress
	private String apRemarks;		// ap_remarks
	private Date apCheckTime;		// ap_check_time
	private String apNotice;		// ap_notice
	
	public Approved() {
		super();
	}

	public Approved(String id){
		super(id);
	}

	@Length(min=1, max=32, message="ap_info_id长度必须介于 1 和 32 之间")
	public String getApInfoId() {
		return apInfoId;
	}

	public void setApInfoId(String apInfoId) {
		this.apInfoId = apInfoId;
	}
	
	@Length(min=1, max=11, message="ap_sy_id_first长度必须介于 1 和 11 之间")
	public String getApSyIdFirst() {
		return apSyIdFirst;
	}

	public void setApSyIdFirst(String apSyIdFirst) {
		this.apSyIdFirst = apSyIdFirst;
	}
	
	@Length(min=0, max=11, message="ap_sy_id_second长度必须介于 0 和 11 之间")
	public String getApSyIdSecond() {
		return apSyIdSecond;
	}

	public void setApSyIdSecond(String apSyIdSecond) {
		this.apSyIdSecond = apSyIdSecond;
	}
	
	@Length(min=0, max=11, message="ap_progress长度必须介于 0 和 11 之间")
	public String getApProgress() {
		return apProgress;
	}

	public void setApProgress(String apProgress) {
		this.apProgress = apProgress;
	}
	
	@Length(min=0, max=200, message="ap_remarks长度必须介于 0 和 200 之间")
	public String getApRemarks() {
		return apRemarks;
	}

	public void setApRemarks(String apRemarks) {
		this.apRemarks = apRemarks;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="ap_check_time不能为空")
	public Date getApCheckTime() {
		return apCheckTime;
	}

	public void setApCheckTime(Date apCheckTime) {
		this.apCheckTime = apCheckTime;
	}
	
	@Length(min=0, max=11, message="ap_notice长度必须介于 0 和 11 之间")
	public String getApNotice() {
		return apNotice;
	}

	public void setApNotice(String apNotice) {
		this.apNotice = apNotice;
	}
	
}