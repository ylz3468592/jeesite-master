/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vedioadver.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.routon.gridsystem.SendFileListener;
import com.routon.gridsystem.tool.SendVideoTool;
import com.routon.gridsystem.util.ErrorCode;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.adver.util.Constants;
import com.thinkgem.jeesite.modules.path.entity.Property;
import com.thinkgem.jeesite.modules.path.service.PropertyService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.term.entity.Term;
import com.thinkgem.jeesite.modules.term.service.TermService;
import com.thinkgem.jeesite.modules.terminfo.entity.InfoTerm;
import com.thinkgem.jeesite.modules.terminfo.service.InfoTermService;
import com.thinkgem.jeesite.modules.vedioadver.entity.AdverVedio;
import com.thinkgem.jeesite.modules.vedioadver.service.AdverVedioService;

/**
 * 视频广告Controller
 * @author tzj
 * @version 2018-01-09
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "${adminPath}/vedioadver/adverVedio")
public class AdverVedioController extends BaseController {
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private AdverVedioService adverVedioService;
	@Autowired
	private InfoTermService infoTermService;
	@Autowired
	private TermService termService;
	
	@ModelAttribute
	public AdverVedio get(@RequestParam(required=false) String id) {
		AdverVedio entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = adverVedioService.get(id);
		}
		if (entity == null){
			entity = new AdverVedio();
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "test")
	public String test(String list) {
		list = list.replaceAll("&quot;","'");
		JSONArray obj = JSONObject.parseObject(list).getJSONArray("list");
		String objStr = obj.toJSONString();
		List<AdverVedio> adverVedioList = JSONObject.parseArray(objStr, AdverVedio.class);
		System.out.println(adverVedioList.get(0).getId());
		System.out.println(adverVedioList.get(0).getTerm().getTeNo());
		return "succ";
	}
	
	@RequiresPermissions("vedioadver:adverVedio:view")
	@RequestMapping(value = "viewOne")
	public String viewOne(String adId,String teNo,Model model) {
		AdverVedio adverVedio=adverVedioService.findVeAdver(adId, teNo);
		Property property=propertyService.getFileServerUrl();
		model.addAttribute("property", property);
		model.addAttribute("adverVedio", adverVedio);
		return "modules/vedioadver/adverVedioOne";
	}
	
	@RequiresPermissions("vedioadver:adverVedio:view")
	@RequestMapping(value = "preUpdate")
	public String preUpdate(String adId,String teNo,Model model) {
		AdverVedio adverVedio=adverVedioService.findVeAdver(adId, teNo);
		Term term=termService.findTerm(teNo);
		List<Term> termList=termService.findTermList(term);
		InfoTerm infoTerm=infoTermService.findTermInfo(term.getId(), adId);
		model.addAttribute("infoTerm", infoTerm);
		model.addAttribute("termList",termList);
		model.addAttribute("adverVedio", adverVedio);
		return "modules/vedioadver/adverVedioUpdate";
	}
	
	@RequiresPermissions("vedioadver:adverVedio:edit")
	@RequestMapping(value = "update")
	public String update(AdverVedio adverVedio, Model model, RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request) throws Exception {
		MultipartFile file=request.getFile("file");
		if(file.isEmpty()){
			adverVedioService.updateNot(adverVedio);
		} else{
		String name1=UUID.randomUUID().toString().substring(0, 10);
		String name2=file.getOriginalFilename();
		String name=name1+name2;
		Property property=propertyService.getVedioPath();
		File photo=new File(property.getKey()+name);
		file.transferTo(photo);
		//设置图片路径
		adverVedio.setVePath(property.getKey()+name);
		//修改图片广告
		adverVedioService.update(adverVedio);
		}
		InfoTerm infoTerm=new InfoTerm();
		infoTerm.setItInfoId(adverVedio.getId());
		Term term=termService.findTerm(adverVedio.getTerm().getTeNo());
		infoTerm.setItTermId(term.getId());
		String midId=request.getParameter("midId");
		infoTerm.setId(midId);
		//修改广告中间表
		infoTermService.updateInfoTerm(infoTerm);
		addMessage(redirectAttributes, "修改图文广告成功");
		return "redirect:"+Global.getAdminPath()+"/vedioadver/adverVedio/?repage";
	}
	
	@RequiresPermissions("vedioadver:adverVedio:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdverVedio adverVedio, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdverVedio> page = adverVedioService.findVedioAdver(new Page<AdverVedio>(request, response), adverVedio); 
		model.addAttribute("page", page);
		return "modules/vedioadver/adverVedioList";
	}

	@RequiresPermissions("vedioadver:adverVedio:edit")
	@RequestMapping(value = "preHand")
	public String preHand(AdverVedio adverVedio, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdverVedio> page = adverVedioService.findOpenVedioAdver(new Page<AdverVedio>(request, response), adverVedio); 
		model.addAttribute("page", page);
		return "modules/vedioadver/vedioHand";
	}
	
	/**
	 * 下发单条广告,状态由1变为2 0：发布的未审核 1：通过审核 2：已下发 3:删除掉了 4：审核未通过
	 * 
	 * @param adverVedio
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	boolean flag =true;
	@RequiresPermissions("vedioadver:adverVedio:edit")
	@RequestMapping(value = "doHand")
	public String doHand(String teNo, String adId, Model model, final RedirectAttributes redirectAttributes) {
		final AdverVedio adverVedio = adverVedioService.findVeAdver(adId, teNo);

		new SendVideoTool(teNo, new File(adverVedio.getVePath()), new SendFileListener() {

			@Override
			public void onError(ErrorCode arg0) {
				System.out.println("======================--------------------" + arg0);
				flag =false;
				addMessage(redirectAttributes, "图文广告下发失败" + arg0);
			}

			@Override
			public int nowPercent(String arg0) {
				System.out.println("======================--------------------" + arg0);
				String[] per = arg0.split("/");
				if (Integer.valueOf(per[0]) > Integer.valueOf(per[1])) {
					adverVedio.setVeStatus("2");
					adverVedioService.update(adverVedio);
					addMessage(redirectAttributes, "视频广告下发成功");
					flag =false;
				}
				return 0;
			}
		}).sendMsg();

		while(flag){
			try {
				System.out.println("-------------");
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		flag=true;
		return "redirect:" + Global.getAdminPath() + "/vedioadver/adverVedio/list?repage";
	}
	
	/**
	 * 多条一起下发
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "doBatchHand")
	public String doBatchHand(String ids,RedirectAttributes redirectAttributes) {
		String split[]=ids.split(",");
		//转化成id集合
		List<String> idList=Arrays.asList(split);
		for(String id:idList){
			AdverVedio adverVedio=adverVedioService.get(id);
			
			
			adverVedio.setVeStatus("2");
			adverVedioService.update(adverVedio);
		}
		addMessage(redirectAttributes, "视频广告下发成功");
		return "redirect:"+Global.getAdminPath()+"/vedioadver/adverVedio/preHand?repage";
	}
	
	@RequiresPermissions("vedioadver:adverVedio:view")
	@RequestMapping(value = "form")
	public String form(AdverVedio adverVedio, Model model) {
		Term term=new Term();
		term.setUser(UserUtils.getUser());
		List<Term> termList=termService.findTermList(term);
		model.addAttribute("termList",termList);
		model.addAttribute("adverVedio", adverVedio);
		return "modules/vedioadver/adverVedioForm";
	}

	@RequiresPermissions("vedioadver:adverVedio:edit")
	@RequestMapping(value = "save")
	public String save(AdverVedio adverVedio, Model model, RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request) throws Exception {
		if (!beanValidator(model, adverVedio)){
			return form(adverVedio, model);
		}
		MultipartFile file=request.getFile("file");
		String name1=UUID.randomUUID().toString().substring(0, 10);
		String name2=file.getOriginalFilename();
		String name=name1+name2;
		Property property=propertyService.getVedioPath();
		File vedio=new File(property.getKey()+name);
		file.transferTo(vedio);
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		//设置文件路径
		adverVedio.setVePath(property.getKey()+name);
		//设置创建时间
		adverVedio.setVeCreateTime(time);
		//设置创建人id
		adverVedio.setUser(UserUtils.getUser());
		//设置广告状态————0：发布的未审核  1：发布的审核过了  2：已过期的  3:删除掉了
		adverVedio.setVeStatus("0");
		//获取页面的选择的终端（多个）
		String termList[]=request.getParameterValues("termList");
		for(int i=0;i<termList.length;i++){
			String fileId=UUID.randomUUID().toString().substring(0, 31);
			adverVedio.setId(fileId);
			adverVedioService.mySave(adverVedio);
			InfoTerm infoTerm=new InfoTerm();
			infoTerm.setItInfoId(adverVedio.getId());
			infoTerm.setItTermId(termList[i]);
			infoTermService.save(infoTerm);
		}
		addMessage(redirectAttributes, "保存视频广告成功");
		return "redirect:"+Global.getAdminPath()+"/vedioadver/adverVedio/?repage";
	}
	
	@RequiresPermissions("vedioadver:adverVedio:edit")
	@RequestMapping(value = "delete")
	public String delete(String veId, RedirectAttributes redirectAttributes,
			String teNo) {
		Term term=termService.findTerm(teNo);
		InfoTerm infoTerm=infoTermService.findTermInfo(term.getId(),veId);
		infoTermService.deleteInfoTerm(infoTerm);
		addMessage(redirectAttributes, "删除视频广告成功");
		return "redirect:"+Global.getAdminPath()+"/vedioadver/adverVedio/?repage";
	}

}