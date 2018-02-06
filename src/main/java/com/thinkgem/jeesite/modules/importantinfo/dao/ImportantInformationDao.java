/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.importantinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.importantinfo.entity.ImportantInformation;

/**
 * 停电重要信息DAO接口
 * @author tzj
 * @version 2018-01-09
 */
@MyBatisDao
public interface ImportantInformationDao extends CrudDao<ImportantInformation> {
	ImportantInformation findOne(@Param("id")String id,@Param("teNo")String teNo );
	
	int update(ImportantInformation information);
	
	void mySave(ImportantInformation information);
}