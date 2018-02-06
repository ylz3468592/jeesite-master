/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.adver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.adver.dao.AdverDao;

/**
 * 图文广告Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class AdverService extends CrudService<AdverDao, Adver> {
	@Autowired
	private AdverDao adverDao;

	public Adver get(String id) {
		return super.get(id);
	}
	
	public Adver findAdver(String id,String teNo){
		return adverDao.findAdver(id, teNo);
	}
	
	@Transactional(readOnly = false)
	public int update(Adver adver){
		return adverDao.update(adver);
	}
	
	@Transactional(readOnly = false)
	public int updateNot(Adver adver){
		return adverDao.updateNot(adver);
	}
	
	public List<Adver> findList(Adver adver) {
		return super.findList(adver);
	}
	
	public Page<Adver> findPage(Page<Adver> page, Adver adver) {
		return super.findPage(page, adver);
	}
	
	public Page<Adver> findAdver(Page<Adver> page, Adver adver){
		adver.setPage(page);
		//仅查询当前网格员所拥有的终端的广告，如果查询所有网格员，那就不要setUser
		adver.setUser(UserUtils.getUser());
		page.setList(adverDao.findAllList(adver));
		return page;
	}
	
	public Page<Adver> findOpenAdver(Page<Adver> page, Adver adver){
		adver.setPage(page);
		//仅查询当前网格员所拥有的终端的广告，如果查询所有网格员，那就不要setUser
		adver.setUser(UserUtils.getUser());
		//查询状态为已审核的
		adver.setAdStatus("1");
		page.setList(adverDao.findAllList(adver));
		return page;
	}
	
	public List<Adver> findAllList(Adver adver){
		return adverDao.findAllList(adver);
	}
	
	/**
	 * 只查询未审核的 0
	 * @param adver
	 * @return
	 */
	public List<Adver> findAllListNot(Adver adver){
		adver.setAdStatus("0");
		return adverDao.findAllList(adver);
	}
	
	@Transactional(readOnly = false)
	public void save(Adver adver) {
		super.save(adver);
	}
	
	@Transactional(readOnly = false)
	public void mySave(Adver adver) {
		adverDao.mySave(adver);
	}
	
	@Transactional(readOnly = false)
	public void delete(Adver adver) {
		super.delete(adver);
	}
	
}