/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.terminfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.terminfo.entity.InfoTerm;
import com.thinkgem.jeesite.modules.terminfo.dao.InfoTermDao;

/**
 * 信息终端关联Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class InfoTermService extends CrudService<InfoTermDao, InfoTerm> {
	@Autowired
	private InfoTermDao infoTermDao;
	
	public InfoTerm get(String id) {
		return super.get(id);
	}
	
	public List<InfoTerm> findList(InfoTerm infoTerm) {
		return super.findList(infoTerm);
	}
	
	public Page<InfoTerm> findPage(Page<InfoTerm> page, InfoTerm infoTerm) {
		return super.findPage(page, infoTerm);
	}
	
	@Transactional(readOnly = false)
	public void save(InfoTerm infoTerm) {
		super.save(infoTerm);
	}
	
	@Transactional(readOnly = false)
	public void delete(InfoTerm infoTerm) {
		super.delete(infoTerm);
	}
	
	@Transactional(readOnly = false)
	public void deleteInfoTerm(InfoTerm infoTerm) {
		infoTermDao.deleteInfoTerm(infoTerm);
	}
	
	public InfoTerm findTermInfo(String termId,String infoId){
		return infoTermDao.findTermInfo(termId,infoId);
	}
	
	@Transactional(readOnly = false)
	public int insertInfoTerm(InfoTerm infoTerm){
		return infoTermDao.insert(infoTerm);
	}
	
	@Transactional(readOnly = false)
	public int updateInfoTerm(InfoTerm infoTerm){
		return infoTermDao.updateTermInfo(infoTerm);
	}
}