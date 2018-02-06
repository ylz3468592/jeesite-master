/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.path.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.path.entity.Property;
import com.thinkgem.jeesite.modules.path.dao.PropertyDao;

/**
 * 存放路径Service
 * @author tzj
 * @version 2018-02-06
 */
@Service
@Transactional(readOnly = true)
public class PropertyService extends CrudService<PropertyDao, Property> {
	@Autowired
	private PropertyDao propertyDao;
	public Property getFileServerUrl(){
	    String id="url";
	    return propertyDao.getPath(id);
	  }
	
	public Property getPath(String id){
		return propertyDao.getPath(id);
	}
	
	public Property getImagePath(){
		String id="image_path";
		return propertyDao.getPath(id);		
	}
	
	public Property getVedioPath(){
		String id="vedio_path";
		return propertyDao.getPath(id);		
	} 
	
	public Property getUpdatePath(){
		String id="update_path";
		return propertyDao.getPath(id);		
	}
	
	public Property getVoicePath(){
		String id="voice_path";
		return propertyDao.getPath(id);		
	}
	
	public Property get(String id) {
		return super.get(id);
	}
	
	public List<Property> findList(Property property) {
		return super.findList(property);
	}
	
	public Page<Property> findPage(Page<Property> page, Property property) {
		return super.findPage(page, property);
	}
	
	@Transactional(readOnly = false)
	public void save(Property property) {
		super.save(property);
	}
	
	@Transactional(readOnly = false)
	public void delete(Property property) {
		super.delete(property);
	}
	
}