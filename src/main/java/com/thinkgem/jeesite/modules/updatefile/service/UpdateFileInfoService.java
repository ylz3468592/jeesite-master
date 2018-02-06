/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.updatefile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.updatefile.entity.UpdateFileInfo;
import com.thinkgem.jeesite.modules.updatefile.dao.UpdateFileInfoDao;

/**
 * 升级文件记录Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class UpdateFileInfoService extends CrudService<UpdateFileInfoDao, UpdateFileInfo> {
	@Autowired
	private UpdateFileInfoDao fileInfoDao;
	
	public UpdateFileInfo findUpInfo(String id,String teNo){
		return fileInfoDao.findUpInfo(id, teNo);
	}
	
	public UpdateFileInfo get(String id) {
		return super.get(id);
	}
	
	public List<UpdateFileInfo> findList(UpdateFileInfo updateFileInfo) {
		return super.findList(updateFileInfo);
	}
	
	public Page<UpdateFileInfo> findPage(Page<UpdateFileInfo> page, UpdateFileInfo updateFileInfo) {
		return super.findPage(page, updateFileInfo);
	}
	
	public Page<UpdateFileInfo> findUpdateFile(Page<UpdateFileInfo> page, UpdateFileInfo fileInfo){
		fileInfo.setPage(page);
		//管理员对所有终端进行升级操作
		page.setList(fileInfoDao.findAllList(fileInfo));
		return page;
	}
	
	public Page<UpdateFileInfo> findOpenUpdateFile(Page<UpdateFileInfo> page, UpdateFileInfo fileInfo){
		fileInfo.setPage(page);
		//管理员对所有终端进行升级操作
		//查询状态为已审核的
		fileInfo.setEmpty1("0");
		page.setList(fileInfoDao.findAllList(fileInfo));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(UpdateFileInfo updateFileInfo) {
		super.save(updateFileInfo);
	}
	
	@Transactional(readOnly = false)
	public void mySave(UpdateFileInfo updateFileInfo) {
		fileInfoDao.mySave(updateFileInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(UpdateFileInfo updateFileInfo) {
		super.delete(updateFileInfo);
	}
	
	@Transactional(readOnly = false)
	public int update(UpdateFileInfo updateFileInfo) {
		return fileInfoDao.update(updateFileInfo);
	}
	
	@Transactional(readOnly = false)
	public int updateNot(UpdateFileInfo updateFileInfo) {
		return fileInfoDao.updateNot(updateFileInfo);
	}
}