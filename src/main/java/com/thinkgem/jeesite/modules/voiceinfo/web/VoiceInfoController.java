/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voiceinfo.web;

import java.util.List;

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
import com.thinkgem.jeesite.modules.voiceinfo.entity.VoiceInfo;
import com.thinkgem.jeesite.modules.voiceinfo.service.VoiceInfoService;

/**
 * 语音留言信息Controller
 * @author tzj
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/voiceinfo/voiceInfo")
public class VoiceInfoController extends BaseController {
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private VoiceInfoService voiceInfoService;
	
	@ModelAttribute
	public VoiceInfo get(@RequestParam(required=false) String id) {
		VoiceInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = voiceInfoService.get(id);
		}
		if (entity == null){
			entity = new VoiceInfo();
		}
		return entity;
	}
	

@RequiresPermissions("voiceinfo:voiceInfo:view")
  @RequestMapping(value = {"list", ""})
  public String list( Model model) {
    List<VoiceInfo> voList=voiceInfoService.getVoice();
    Property property=propertyService.getFileServerUrl();
    model.addAttribute("property",property );
    model.addAttribute("voList", voList);
    return "modules/voiceinfo/voiceInfoList";
  }
	@RequiresPermissions("voiceinfo:voiceInfo:view")
	@RequestMapping(value = "form")
	public String form(VoiceInfo voiceInfo, Model model) {
		model.addAttribute("voiceInfo", voiceInfo);
		return "modules/voiceinfo/voiceInfoForm";
	}

	@RequiresPermissions("voiceinfo:voiceInfo:edit")
	@RequestMapping(value = "save")
	public String save(VoiceInfo voiceInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, voiceInfo)){
			return form(voiceInfo, model);
		}
		voiceInfoService.save(voiceInfo);
		addMessage(redirectAttributes, "保存语音留言信息成功");
		return "redirect:"+Global.getAdminPath()+"/voiceinfo/voiceInfo/?repage";
	}
	
	@RequiresPermissions("voiceinfo:voiceInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		voiceInfoService.delete(id);
		addMessage(redirectAttributes, "删除语音留言信息成功");
		return "redirect:"+Global.getAdminPath()+"/voiceinfo/voiceInfo/?repage";
	}

}