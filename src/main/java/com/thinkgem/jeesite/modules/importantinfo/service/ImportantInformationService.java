/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.importantinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.importantinfo.entity.ImportantInformation;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.importantinfo.dao.ImportantInformationDao;

/**
 * 停电重要信息Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class ImportantInformationService extends CrudService<ImportantInformationDao, ImportantInformation> {
	@Autowired
	private ImportantInformationDao informationDao;
	
	public ImportantInformation get(String id) {
		return super.get(id);
	}
	
	public List<ImportantInformation> findList(ImportantInformation importantInformation) {
		return super.findList(importantInformation);
	}
	
	public Page<ImportantInformation> findPage(Page<ImportantInformation> page, ImportantInformation importantInformation) {
		return super.findPage(page, importantInformation);
	}
	
	@Transactional(readOnly = false)
	public void save(ImportantInformation importantInformation) {
		super.save(importantInformation);
	}
	
	@Transactional(readOnly = false)
	public void mySave(ImportantInformation importantInformation) {
		informationDao.mySave(importantInformation);
	}
	
	@Transactional(readOnly = false)
	public void delete(ImportantInformation importantInformation) {
		super.delete(importantInformation);
	}
	
	public ImportantInformation findOne(String id,String teNo){
		return informationDao.findOne(id, teNo);
	}
	
	@Transactional(readOnly = false)
	public int update(ImportantInformation imInformation){
		return informationDao.update(imInformation);
	}
	
	public Page<ImportantInformation> findImInformation(Page<ImportantInformation> page, ImportantInformation imInformation){
		imInformation.setPage(page);
		//仅查询当前网格员所拥有的终端的广告，如果查询所有网格员，那就不要setUser
		imInformation.setUser(UserUtils.getUser());
		page.setList(informationDao.findAllList(imInformation));
		return page;
	}
	
	public Page<ImportantInformation> findOpenImInformation(Page<ImportantInformation> page, ImportantInformation imInformation){
		imInformation.setPage(page);
		//仅查询当前网格员所拥有的终端的广告，如果查询所有网格员，那就不要setUser
		imInformation.setUser(UserUtils.getUser());
		//查询已审核的广告
		imInformation.setImStatus("1");
		page.setList(informationDao.findAllList(imInformation));
		return page;
	}
	
	public List<ImportantInformation> findAllList(ImportantInformation imInformation){
		return informationDao.findAllList(imInformation);
	}
	
	/**
	 * 只查询未审核的 0
	 * @param imInformation
	 * @return
	 */
	public List<ImportantInformation> findAllListNot(ImportantInformation imInformation){
		imInformation.setImStatus("0");
		return informationDao.findAllList(imInformation);
	}
}