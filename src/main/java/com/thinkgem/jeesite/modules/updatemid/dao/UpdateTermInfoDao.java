/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.updatemid.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.terminfo.entity.InfoTerm;
import com.thinkgem.jeesite.modules.updatemid.entity.UpdateTermInfo;

/**
 * 升级中间表关联DAO接口
 * @author tzj
 * @version 2018-01-09
 */
@MyBatisDao
public interface UpdateTermInfoDao extends CrudDao<UpdateTermInfo> {
	void deleteUpdateInfo(UpdateTermInfo updateTermInfo);
	
	public int insert(UpdateTermInfo updateTermInfo);
	
	UpdateTermInfo findUpdateInfo(@Param("termId")String termId,@Param("infoId")String infoId);
	
	int update(UpdateTermInfo updateTermInfo);
}