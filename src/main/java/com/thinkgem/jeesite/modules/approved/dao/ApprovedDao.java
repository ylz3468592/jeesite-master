/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.approved.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.approved.entity.Approved;

/**
 * 审批流程DAO接口
 * @author tzj
 * @version 2018-01-09
 */
@MyBatisDao
public interface ApprovedDao extends CrudDao<Approved> {
	
}