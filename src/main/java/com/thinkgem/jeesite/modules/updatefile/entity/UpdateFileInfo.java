/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.updatefile.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.term.entity.Term;

/**
 * 升级文件记录Entity
 * @author tzj
 * @version 2018-01-09
 */
public class UpdateFileInfo extends DataEntity<UpdateFileInfo> {
	
	private static final long serialVersionUID = 1L;
	private Date uiDate;		// ui_date
	private String uiFilePath;		// ui_file_path
	private String uiFileVersion;		// ui_file_version
	private Term term;
	private String empty1;		// 升级状态 0：未升级 1：已升级
	private String empty2;		// empty_2
	private String empty3;		// empty_3
	private String empty4;		// empty_4
	
	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public UpdateFileInfo() {
		super();
	}

	public UpdateFileInfo(String id){
		super(id);
	}

	
	public Date getUiDate() {
		return uiDate;
	}

	public void setUiDate(Date uiDate) {
		this.uiDate = uiDate;
	}
	
	@Length(min=1, max=100, message="ui_file_path长度必须介于 1 和 100 之间")
	public String getUiFilePath() {
		return uiFilePath;
	}

	public void setUiFilePath(String uiFilePath) {
		this.uiFilePath = uiFilePath;
	}
	
	@Length(min=1, max=100, message="ui_file_version长度必须介于 1 和 100 之间")
	public String getUiFileVersion() {
		return uiFileVersion;
	}

	public void setUiFileVersion(String uiFileVersion) {
		this.uiFileVersion = uiFileVersion;
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