/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.importantinfo.web;

import java.io.File;
import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.routon.gridsystem.SendFileListener;
import com.routon.gridsystem.tool.SendImportInformation;
import com.routon.gridsystem.util.ErrorCode;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.adver.util.Constants;
import com.thinkgem.jeesite.modules.importantinfo.entity.ImportantInformation;
import com.thinkgem.jeesite.modules.importantinfo.service.ImportantInformationService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.term.entity.Term;
import com.thinkgem.jeesite.modules.term.service.TermService;
import com.thinkgem.jeesite.modules.terminfo.entity.InfoTerm;
import com.thinkgem.jeesite.modules.terminfo.service.InfoTermService;

/**
 * 停电重要信息Controller
 * @author tzj
 * @version 2018-01-09
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "${adminPath}/importantinfo/importantInformation")
public class ImportantInformationController extends BaseController {

	@Autowired
	private ImportantInformationService importantInformationService;
	@Autowired
	private InfoTermService infoTermService;
	@Autowired
	private TermService termService;
	
	@ModelAttribute
	public ImportantInformation get(@RequestParam(required=false) String id) {
		ImportantInformation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = importantInformationService.get(id);
		}
		if (entity == null){
			entity = new ImportantInformation();
		}
		return entity;
	}
	
	@RequiresPermissions("importantinfo:importantInformation:view")
	@RequestMapping(value = "viewOne")
	public String viewOne(String adId,String teNo,Model model) {
		ImportantInformation information=importantInformationService.findOne(adId, teNo);
		model.addAttribute("information", information);
		return "modules/importantinfo/importantOne";
	}
	
	@RequiresPermissions("importantinfo:importantInformation:view")
	@RequestMapping(value = "preUpdate")
	public String preUpdate(String adId,String teNo,Model model) {
		ImportantInformation information=importantInformationService.findOne(adId, teNo);
		Term term=termService.findTerm(teNo);
		List<Term> termList=termService.findTermList(term);
		InfoTerm infoTerm=infoTermService.findTermInfo(term.getId(), adId);
		model.addAttribute("infoTerm", infoTerm);
		model.addAttribute("termList",termList);
		model.addAttribute("information",information);
		return "modules/importantinfo/importantUpdate";
	}
	
	@RequiresPermissions("importantinfo:importantInformation:edit")
	@RequestMapping(value = "update")
	public String update(ImportantInformation imInformation, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws Exception {
		importantInformationService.update(imInformation);
		InfoTerm infoTerm=new InfoTerm();
		infoTerm.setItInfoId(imInformation.getId());
		Term term=termService.findTerm(imInformation.getTerm().getTeNo());
		infoTerm.setItTermId(term.getId());
		String midId=request.getParameter("midId");
		infoTerm.setId(midId);
		//修改广告中间表
		infoTermService.updateInfoTerm(infoTerm);
		addMessage(redirectAttributes, "修改停电信息成功");
		return "redirect:"+Global.getAdminPath()+"/importantinfo/importantInformation/?repage";
	}
	
	
	
	@RequiresPermissions("importantinfo:importantInformation:view")
	@RequestMapping(value = {"list", ""})
	public String list(ImportantInformation importantInformation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ImportantInformation> page = importantInformationService.findImInformation(new Page<ImportantInformation>(request, response), importantInformation); 
		model.addAttribute("page", page);
		return "modules/importantinfo/importantInformationList";
	}

	@RequiresPermissions("importantinfo:importantInformation:edit")
	@RequestMapping(value ="preHand")
	public String preHand(ImportantInformation importantInformation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ImportantInformation> page = importantInformationService.findOpenImInformation(new Page<ImportantInformation>(request, response), importantInformation); 
		model.addAttribute("page", page);
		return "modules/importantinfo/importantHand";
	}
	
	/**
	 * 下发单条广告,状态由1变为2
	 * 0：发布的未审核  1：通过审核  2：已下发  3:删除掉了 4：审核未通过
	 * @param ImportantInformation
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	boolean flag =true;
	@RequiresPermissions("importantinfo:importantInformation:edit")
	@RequestMapping(value = "doHand")
	public String doHand(String teNo, String adId, Model model,final RedirectAttributes redirectAttributes) {
		final ImportantInformation imInformation=importantInformationService.findOne(adId, teNo);
		
		com.routon.gridsystem.model.ImportantInformation info = new com.routon.gridsystem.model.ImportantInformation();
		info.setId(imInformation.getId());
		info.setImTitle(imInformation.getImTitle());
		info.setImAddress(imInformation.getImAddress());
		info.setImStopTime(new Timestamp(imInformation.getImStopTime().getTime()));
		info.setImStopTimeEnd(new Timestamp(imInformation.getImStopTimeEnd().getTime()));
		info.setImStopType(imInformation.getImStopType());
		info.setImStopArea(imInformation.getImStopArea());
		info.setImStopLine(imInformation.getImStopLine());
		info.setImCreateTime(new Timestamp(imInformation.getImCreateTime().getTime()));
		info.setImCreater(imInformation.getUserChecker().getName());
		info.setImChecker(imInformation.getUser().getId());
		info.setImCheckTime(new Timestamp(imInformation.getImCheckTime().getTime()));
		info.setImStatus("0");
		
		
		new SendImportInformation(teNo, new SendFileListener() {
			@Override
			public void onError(ErrorCode code) {
				System.out.println("======================--------------------"+code);
				flag =false;
				addMessage(redirectAttributes, "停电信息下发失败"+code);
			}

			@Override
			public int nowPercent(String percent) {
				System.out.println("======================--------------------"+percent);
				imInformation.setImStatus("2");
				importantInformationService.update(imInformation);
				addMessage(redirectAttributes, "停电信息下发成功");
				flag =false;
				return 0;
			}
		}, info).sendMsg();

		while(flag){
			try {
				System.out.println("-------------");
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		flag=true;
		return "redirect:"+Global.getAdminPath()+"/importantinfo/importantInformation/list?repage";
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
			ImportantInformation imInformation=importantInformationService.get(id);
			
			
			imInformation.setImStatus("2");
			importantInformationService.update(imInformation);
		}
		addMessage(redirectAttributes, "停电信息广告下发成功");
		return "redirect:"+Global.getAdminPath()+"/importantinfo/importantInformation/preHand?repage";
	}
	
	@RequiresPermissions("importantinfo:importantInformation:view")
	@RequestMapping(value = "form")
	public String form(ImportantInformation importantInformation, Model model) {
		Term term=new Term();
		term.setUser(UserUtils.getUser());
		List<Term> termList=termService.findTermList(term);
		model.addAttribute("termList",termList);
		model.addAttribute("importantInformation", importantInformation);
		return "modules/importantinfo/importantInformationForm";
	}

	@RequiresPermissions("importantinfo:importantInformation:edit")
	@RequestMapping(value = "save")
	public String save(ImportantInformation importantInformation, Model model, 
			RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception {
		if (!beanValidator(model, importantInformation)){
			return form(importantInformation, model);
		}
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		//设置创建时间
		importantInformation.setImCreateTime(time);
		//设置创建人id
		importantInformation.setUser(UserUtils.getUser());
		//设置广告状态————0：发布的未审核  1：发布的审核过了  2：已过期的  3:删除掉了
		importantInformation.setImStatus("0");
		//获取页面的选择的终端（多个）
		String termList[]=request.getParameterValues("termList");
		for(int i=0;i<termList.length;i++){
			String fileId=UUID.randomUUID().toString().substring(0, 31);
			importantInformation.setId(fileId);
			importantInformationService.mySave(importantInformation);
			InfoTerm infoTerm=new InfoTerm();
			infoTerm.setItInfoId(importantInformation.getId());
			infoTerm.setItTermId(termList[i]);
			infoTermService.save(infoTerm);
		}
		addMessage(redirectAttributes, "保存停电重要信息成功");
		return "redirect:"+Global.getAdminPath()+"/importantinfo/importantInformation/?repage";
	}
	
	@RequiresPermissions("importantinfo:importantInformation:edit")
	@RequestMapping(value = "delete")
	public String delete(String infoId, RedirectAttributes redirectAttributes,
			String teNo) {
		Term term=termService.findTerm(teNo);
		InfoTerm infoTerm=infoTermService.findTermInfo(term.getId(),infoId);
		infoTermService.deleteInfoTerm(infoTerm);
		addMessage(redirectAttributes, "删除停电重要信息成功");
		return "redirect:"+Global.getAdminPath()+"/importantinfo/importantInformation/?repage";
	}

}