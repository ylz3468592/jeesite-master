/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.approved.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.approved.entity.Approved;
import com.thinkgem.jeesite.modules.approved.dao.ApprovedDao;

/**
 * 审批流程Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class ApprovedService extends CrudService<ApprovedDao, Approved> {

	public Approved get(String id) {
		return super.get(id);
	}
	
	public List<Approved> findList(Approved approved) {
		return super.findList(approved);
	}
	
	public Page<Approved> findPage(Page<Approved> page, Approved approved) {
		return super.findPage(page, approved);
	}
	
	@Transactional(readOnly = false)
	public void save(Approved approved) {
		super.save(approved);
	}
	
	@Transactional(readOnly = false)
	public void delete(Approved approved) {
		super.delete(approved);
	}
	
}