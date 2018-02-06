/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voiceinfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.commuser.entity.CommonUser;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.term.entity.Term;

/**
 * 语音留言信息Entity
 * @author tzj
 * @version 2018-01-09
 */
public class VoiceInfo extends DataEntity<VoiceInfo> {
	
	private static final long serialVersionUID = 1L;
	private String coId;		// vi_co_id
	private Date viLeaveDate;		// 居民留言时间
	private String  syId;		// vi_sy_id
	private String  termNo;		// vi_te_id
	private String viPath;		// 语音文件路径
	private Date viSaveTime;		// vi_save_time
	private String 	viCoTel;		//居民号码
	
	
	

	

	

	public String getCoId() {
		return coId;
	}

	public void setCoId(String coId) {
		this.coId = coId;
	}

	public String getSyId() {
		return syId;
	}

	public void setSyId(String syId) {
		this.syId = syId;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getViCoTel() {
		return viCoTel;
	}

	public void setViCoTel(String viCoTel) {
		this.viCoTel = viCoTel;
	}

	public VoiceInfo() {
		super();
	}

	public VoiceInfo(String id){
		super(id);
	}

	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="vi_leave_date不能为空")
	public Date getViLeaveDate() {
		return viLeaveDate;
	}

	public void setViLeaveDate(Date viLeaveDate) {
		this.viLeaveDate = viLeaveDate;
	}
	
	
	
	
	@Length(min=0, max=100, message="vi_path长度必须介于 0 和 100 之间")
	public String getViPath() {
		return viPath;
	}

	public void setViPath(String viPath) {
		this.viPath = viPath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getViSaveTime() {
		return viSaveTime;
	}

	public void setViSaveTime(Date viSaveTime) {
		this.viSaveTime = viSaveTime;
	}
	
}