/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vedioadver.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.term.entity.Term;

/**
 * 视频广告Entity
 * @author tzj
 * @version 2018-01-09
 */
public class AdverVedio extends DataEntity<AdverVedio> {
	
	private static final long serialVersionUID = 1L;
	private String veTitle;		// ve_title
	private String vePath;		// ve_path
	private Date veCreateTime;		// ve_create_time
	private User userChecker;		// ve_checker
	private User user;		// ve_creater
	private Date veCheckTime;		// ve_check_time
	private String veStatus;		// ve_status
	private Term term;
	
	public AdverVedio() {
		super();
	}

	public AdverVedio(String id){
		super(id);
	}

	@Length(min=1, max=50, message="ve_title长度必须介于 1 和 50 之间")
	public String getVeTitle() {
		return veTitle;
	}

	public void setVeTitle(String veTitle) {
		this.veTitle = veTitle;
	}
	
	
	public String getVePath() {
		return vePath;
	}

	public void setVePath(String vePath) {
		this.vePath = vePath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	public Date getVeCreateTime() {
		return veCreateTime;
	}

	public void setVeCreateTime(Date veCreateTime) {
		this.veCreateTime = veCreateTime;
	}
	
	
	
	
	
	public User getUserChecker() {
		return userChecker;
	}

	public void setUserChecker(User userChecker) {
		this.userChecker = userChecker;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVeCheckTime() {
		return veCheckTime;
	}

	public void setVeCheckTime(Date veCheckTime) {
		this.veCheckTime = veCheckTime;
	}
	
	@Length(min=1, max=2, message="ve_status长度必须介于 1 和 2 之间")
	public String getVeStatus() {
		return veStatus;
	}

	public void setVeStatus(String veStatus) {
		this.veStatus = veStatus;
	}
	
}