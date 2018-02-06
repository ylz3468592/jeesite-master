/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.path.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.path.entity.Property;
import com.thinkgem.jeesite.modules.path.service.PropertyService;

/**
 * 存放路径Controller
 * @author tzj
 * @version 2018-02-06
 */
@Controller
@RequestMapping(value = "${adminPath}/path/property")
public class PropertyController extends BaseController {

	@Autowired
	private PropertyService propertyService;
	
	@ModelAttribute
	public Property get(@RequestParam(required=false) String id) {
		Property entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = propertyService.get(id);
		}
		if (entity == null){
			entity = new Property();
		}
		return entity;
	}
	
	@RequiresPermissions("path:property:view")
	@RequestMapping(value = {"list", ""})
	public String list(Property property, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Property> page = propertyService.findPage(new Page<Property>(request, response), property); 
		model.addAttribute("page", page);
		return "modules/path/propertyList";
	}

	@RequiresPermissions("path:property:view")
	@RequestMapping(value = "form")
	public String form(Property property, Model model) {
		model.addAttribute("property", property);
		return "modules/path/propertyForm";
	}

	@RequiresPermissions("path:property:edit")
	@RequestMapping(value = "save")
	public String save(Property property, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, property)){
			return form(property, model);
		}
		propertyService.save(property);
		addMessage(redirectAttributes, "保存存放路径成功");
		return "redirect:"+Global.getAdminPath()+"/path/property/?repage";
	}
	
	@RequiresPermissions("path:property:edit")
	@RequestMapping(value = "delete")
	public String delete(Property property, RedirectAttributes redirectAttributes) {
		propertyService.delete(property);
		addMessage(redirectAttributes, "删除存放路径成功");
		return "redirect:"+Global.getAdminPath()+"/path/property/?repage";
	}

}