/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gridman.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.gridman.entity.Gridman;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.gridman.dao.GridmanDao;

/**
 * 一、二级网格员关联表Service
 * @author tzj
 * @version 2018-01-29
 */
@Service
@Transactional(readOnly = true)
public class GridmanService extends CrudService<GridmanDao, Gridman> {
	@Autowired
	private GridmanDao gridmanDao;
	@Autowired
	private UserDao userDao;
	
	@Transactional(readOnly = false)
	public int myDelete(String u1Id,String u2id){
		return gridmanDao.myDelete(u1Id, u2id);
	}
	
	public List<Gridman> findFirstById(String id){
		return gridmanDao.findFirstById(id);
	}
	
	public Gridman findSecondById(String id){
		return gridmanDao.findSecondById(id);
	}
	
	public Gridman get(String id) {
		return super.get(id);
	}
	
	public List<Gridman> findList(Gridman gridman) {
		return super.findList(gridman);
	}
	
	public Page<Gridman> findPage(Page<Gridman> page, Gridman gridman) {
		return super.findPage(page, gridman);
	}
	
	public Page<User> findFirstGrid(Page<User> page, User user) {
		user.setPage(page);
		String roleName="一级网格管理员";
		String flag="0";
		page.setList(userDao.getAllFirstGrid(roleName,flag));
		return page;
	}
	
	public Page<User> findSecondGrid(Page<User> page, User user) {
		user.setPage(page);
		String roleName="二级网格管理员";
		String flag="0";
		page.setList(userDao.getAllFirstGrid(roleName,flag));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Gridman gridman) {
		super.save(gridman);
	}
	
	@Transactional(readOnly = false)
	public void delete(Gridman gridman) {
		super.delete(gridman);
	}
	
	public int getCount(String id){
		return gridmanDao.getCount(id);
	}
	
	@Transactional(readOnly = false)
	public int update(Gridman gridman){
		return gridmanDao.update(gridman);
	}
	
	public Gridman getGrid(String id){
		return gridmanDao.getGrid(id);
	}
}