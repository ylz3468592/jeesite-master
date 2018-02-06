/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.updatemid.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 升级中间表关联Entity
 * @author tzj
 * @version 2018-01-09
 */
public class UpdateTermInfo extends DataEntity<UpdateTermInfo> {
	
	private static final long serialVersionUID = 1L;
	private Date utUpdateTime;		// ut_update_time
	private String utTermId;		// ut_term_id
	private String utUiId;		// ut_ui_id
	private String empty1;		// empty_1
	private String empty2;		// empty_2
	
	public UpdateTermInfo() {
		super();
	}

	public UpdateTermInfo(String id){
		super(id);
	}

	
	public Date getUtUpdateTime() {
		return utUpdateTime;
	}

	public void setUtUpdateTime(Date utUpdateTime) {
		this.utUpdateTime = utUpdateTime;
	}
	
	@Length(min=1, max=11, message="ut_term_id长度必须介于 1 和 11 之间")
	public String getUtTermId() {
		return utTermId;
	}

	public void setUtTermId(String utTermId) {
		this.utTermId = utTermId;
	}
	
	@Length(min=1, max=11, message="ut_ui_id长度必须介于 1 和 11 之间")
	public String getUtUiId() {
		return utUiId;
	}

	public void setUtUiId(String utUiId) {
		this.utUiId = utUiId;
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
	
}