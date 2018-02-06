/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.communication.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 通讯记录保存Entity
 * @author tzj
 * @version 2018-01-09
 */
public class CommunicationInfo extends DataEntity<CommunicationInfo> {
	
	private static final long serialVersionUID = 1L;
	private String ciCoId;		// ci_co_id
	private String ciTeId;		// ci_te_id
	private Date ciTakeTime;		// ci_take_time
	private Date ciSaveTime;		// ci_save_time
	private String ciSyId;		// ci_sy_id
	private String ciType;		// ci_type
	private Date ciStartTime;		// ci_start_time
	
	public CommunicationInfo() {
		super();
	}

	public CommunicationInfo(String id){
		super(id);
	}

	@Length(min=1, max=11, message="ci_co_id长度必须介于 1 和 11 之间")
	public String getCiCoId() {
		return ciCoId;
	}

	public void setCiCoId(String ciCoId) {
		this.ciCoId = ciCoId;
	}
	
	@Length(min=1, max=11, message="ci_te_id长度必须介于 1 和 11 之间")
	public String getCiTeId() {
		return ciTeId;
	}

	public void setCiTeId(String ciTeId) {
		this.ciTeId = ciTeId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCiTakeTime() {
		return ciTakeTime;
	}

	public void setCiTakeTime(Date ciTakeTime) {
		this.ciTakeTime = ciTakeTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCiSaveTime() {
		return ciSaveTime;
	}

	public void setCiSaveTime(Date ciSaveTime) {
		this.ciSaveTime = ciSaveTime;
	}
	
	@Length(min=1, max=11, message="ci_sy_id长度必须介于 1 和 11 之间")
	public String getCiSyId() {
		return ciSyId;
	}

	public void setCiSyId(String ciSyId) {
		this.ciSyId = ciSyId;
	}
	
	@Length(min=1, max=255, message="ci_type长度必须介于 1 和 255 之间")
	public String getCiType() {
		return ciType;
	}

	public void setCiType(String ciType) {
		this.ciType = ciType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCiStartTime() {
		return ciStartTime;
	}

	public void setCiStartTime(Date ciStartTime) {
		this.ciStartTime = ciStartTime;
	}
	
}