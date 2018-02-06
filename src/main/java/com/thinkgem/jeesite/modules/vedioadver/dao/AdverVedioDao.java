/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vedioadver.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.vedioadver.entity.AdverVedio;

/**
 * 视频广告DAO接口
 * @author tzj
 * @version 2018-01-09
 */
@MyBatisDao
public interface AdverVedioDao extends CrudDao<AdverVedio> {
	AdverVedio findVeAdver(@Param("id")String id,@Param("teNo")String teNo );
	
	int updateNot(AdverVedio adverVedio);
	
	int update(AdverVedio adverVedio);
	
	void mySave(AdverVedio adverVedio);
}