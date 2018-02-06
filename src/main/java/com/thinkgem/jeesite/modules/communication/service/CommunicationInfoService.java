/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.communication.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.communication.entity.CommunicationInfo;
import com.thinkgem.jeesite.modules.communication.dao.CommunicationInfoDao;

/**
 * 通讯记录保存Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class CommunicationInfoService extends CrudService<CommunicationInfoDao, CommunicationInfo> {

	public CommunicationInfo get(String id) {
		return super.get(id);
	}
	
	public List<CommunicationInfo> findList(CommunicationInfo communicationInfo) {
		return super.findList(communicationInfo);
	}
	
	public Page<CommunicationInfo> findPage(Page<CommunicationInfo> page, CommunicationInfo communicationInfo) {
		return super.findPage(page, communicationInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CommunicationInfo communicationInfo) {
		super.save(communicationInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommunicationInfo communicationInfo) {
		super.delete(communicationInfo);
	}
	
}