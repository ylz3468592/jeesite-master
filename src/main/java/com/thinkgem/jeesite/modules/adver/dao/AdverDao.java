/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.adver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.adver.entity.Adver;

/**
 * 图文广告DAO接口
 * @author tzj
 * @version 2018-01-09
 */
@MyBatisDao
public interface AdverDao extends CrudDao<Adver> {
	Adver findAdver(@Param("id")String id,@Param("teNo")String teNo );
	
	int update(Adver adver);
	
	int updateNot(Adver adver);
	
	void mySave(Adver adver);
}