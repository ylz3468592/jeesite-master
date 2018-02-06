/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.path.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.path.entity.Property;

/**
 * 存放路径DAO接口
 * @author tzj
 * @version 2018-02-06
 */
@MyBatisDao
public interface PropertyDao extends CrudDao<Property> {
	Property getPath(String id);
}