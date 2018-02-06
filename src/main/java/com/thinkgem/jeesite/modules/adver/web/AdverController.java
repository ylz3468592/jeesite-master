/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.adver.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.routon.gridsystem.SendFileListener;
import com.routon.gridsystem.SubReqServer;
import com.routon.gridsystem.tool.SendPicTool;
import com.routon.gridsystem.util.ErrorCode;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.adver.service.AdverService;
import com.thinkgem.jeesite.modules.adver.util.Constants;
import com.thinkgem.jeesite.modules.path.entity.Property;
import com.thinkgem.jeesite.modules.path.service.PropertyService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.term.entity.Term;
import com.thinkgem.jeesite.modules.term.service.TermService;
import com.thinkgem.jeesite.modules.terminfo.entity.InfoTerm;
import com.thinkgem.jeesite.modules.terminfo.service.InfoTermService;

/**
 * 图文广告Controller
 * @author tzj
 * @version 2018-01-09
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "${adminPath}/adver/adver")
public class AdverController extends BaseController {

	@Autowired
	private PropertyService propertyService;
	@Autowired
	private AdverService adverService;
	@Autowired
	private InfoTermService infoTermService;
	@Autowired
	private TermService termService;
	
	@ModelAttribute
	public Adver get(@RequestParam(required=false) String id) {
		Adver entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = adverService.get(id);
		}
		if (entity == null){
			entity = new Adver();
		}
		return entity;
	}
	
	/**
	 * 已审核的才能下发
	 * @param adver
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adver:adver:edit")
	@RequestMapping(value = "preHand")
	public String preHand(Adver adver, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Adver> page = adverService.findOpenAdver(new Page<Adver>(request, response), adver); 
		model.addAttribute("page", page);
		return "modules/adver/adverHand";
	}
	
	/**
	 * 下发单条广告,状态由1变为2
	 * 0：发布的未审核  1：通过审核  2：已下发  3:删除掉了 4：审核未通过
	 * @param adver
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	boolean flag =true;
	@RequiresPermissions("adver:adver:edit")
	@RequestMapping(value = "doHand")
	public String doHand(String teNo, String adId, Model model,final RedirectAttributes redirectAttributes) {
		final Adver adver=adverService.findAdver(adId, teNo);
		
		
		new SendPicTool(teNo, new File(adver.getAdImage()), Integer.valueOf(adver.getAdLiveTime())*1000,new SendFileListener() {
			
			@Override
			public void onError(ErrorCode arg0) {
				System.out.println("======================--------------------"+arg0);
				flag =false;
				addMessage(redirectAttributes, "图文广告下发失败"+arg0);
			}
			
			@Override
			public int nowPercent(String arg0) {
				System.out.println("======================--------------------"+arg0);
				adver.setAdStatus("2");
				adverService.update(adver);
				addMessage(redirectAttributes, "图文广告下发成功");
				flag =false;
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
		return "redirect:"+Global.getAdminPath()+"/adver/adver/list?repage";
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
			Adver adver=adverService.get(id);
			
			
			adver.setAdStatus("2");
			adverService.update(adver);
		}
		addMessage(redirectAttributes, "图文广告下发成功");
		return "redirect:"+Global.getAdminPath()+"/adver/adver/preHand?repage";
	}
	
	@RequestMapping("show")
	public void show(HttpServletRequest request,HttpServletResponse response){
		
		String name = request.getParameter("name");
		File file=new File(name);
		if(file!=null){
			try {
				FileInputStream stream=new FileInputStream(file);
				OutputStream os=response.getOutputStream();
				byte []b=new byte[1024];
				int len=0;
				while((len=stream.read(b))!=-1){
					os.write(b, 0, len);
				}
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "onServer")
	public String onServer(RedirectAttributes redirectAttributes){
	    int nPort = 5656;
        nPort = Integer.valueOf(nPort);
        
        try {
          new SubReqServer().bind(nPort);
        } catch (Exception e) {
       
          e.printStackTrace();
        }
		addMessage(redirectAttributes, "服务器启动成功");
		return  "redirect:"+Global.getAdminPath()+"/adver/adver/?repage";
	}
	
	@RequestMapping(value = "stopServer")
	public String stopServer(RedirectAttributes redirectAttributes){
	    try {
	    	  SubReqServer.groups.get(0).close();
	          SubReqServer.groups.get(1).close();
	        } catch (Exception e1) {
	          e1.printStackTrace();
	        }
		addMessage(redirectAttributes, "服务器关闭成功");
		return  "redirect:"+Global.getAdminPath()+"/adver/adver/?repage";
	}
	
	
	
	/**
	 * 查看单条广告记录
	 * @param adId
	 * @param teNo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adver:adver:view")
	@RequestMapping(value = "viewOne")
	public String viewOne(String adId,String teNo,Model model) {
		Adver adver=adverService.findAdver(adId, teNo);
		Term term=termService.findTerm(teNo);
		List<Term> termList=termService.findTermList(term);
		model.addAttribute("termList",termList);
		model.addAttribute("adver", adver);
		return "modules/adver/adverOne";
	}
	
	@RequiresPermissions("adver:adver:view")
	@RequestMapping(value = "preUpdate")
	public String preUpdate(String adId,String teNo,Model model) {
		Adver adver=adverService.findAdver(adId, teNo);
		Term term=termService.findTerm(teNo);
		List<Term> termList=termService.findTermList(term);
		InfoTerm infoTerm=infoTermService.findTermInfo(term.getId(), adId);
		model.addAttribute("infoTerm", infoTerm);
		model.addAttribute("termList",termList);
		model.addAttribute("adver", adver);
		return "modules/adver/adverUpdate";
	}
	
	@RequiresPermissions("adver:adver:edit")
	@RequestMapping(value = "update")
	public String update(Adver adver, Model model, RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request) throws Exception {
		MultipartFile file=request.getFile("file");
		if(file.isEmpty()){
			adverService.updateNot(adver);
		} else{
		String name1=UUID.randomUUID().toString().substring(0, 10);
		String name2=file.getOriginalFilename();
		String name=name1+name2;
		Property property=propertyService.getImagePath();
		File photo=new File(property.getKey()+name);
		file.transferTo(photo);
		//设置图片路径
		adver.setAdImage(property.getKey()+name);
		//修改图片广告
		adverService.update(adver);
		}
		InfoTerm infoTerm=new InfoTerm();
		infoTerm.setItInfoId(adver.getId());
		Term term=termService.findTerm(adver.getTerm().getTeNo());
		infoTerm.setItTermId(term.getId());
		String midId=request.getParameter("midId");
		infoTerm.setId(midId);
		//修改广告中间表
		infoTermService.updateInfoTerm(infoTerm);
		addMessage(redirectAttributes, "修改图文广告成功");
		return "redirect:"+Global.getAdminPath()+"/adver/adver/?repage";
	}
	
	@RequiresPermissions("adver:adver:view")
	@RequestMapping(value = {"list", ""})
	public String list(Adver adver, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Adver> page = adverService.findAdver(new Page<Adver>(request, response), adver); 
		model.addAttribute("page", page);
		return "modules/adver/adverList";
	}

	@RequiresPermissions("adver:adver:view")
	@RequestMapping(value = "form")
	public String form(Adver adver, Model model) {
		Term term=new Term();
		term.setUser(UserUtils.getUser());
		List<Term> termList=termService.findTermList(term);
		model.addAttribute("termList",termList);
		model.addAttribute("adver", adver);
		return "modules/adver/adverForm";
	}

	@ResponseBody
	@RequestMapping(value = "asd")
	public List<Term> asd() {
		Term term=new Term();
		term.setUser(UserUtils.getUser());
		List<Term> termList=termService.findTermList(term);
		return termList;
	}
	
	@RequiresPermissions("adver:adver:edit")
	@RequestMapping(value = "save")
	public String save(Adver adver, Model model, RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request) throws Exception {
		if (!beanValidator(model, adver)){
			return form(adver, model);
		}
		MultipartFile file=request.getFile("file");
		//获取文件原始名字
		String name1=UUID.randomUUID().toString().substring(0, 10);
		String name2=file.getOriginalFilename();
		String name=name1+name2;
		Property property=propertyService.getImagePath();
		File photo=new File(property.getKey()+name);
		file.transferTo(photo);
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		//设置图片路径
		adver.setAdImage(property.getKey()+name);
		//设置创建时间
		adver.setAdCreateTime(time);
		//设置创建人id
		adver.setUser(UserUtils.getUser());
		//设置广告状态————0：发布的未审核  1：通过审核  2：已过期的  3:删除掉了 4：审核未通过
		adver.setAdStatus("0");
		//获取页面的选择的终端（多个）
		String termList=request.getParameter("ids");
		String split[]=termList.split(",");
		//转化成id集合
		List<String> noList=Arrays.asList(split);
		for(String no:noList){
			String fileId=UUID.randomUUID().toString().substring(0, 31);
			adver.setId(fileId);
			adverService.mySave(adver);
			Term term=termService.findTermByNo(no);
			InfoTerm infoTerm=new InfoTerm();
			infoTerm.setItInfoId(adver.getId());
			infoTerm.setItTermId(term.getId());
			infoTermService.save(infoTerm);
		}
		addMessage(redirectAttributes, "保存图文广告成功");
		return "redirect:"+Global.getAdminPath()+"/adver/adver/?repage";
	}
	
	@RequiresPermissions("adver:adver:edit")
	@RequestMapping(value = "delete")
	public String delete(String adId, RedirectAttributes redirectAttributes,
		String teNo,HttpServletRequest request) {
		Term term=termService.findTerm(teNo);
		InfoTerm infoTerm=infoTermService.findTermInfo(term.getId(),adId);
		infoTermService.deleteInfoTerm(infoTerm);
		addMessage(redirectAttributes, "删除图文广告成功");
		return "redirect:"+Global.getAdminPath()+"/adver/adver/?repage";
	}

}