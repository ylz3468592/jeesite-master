/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.updatefile.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.updatefile.entity.UpdateFileInfo;

/**
 * 升级文件记录DAO接口
 * @author tzj
 * @version 2018-01-09
 */
@MyBatisDao
public interface UpdateFileInfoDao extends CrudDao<UpdateFileInfo> {
	UpdateFileInfo findUpInfo(@Param("id")String id,@Param("teNo")String teNo );
	
	void mySave(UpdateFileInfo updateFileInfo);
	
	int update(UpdateFileInfo updateFileInfo);
	
	int updateNot(UpdateFileInfo updateFileInfo);
}