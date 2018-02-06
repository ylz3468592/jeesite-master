/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voiceinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.voiceinfo.entity.VoiceInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voiceinfo.dao.VoiceInfoDao;

/**
 * 语音留言信息Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class VoiceInfoService extends CrudService<VoiceInfoDao, VoiceInfo> {
	@Autowired
	private VoiceInfoDao voiceInfoDao;
	
	public VoiceInfo get(String id) {
		return super.get(id);
	}
	
	public List<VoiceInfo> findList(VoiceInfo voiceInfo) {
		return super.findList(voiceInfo);
	}
	
	public Page<VoiceInfo> findPage(Page<VoiceInfo> page, VoiceInfo voiceInfo) {
		return super.findPage(page, voiceInfo);
	}
	
	public Page<VoiceInfo> findVoiceInfo(Page<VoiceInfo> page, VoiceInfo voiceInfo) {
		voiceInfo.setPage(page);
		//仅查询当前网格员对应的居民留言
		String id=UserUtils.getUser().getId();
		page.setList(voiceInfoDao.getVoice(id));
		return super.findPage(page, voiceInfo);
	}
	
	public List<VoiceInfo> getVoice(){
		String id=UserUtils.getUser().getId();
		return voiceInfoDao.getVoice(id);
	}
	
	@Transactional(readOnly = false)
	public void save(VoiceInfo voiceInfo) {
		super.save(voiceInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		voiceInfoDao.delete(id);
	}
	
}