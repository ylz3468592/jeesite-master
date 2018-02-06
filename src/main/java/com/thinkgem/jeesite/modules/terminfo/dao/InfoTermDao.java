/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.terminfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.term.entity.Term;
import com.thinkgem.jeesite.modules.terminfo.entity.InfoTerm;

/**
 * 信息终端关联DAO接口
 * @author tzj
 * @version 2018-01-09
 */
@MyBatisDao
public interface InfoTermDao extends CrudDao<InfoTerm> {
	InfoTerm findTermInfo(@Param("termId")String termId,@Param("infoId")String infoId);
	
	public int insert(InfoTerm infoTerm);
	
	void deleteInfoTerm(InfoTerm infoTerm);
	
	int updateTermInfo(InfoTerm infoTerm);
	
}