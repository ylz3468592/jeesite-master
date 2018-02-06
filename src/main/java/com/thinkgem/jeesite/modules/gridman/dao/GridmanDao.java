/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gridman.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.gridman.entity.Gridman;

/**
 * 一、二级网格员关联表DAO接口
 * @author tzj
 * @version 2018-01-29
 */
@MyBatisDao
public interface GridmanDao extends CrudDao<Gridman> {
	List<Gridman> findFirstById(String id);
	
	Gridman findSecondById(String id);
	
	int getCount(String id);
	
	int update(Gridman gridman);
	
	Gridman getGrid(String id);
	
	int myDelete(@Param("sysU1Id")String sysU1Id,@Param("sysU2Id")String sysU2Id);
}