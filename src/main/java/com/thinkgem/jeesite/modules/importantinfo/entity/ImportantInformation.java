/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.importantinfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.term.entity.Term;

/**
 * 停电重要信息Entity
 * @author tzj
 * @version 2018-01-09
 */
public class ImportantInformation extends DataEntity<ImportantInformation> {
	
	private static final long serialVersionUID = 1L;
	private String imTitle;		// im_title
	private String imAddress;		// im_address
	private Date imStopTime;		// im_stop_time
	private String imStopType;		// im_stop_type
	private String imStopArea;		// im_stop_area
	private String imStopLine;		// im_stop_line
	private Date imCreateTime;		// im_create_time
	private User user;		// im_creater
	private User userChecker;		// im_checker
	private Date imCheckTime;		// im_check_time
	private String imStatus;		// im_status
	private Term term;
	private Date imStopTimeEnd;		//im_stop_time_end
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="im_stop_time_end不能为空")
	public Date getImStopTimeEnd() {
		return imStopTimeEnd;
	}

	public void setImStopTimeEnd(Date imStopTimeEnd) {
		this.imStopTimeEnd = imStopTimeEnd;
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

	public ImportantInformation() {
		super();
	}

	public ImportantInformation(String id){
		super(id);
	}

	@Length(min=1, max=50, message="im_title长度必须介于 1 和 50 之间")
	public String getImTitle() {
		return imTitle;
	}

	public void setImTitle(String imTitle) {
		this.imTitle = imTitle;
	}
	
	@Length(min=0, max=100, message="im_address长度必须介于 0 和 100 之间")
	public String getImAddress() {
		return imAddress;
	}

	public void setImAddress(String imAddress) {
		this.imAddress = imAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="im_stop_time不能为空")
	public Date getImStopTime() {
		return imStopTime;
	}

	public void setImStopTime(Date imStopTime) {
		this.imStopTime = imStopTime;
	}
	
	
	public String getImStopType() {
		return imStopType;
	}

	public void setImStopType(String imStopType) {
		this.imStopType = imStopType;
	}
	
	@Length(min=1, max=100, message="im_stop_area长度必须介于 1 和 100 之间")
	public String getImStopArea() {
		return imStopArea;
	}

	public void setImStopArea(String imStopArea) {
		this.imStopArea = imStopArea;
	}
	
	@Length(min=0, max=100, message="im_stop_line长度必须介于 0 和 100 之间")
	public String getImStopLine() {
		return imStopLine;
	}

	public void setImStopLine(String imStopLine) {
		this.imStopLine = imStopLine;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getImCreateTime() {
		return imCreateTime;
	}

	public void setImCreateTime(Date imCreateTime) {
		this.imCreateTime = imCreateTime;
	}
	
	
	
	
	
	public User getUserChecker() {
		return userChecker;
	}

	public void setUserChecker(User userChecker) {
		this.userChecker = userChecker;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getImCheckTime() {
		return imCheckTime;
	}

	public void setImCheckTime(Date imCheckTime) {
		this.imCheckTime = imCheckTime;
	}
	
	@Length(min=0, max=2, message="im_status长度必须介于 0 和 2 之间")
	public String getImStatus() {
		return imStatus;
	}

	public void setImStatus(String imStatus) {
		this.imStatus = imStatus;
	}
	
}