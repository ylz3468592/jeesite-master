/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.approved.web;

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
import com.thinkgem.jeesite.modules.approved.entity.Approved;
import com.thinkgem.jeesite.modules.approved.service.ApprovedService;

/**
 * 审批流程Controller
 * @author tzj
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/approved/approved")
public class ApprovedController extends BaseController {

	@Autowired
	private ApprovedService approvedService;
	
	@ModelAttribute
	public Approved get(@RequestParam(required=false) String id) {
		Approved entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = approvedService.get(id);
		}
		if (entity == null){
			entity = new Approved();
		}
		return entity;
	}
	
	@RequiresPermissions("approved:approved:view")
	@RequestMapping(value = {"list", ""})
	public String list(Approved approved, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Approved> page = approvedService.findPage(new Page<Approved>(request, response), approved); 
		model.addAttribute("page", page);
		return "modules/approved/approvedList";
	}

	@RequiresPermissions("approved:approved:view")
	@RequestMapping(value = "form")
	public String form(Approved approved, Model model) {
		model.addAttribute("approved", approved);
		return "modules/approved/approvedForm";
	}

	@RequiresPermissions("approved:approved:edit")
	@RequestMapping(value = "save")
	public String save(Approved approved, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, approved)){
			return form(approved, model);
		}
		approvedService.save(approved);
		addMessage(redirectAttributes, "保存审批流程成功");
		return "redirect:"+Global.getAdminPath()+"/approved/approved/?repage";
	}
	
	@RequiresPermissions("approved:approved:edit")
	@RequestMapping(value = "delete")
	public String delete(Approved approved, RedirectAttributes redirectAttributes) {
		approvedService.delete(approved);
		addMessage(redirectAttributes, "删除审批流程成功");
		return "redirect:"+Global.getAdminPath()+"/approved/approved/?repage";
	}

}