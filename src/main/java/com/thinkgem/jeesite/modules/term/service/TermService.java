/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.term.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.term.entity.Term;
import com.thinkgem.jeesite.modules.term.dao.TermDao;

/**
 * 终端Service
 * @author 田卓军
 * @version 2018-01-09
 */
@Service
@Transactional(readOnly = true)
public class TermService extends CrudService<TermDao, Term> {
	@Autowired
	private TermDao termDao;
	
	public List<Term> findTermBySysId(String id){
		return termDao.findTermBySysId(id);
	}
	
	public Term get(String id) {
		return super.get(id);
	}
	
	public List<Term> findList(Term term) {
		return super.findList(term);
	}
	
	public Page<Term> findPage(Page<Term> page, Term term) {
		return super.findPage(page, term);
	}
	
	@Transactional(readOnly = false)
	public void save(Term term) {
		super.save(term);
	}
	
	@Transactional(readOnly = false)
	public void updateTerm(Term term) {
		termDao.updateTerm(term);
	}
	
	@Transactional(readOnly = false)
	public void update(Term term) {
		termDao.update(term);
	}
	
	@Transactional(readOnly = false)
	public void delete(Term term) {
		super.delete(term);
	}
	
	public Term findTerm(String teNo){
		return termDao.findTerm(teNo);
	}
	
	public Page<Term> findTerm(Page<Term> page, Term term){
		term.setPage(page);
		page.setList(termDao.findAllList(term));
		return page;
	}
	
	/**
	 * 网格员查询自己所拥有的所有终端
	 * @param term
	 * @return
	 */
	public List<Term> findTermList(Term term){
		term.setUser(UserUtils.getUser());
		return termDao.findList(term);
	}
	
	/**
	 * 查询所有终端信息
	 * @return
	 */
	public List<Term> findSysList(){
		return termDao.findSysList();
	}
	
	public Term findTermByNo(String teNo){
		return termDao.findTermByNo(teNo);
	}
}