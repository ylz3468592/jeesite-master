/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.term.web;

import java.util.Arrays;
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
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.adver.service.AdverService;
import com.thinkgem.jeesite.modules.importantinfo.entity.ImportantInformation;
import com.thinkgem.jeesite.modules.importantinfo.service.ImportantInformationService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.term.entity.Term;
import com.thinkgem.jeesite.modules.term.service.TermService;
import com.thinkgem.jeesite.modules.vedioadver.entity.AdverVedio;
import com.thinkgem.jeesite.modules.vedioadver.service.AdverVedioService;

/**
 * 终端Controller
 * @author 田卓军
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/term/term")
public class TermController extends BaseController {

	@Autowired
	private TermService termService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AdverService adverService;
	@Autowired
	private AdverVedioService adverVedioService;
	@Autowired
	private ImportantInformationService informationService;
	
	@ModelAttribute
	public Term get(@RequestParam(required=false) String id) {
		Term entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = termService.get(id);
		}
		if (entity == null){
			entity = new Term();
		}
		return entity;
	}
	
	@RequiresPermissions("term:term:view")
	@RequestMapping(value = {"list", ""})
	public String list(Term term, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Term> page = termService.findTerm(new Page<Term>(request, response), term); 
		model.addAttribute("page", page);
		return "modules/term/termList";
	}

	@RequiresPermissions("term:term:view")
	@RequestMapping(value = "form")
	public String form(Term term, Model model) {
		//把所有共产党员传过去
		String roleName="共产党员";
		String roleName1="一级网格管理员";
		String flag="0";
		List<User> prcList=systemService.getAllPrc(roleName,flag);
//		System.out.println("=======================");
//		System.out.println(prcList.toString());
		//把所有一级网格员传过去
		List<User> firstList=systemService.getAllFirstGrid(roleName1,flag);
//		System.out.println("=======================");
//		System.out.println(firstList.toString());
		model.addAttribute("firstList", firstList);
		model.addAttribute("prcList", prcList);
		model.addAttribute("term", term);
		return "modules/term/termForm";
	}

	@RequiresPermissions("term:term:edit")
	@RequestMapping(value = "preUpdate")
	public String form(String teNo, Model model) {
		//把所有共产党员传过去
		String roleName="共产党员";
		String roleName1="一级网格管理员";
		String flag="0";
		List<User> prcList=systemService.getAllPrc(roleName,flag);
		List<User> firstList=systemService.getAllFirstGrid(roleName1,flag);
		Term term=termService.findTerm(teNo);
		User prcUser=systemService.getUser(term.getEmpty1());
		model.addAttribute("prcUser",prcUser);
		model.addAttribute("firstList", firstList);
		model.addAttribute("prcList", prcList);
		model.addAttribute("term", term);
		return "modules/term/termUpdate";
	}
	
	
	
	@RequiresPermissions("term:term:edit")
	@RequestMapping(value = "save")
	public String save(Term term, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, term)){
			return form(term, model);
		}
		//1为有效，0无效
		term.setTeStatus("1");
		termService.save(term);
		addMessage(redirectAttributes, "保存终端成功");
		return "redirect:"+Global.getAdminPath()+"/term/term/?repage";
	}
	
	@RequiresPermissions("term:term:edit")
	@RequestMapping(value = "update")
	public String update(Term term, RedirectAttributes redirectAttributes) {
		termService.update(term);
		addMessage(redirectAttributes, "终端修改成功");
		return "redirect:"+Global.getAdminPath()+"/term/term/list?repage";
	}
	
	@RequiresPermissions("term:term:edit")
	@RequestMapping(value = "delete")
	public String delete(String termId, RedirectAttributes redirectAttributes) {
		Term term=termService.get(termId);
		//删除终端时是将终端的状态从1（有效）变为0（无效）
		term.setTeStatus("0");
		termService.updateTerm(term);
		addMessage(redirectAttributes, "删除终端成功");
		return "redirect:"+Global.getAdminPath()+"/term/term/?repage";
	}

	/**
	 * 根据终端编号查询终端上的所有广告信息
	 * @return
	 */
	@RequiresPermissions("term:term:view")
	@RequestMapping(value = "findAllAdver")
	public String findAllAdver(Model model,String teNo){
		Term term=new Term();
		term.setTeNo(teNo);
		Adver adver=new Adver();
		adver.setTerm(term);
		AdverVedio adverVedio=new AdverVedio();
		adverVedio.setTerm(term);
		ImportantInformation imInformation=new ImportantInformation();
		imInformation.setTerm(term);
		//查询其所有图片广告信息
		List<Adver> adverList=adverService.findAllList(adver);
		//查询其所有视频广告的信息
		List<AdverVedio> veList=adverVedioService.findAllList(adverVedio);
		//查询其所有停电信息
		List<ImportantInformation> inforList=informationService.findAllList(imInformation);
		model.addAttribute("teNo", teNo );
		model.addAttribute("adverList", adverList );
		model.addAttribute("veList", veList );
		model.addAttribute("inforList", inforList );
		return "modules/term/allAdverList";
	}
	
	/**
	 * 查看一个终端的信息，包括一级网格员信息，其上级二级网格员的信息，还有终端所属共产党员的信息
	 * @param model
	 * @param teNo
	 * @return
	 */
	@RequiresPermissions("term:term:view")
	@RequestMapping(value = "viewOne")
	public String viewOne(Model model,String teNo) {
		Term term=termService.findTerm(teNo);
		//其所属共产党员信息
		User prcUser=systemService.getUser(term.getEmpty1());
		//一级网格员的信息
		User firstUser=systemService.getUser(term.getUser().getId());
		model.addAttribute("firstUser", firstUser);
		model.addAttribute("prcUser", prcUser);
		model.addAttribute("term", term);
		return "modules/term/termOne";
	}
	
	/**
	 * 批量让所选终端无效
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "batchDelete")
	public String batchDelete(String ids,RedirectAttributes redirectAttributes){
		String split[]=ids.split(",");
		//转化成id集合
		List<String> idList=Arrays.asList(split);
		for(String id:idList){
			Term term=termService.get(id);
			//删除终端时是将终端的状态从1（有效）变为0（无效）
			term.setTeStatus("0");
			termService.updateTerm(term);
		}
		addMessage(redirectAttributes, "删除终端成功");
		return "";
	}
}