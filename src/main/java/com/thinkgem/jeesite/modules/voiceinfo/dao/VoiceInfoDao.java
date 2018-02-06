/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voiceinfo.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.voiceinfo.entity.VoiceInfo;

/**
 * 语音留言信息DAO接口
 * @author tzj
 * @version 2018-01-09
 */
@MyBatisDao
public interface VoiceInfoDao extends CrudDao<VoiceInfo> {
	int delete(String id);
	
	List<VoiceInfo> getVoice(String id);
}