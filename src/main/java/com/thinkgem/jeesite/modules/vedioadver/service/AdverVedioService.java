/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vedioadver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vedioadver.entity.AdverVedio;
import com.thinkgem.jeesite.modules.vedioadver.dao.AdverVedioDao;

/**
 * 视频广告Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class AdverVedioService extends CrudService<AdverVedioDao, AdverVedio> {
	@Autowired
	private AdverVedioDao adverVedioDao;
	
	public AdverVedio get(String id) {
		return super.get(id);
	}
	
	public List<AdverVedio> findList(AdverVedio adverVedio) {
		return super.findList(adverVedio);
	}
	
	public Page<AdverVedio> findPage(Page<AdverVedio> page, AdverVedio adverVedio) {
		return super.findPage(page, adverVedio);
	}
	
	@Transactional(readOnly = false)
	public void save(AdverVedio adverVedio) {
		super.save(adverVedio);
	}
	
	@Transactional(readOnly = false)
	public void mySave(AdverVedio adverVedio) {
		adverVedioDao.mySave(adverVedio);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdverVedio adverVedio) {
		super.delete(adverVedio);
	}
	
	public AdverVedio findVeAdver(String id,String teNo){
		return	adverVedioDao.findVeAdver(id, teNo);
	}
	
	@Transactional(readOnly = false)
	public int updateNot(AdverVedio adverVedio){
		return adverVedioDao.updateNot(adverVedio);
	}
	
	@Transactional(readOnly = false)
	public int update(AdverVedio adverVedio){
		return adverVedioDao.update(adverVedio);
	}
	
	public Page<AdverVedio> findVedioAdver(Page<AdverVedio> page, AdverVedio adverVedio){
		adverVedio.setPage(page);
		//仅查询当前网格员所拥有的终端的广告，如果查询所有网格员，那就不要setUser
		adverVedio.setUser(UserUtils.getUser());
		page.setList(adverVedioDao.findAllList(adverVedio));
		return page;
	}
	
	public Page<AdverVedio> findOpenVedioAdver(Page<AdverVedio> page, AdverVedio adverVedio){
		adverVedio.setPage(page);
		//仅查询当前网格员所拥有的终端的广告，如果查询所有网格员，那就不要setUser
		adverVedio.setUser(UserUtils.getUser());
		//查询状态为已审核的
		adverVedio.setVeStatus("1");
		page.setList(adverVedioDao.findAllList(adverVedio));
		return page;
	}
	
	public List<AdverVedio> findAllList(AdverVedio adverVedio){
		return adverVedioDao.findAllList(adverVedio);
	}
	
	/**
	 * 只查询未审核的 0
	 * @param adverVedio
	 * @return
	 */
	public List<AdverVedio> findAllListNot(AdverVedio adverVedio){
		adverVedio.setVeStatus("0");
		return adverVedioDao.findAllList(adverVedio);
	}
}