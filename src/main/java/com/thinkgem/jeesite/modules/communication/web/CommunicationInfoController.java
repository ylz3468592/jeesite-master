/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.communication.web;

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
import com.thinkgem.jeesite.modules.communication.entity.CommunicationInfo;
import com.thinkgem.jeesite.modules.communication.service.CommunicationInfoService;

/**
 * 通讯记录保存Controller
 * @author tzj
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/communication/communicationInfo")
public class CommunicationInfoController extends BaseController {

	@Autowired
	private CommunicationInfoService communicationInfoService;
	
	@ModelAttribute
	public CommunicationInfo get(@RequestParam(required=false) String id) {
		CommunicationInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = communicationInfoService.get(id);
		}
		if (entity == null){
			entity = new CommunicationInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("communication:communicationInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommunicationInfo communicationInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommunicationInfo> page = communicationInfoService.findPage(new Page<CommunicationInfo>(request, response), communicationInfo); 
		model.addAttribute("page", page);
		return "modules/communication/communicationInfoList";
	}

	@RequiresPermissions("communication:communicationInfo:view")
	@RequestMapping(value = "form")
	public String form(CommunicationInfo communicationInfo, Model model) {
		model.addAttribute("communicationInfo", communicationInfo);
		return "modules/communication/communicationInfoForm";
	}

	@RequiresPermissions("communication:communicationInfo:edit")
	@RequestMapping(value = "save")
	public String save(CommunicationInfo communicationInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, communicationInfo)){
			return form(communicationInfo, model);
		}
		communicationInfoService.save(communicationInfo);
		addMessage(redirectAttributes, "保存通讯记录保存成功");
		return "redirect:"+Global.getAdminPath()+"/communication/communicationInfo/?repage";
	}
	
	@RequiresPermissions("communication:communicationInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CommunicationInfo communicationInfo, RedirectAttributes redirectAttributes) {
		communicationInfoService.delete(communicationInfo);
		addMessage(redirectAttributes, "删除通讯记录保存成功");
		return "redirect:"+Global.getAdminPath()+"/communication/communicationInfo/?repage";
	}

}