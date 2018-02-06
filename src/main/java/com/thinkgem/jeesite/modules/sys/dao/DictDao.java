/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	/**
	 * 获取所有用户类型，造一个字典
	 * @param dict
	 * @return
	 */
	public List<String> findTypeList(Dict dict);
	
	/**
	 * 获取所有用户，造一个字典
	 * @param dict
	 * @return
	 */
	public List<String> findUserList(Dict dict);
}
