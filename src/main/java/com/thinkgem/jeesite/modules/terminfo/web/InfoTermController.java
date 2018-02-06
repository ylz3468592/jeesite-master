/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.terminfo.web;

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
import com.thinkgem.jeesite.modules.terminfo.entity.InfoTerm;
import com.thinkgem.jeesite.modules.terminfo.service.InfoTermService;

/**
 * 信息终端关联Controller
 * @author tzj
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/terminfo/infoTerm")
public class InfoTermController extends BaseController {

	@Autowired
	private InfoTermService infoTermService;
	
	@ModelAttribute
	public InfoTerm get(@RequestParam(required=false) String id) {
		InfoTerm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = infoTermService.get(id);
		}
		if (entity == null){
			entity = new InfoTerm();
		}
		return entity;
	}
	
	@RequiresPermissions("terminfo:infoTerm:view")
	@RequestMapping(value = {"list", ""})
	public String list(InfoTerm infoTerm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InfoTerm> page = infoTermService.findPage(new Page<InfoTerm>(request, response), infoTerm); 
		model.addAttribute("page", page);
		return "modules/terminfo/infoTermList";
	}

	@RequiresPermissions("terminfo:infoTerm:view")
	@RequestMapping(value = "form")
	public String form(InfoTerm infoTerm, Model model) {
		model.addAttribute("infoTerm", infoTerm);
		return "modules/terminfo/infoTermForm";
	}

	@RequiresPermissions("terminfo:infoTerm:edit")
	@RequestMapping(value = "save")
	public String save(InfoTerm infoTerm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, infoTerm)){
			return form(infoTerm, model);
		}
		infoTermService.save(infoTerm);
		addMessage(redirectAttributes, "保存信息终端关联成功");
		return "redirect:"+Global.getAdminPath()+"/terminfo/infoTerm/?repage";
	}
	
	@RequiresPermissions("terminfo:infoTerm:edit")
	@RequestMapping(value = "delete")
	public String delete(InfoTerm infoTerm, RedirectAttributes redirectAttributes) {
		infoTermService.delete(infoTerm);
		addMessage(redirectAttributes, "删除信息终端关联成功");
		return "redirect:"+Global.getAdminPath()+"/terminfo/infoTerm/?repage";
	}

}