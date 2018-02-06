/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.terminfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.term.entity.Term;

/**
 * 信息终端关联Entity
 * @author tzj
 * @version 2018-01-09
 */
public class InfoTerm extends DataEntity<InfoTerm> {
	
	private static final long serialVersionUID = 1L;
	private String itTermId;		// it_term_id
	private String itInfoId;		// it_info_id
	private Adver adver;
	private Term term;
	private String empty1;		// empty_1
	private String empty2;		// empty_2
	private String empty3;		// empty_3
	private String empty4;		// empty_4
	
	public Adver getAdver() {
		return adver;
	}

	public void setAdver(Adver adver) {
		this.adver = adver;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public InfoTerm() {
		super();
	}

	public InfoTerm(String id){
		super(id);
	}

	@Length(min=1, max=11, message="it_term_id长度必须介于 1 和 11 之间")
	public String getItTermId() {
		return itTermId;
	}

	public void setItTermId(String itTermId) {
		this.itTermId = itTermId;
	}
	
	@Length(min=1, max=32, message="it_info_id长度必须介于 1 和 32 之间")
	public String getItInfoId() {
		return itInfoId;
	}

	public void setItInfoId(String itInfoId) {
		this.itInfoId = itInfoId;
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