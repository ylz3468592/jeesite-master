/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.updatemid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.updatemid.entity.UpdateTermInfo;
import com.thinkgem.jeesite.modules.updatemid.dao.UpdateTermInfoDao;

/**
 * 升级中间表关联Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class UpdateTermInfoService extends CrudService<UpdateTermInfoDao, UpdateTermInfo> {
	@Autowired
	private UpdateTermInfoDao updateTermInfoDao;
	
	public UpdateTermInfo get(String id) {
		return super.get(id);
	}
	
	public List<UpdateTermInfo> findList(UpdateTermInfo updateTermInfo) {
		return super.findList(updateTermInfo);
	}
	
	public Page<UpdateTermInfo> findPage(Page<UpdateTermInfo> page, UpdateTermInfo updateTermInfo) {
		return super.findPage(page, updateTermInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(UpdateTermInfo updateTermInfo) {
		super.save(updateTermInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(UpdateTermInfo updateTermInfo) {
		super.delete(updateTermInfo);
	}
	
	@Transactional(readOnly = false)
	public int update(UpdateTermInfo updateTermInfo) {
		return updateTermInfoDao.update(updateTermInfo);
	}
	
	/**
	 * 删除中间表的记录
	 * @param updateTermInfo
	 */
	@Transactional(readOnly = false)
	public void deleteUpdateInfo(UpdateTermInfo updateTermInfo){
		updateTermInfoDao.deleteUpdateInfo(updateTermInfo);
	}
	
	
	public UpdateTermInfo findUpdateInfo(String termId,String infoId){
		return updateTermInfoDao.findUpdateInfo(termId, infoId);
	}
	
	@Transactional(readOnly = false)
	public int insertUpdateInfo(UpdateTermInfo updateTermInfo){
		return updateTermInfoDao.insert(updateTermInfo);
	}
	
}