/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.commuser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.commuser.entity.CommonUser;
import com.thinkgem.jeesite.modules.commuser.dao.CommonUserDao;

/**
 * 普通用户Service
 * @author tzj
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class CommonUserService extends CrudService<CommonUserDao, CommonUser> {

	public CommonUser get(String id) {
		return super.get(id);
	}
	
	public List<CommonUser> findList(CommonUser commonUser) {
		return super.findList(commonUser);
	}
	
	public Page<CommonUser> findPage(Page<CommonUser> page, CommonUser commonUser) {
		return super.findPage(page, commonUser);
	}
	
	@Transactional(readOnly = false)
	public void save(CommonUser commonUser) {
		super.save(commonUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommonUser commonUser) {
		super.delete(commonUser);
	}
	
}