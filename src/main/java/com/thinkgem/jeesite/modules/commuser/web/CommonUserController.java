/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.commuser.web;

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
import com.thinkgem.jeesite.modules.commuser.entity.CommonUser;
import com.thinkgem.jeesite.modules.commuser.service.CommonUserService;

/**
 * 普通用户Controller
 * @author tzj
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/commuser/commonUser")
public class CommonUserController extends BaseController {

	@Autowired
	private CommonUserService commonUserService;
	
	@ModelAttribute
	public CommonUser get(@RequestParam(required=false) String id) {
		CommonUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commonUserService.get(id);
		}
		if (entity == null){
			entity = new CommonUser();
		}
		return entity;
	}
	
	@RequiresPermissions("commuser:commonUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommonUser commonUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommonUser> page = commonUserService.findPage(new Page<CommonUser>(request, response), commonUser); 
		model.addAttribute("page", page);
		return "modules/commuser/commonUserList";
	}

	@RequiresPermissions("commuser:commonUser:view")
	@RequestMapping(value = "form")
	public String form(CommonUser commonUser, Model model) {
		model.addAttribute("commonUser", commonUser);
		return "modules/commuser/commonUserForm";
	}

	@RequiresPermissions("commuser:commonUser:edit")
	@RequestMapping(value = "save")
	public String save(CommonUser commonUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commonUser)){
			return form(commonUser, model);
		}
		commonUserService.save(commonUser);
		addMessage(redirectAttributes, "保存普通用户成功");
		return "redirect:"+Global.getAdminPath()+"/commuser/commonUser/?repage";
	}
	
	@RequiresPermissions("commuser:commonUser:edit")
	@RequestMapping(value = "delete")
	public String delete(CommonUser commonUser, RedirectAttributes redirectAttributes) {
		commonUserService.delete(commonUser);
		addMessage(redirectAttributes, "删除普通用户成功");
		return "redirect:"+Global.getAdminPath()+"/commuser/commonUser/?repage";
	}

}