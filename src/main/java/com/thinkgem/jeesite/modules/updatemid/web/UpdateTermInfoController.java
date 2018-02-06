/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.updatemid.web;

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
import com.thinkgem.jeesite.modules.updatemid.entity.UpdateTermInfo;
import com.thinkgem.jeesite.modules.updatemid.service.UpdateTermInfoService;

/**
 * 升级中间表关联Controller
 * @author tzj
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/updatemid/updateTermInfo")
public class UpdateTermInfoController extends BaseController {

	@Autowired
	private UpdateTermInfoService updateTermInfoService;
	
	@ModelAttribute
	public UpdateTermInfo get(@RequestParam(required=false) String id) {
		UpdateTermInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = updateTermInfoService.get(id);
		}
		if (entity == null){
			entity = new UpdateTermInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("updatemid:updateTermInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UpdateTermInfo updateTermInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UpdateTermInfo> page = updateTermInfoService.findPage(new Page<UpdateTermInfo>(request, response), updateTermInfo); 
		model.addAttribute("page", page);
		return "modules/updatemid/updateTermInfoList";
	}

	@RequiresPermissions("updatemid:updateTermInfo:view")
	@RequestMapping(value = "form")
	public String form(UpdateTermInfo updateTermInfo, Model model) {
		model.addAttribute("updateTermInfo", updateTermInfo);
		return "modules/updatemid/updateTermInfoForm";
	}

	@RequiresPermissions("updatemid:updateTermInfo:edit")
	@RequestMapping(value = "save")
	public String save(UpdateTermInfo updateTermInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, updateTermInfo)){
			return form(updateTermInfo, model);
		}
		updateTermInfoService.save(updateTermInfo);
		addMessage(redirectAttributes, "保存升级中间表关联成功");
		return "redirect:"+Global.getAdminPath()+"/updatemid/updateTermInfo/?repage";
	}
	
	@RequiresPermissions("updatemid:updateTermInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(UpdateTermInfo updateTermInfo, RedirectAttributes redirectAttributes) {
		updateTermInfoService.delete(updateTermInfo);
		addMessage(redirectAttributes, "删除升级中间表关联成功");
		return "redirect:"+Global.getAdminPath()+"/updatemid/updateTermInfo/?repage";
	}

}