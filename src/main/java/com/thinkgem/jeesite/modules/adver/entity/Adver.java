/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.adver.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.term.entity.Term;

/**
 * 图文广告Entity
 * @author tzj
 * @version 2018-01-09
 */
public class Adver extends DataEntity<Adver> {
	
	private static final long serialVersionUID = 1L;
	private String adTitle;		// ad_title
	private String adContent;		// ad_content
	private String adImage;		// ad_image
	private Date adCreateTime;		// ad_create_time
	private String adLiveTime;		// ad_live_time
	private User user;		// ad_creater
	private User userCheck;		// ad_checker
	private Date adCheckTime;		// ad_check_time
	private String adStatus;		// ad_status
	private Term term;
	
	

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Adver() {
		super();
	}

	public Adver(String id){
		super(id);
	}

	@Length(min=1, max=50, message="ad_title长度必须介于 1 和 50 之间")
	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}
	
	public String getAdContent() {
		return adContent;
	}

	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	
	@Length(min=0, max=100, message="ad_image长度必须介于 0 和 100 之间")
	public String getAdImage() {
		return adImage;
	}

	public void setAdImage(String adImage) {
		this.adImage = adImage;
	}
	
	
	public Date getAdCreateTime() {
		return adCreateTime;
	}

	public void setAdCreateTime(Date adCreateTime) {
		this.adCreateTime = adCreateTime;
	}
	
	@Length(min=0, max=11, message="ad_live_time长度必须介于 0 和 11 之间")
	public String getAdLiveTime() {
		return adLiveTime;
	}

	public void setAdLiveTime(String adLiveTime) {
		this.adLiveTime = adLiveTime;
	}
	
	
	
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
	public User getUserCheck() {
		return userCheck;
	}

	public void setUserCheck(User userCheck) {
		this.userCheck = userCheck;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAdCheckTime() {
		return adCheckTime;
	}

	public void setAdCheckTime(Date adCheckTime) {
		this.adCheckTime = adCheckTime;
	}
	
	@Length(min=1, max=2, message="ad_status长度必须介于 1 和 2 之间")
	public String getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}
	
}