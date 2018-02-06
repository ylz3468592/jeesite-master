/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.term.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.term.entity.Term;

/**
 * 终端DAO接口
 * @author 田卓军
 * @version 2018-01-09
 */
@MyBatisDao
public interface TermDao extends CrudDao<Term> {
	Term findTerm(String teNo);
	
	void updateTerm(Term term);
	
	List<Term> findList();
	
	List<Term> findSysList();
	
	int update(Term term);
	
	Term findTermByNo(String teNo);
	
	List<Term> findTermBySysId(String id);
}