/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.updatefile.web;

import java.io.File;
import java.text.ParseException;
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

import com.routon.gridsystem.SendFileListener;
import com.routon.gridsystem.tool.SendUpdateTool;
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
import com.thinkgem.jeesite.modules.updatefile.entity.UpdateFileInfo;
import com.thinkgem.jeesite.modules.updatefile.service.UpdateFileInfoService;
import com.thinkgem.jeesite.modules.updatemid.dao.UpdateTermInfoDao;
import com.thinkgem.jeesite.modules.updatemid.entity.UpdateTermInfo;
import com.thinkgem.jeesite.modules.updatemid.service.UpdateTermInfoService;

/**
 * 升级文件记录Controller
 * @author tzj
 * @version 2018-01-09
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "${adminPath}/updatefile/updateFileInfo")
public class UpdateFileInfoController extends BaseController {
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private UpdateFileInfoService updateFileInfoService;
	@Autowired
	private TermService termService;
	@Autowired
	private UpdateTermInfoService updateTermInfoService;
	
	@ModelAttribute
	public UpdateFileInfo get(@RequestParam(required=false) String id) {
		UpdateFileInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = updateFileInfoService.get(id);
		}
		if (entity == null){
			entity = new UpdateFileInfo();
		}
		return entity;
	}
	
	/**
	 * 升级状态为0的（未升级）
	 * @param upFileInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("updatefile:updateFileInfo:edit")
	@RequestMapping(value = "preHand")
	public String preHand(UpdateFileInfo upFileInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UpdateFileInfo> page = updateFileInfoService.findOpenUpdateFile(new Page<UpdateFileInfo>(request, response), upFileInfo); 
		model.addAttribute("page", page);
		return "modules/updatefile/updateHand";
	}
	
	/**
	 * 升级单个终端,状态由0变为1
	 * 0：未升级  1：已升级
	 * @param adver
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	boolean flag = true;
	@RequiresPermissions("updatefile:updateFileInfo:edit")
	@RequestMapping(value = "doHand")
	public String doHand(String teNo, String veId, Model model, final RedirectAttributes redirectAttributes) {
		final UpdateFileInfo updateFile = updateFileInfoService.findUpInfo(veId, teNo);

		new SendUpdateTool(teNo, new File(updateFile.getUiFilePath()), updateFile.getUiFileVersion(),
				new SendFileListener() {

					@Override
					public void onError(ErrorCode arg0) {
						System.out.println("======================--------------------" + arg0);
						flag = false;
						addMessage(redirectAttributes, "升级文件下发失败" + arg0);
					}

					@Override
					public int nowPercent(String arg0) {
						System.out.println("======================--------------------" + arg0);
						String[] per = arg0.split("/");
						if (Integer.valueOf(per[0]) > Integer.valueOf(per[1])) {
							updateFile.setEmpty1("1");
							Date date = new Date();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String createTime = format.format(date);
							Date time = null;
							try {
								time = format.parse(createTime);
								// 保存升级完成的时间
								updateFile.setUiDate(time);
								updateFileInfoService.update(updateFile);
								addMessage(redirectAttributes, "升级文件下发成功");
							} catch (ParseException e) {
								e.printStackTrace();
							}

							flag = false;
						}
						return 0;
					}
				}).sendMsg();

		while (flag) {
			try {
				System.out.println("-------------");
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		flag = true;
		return "redirect:" + Global.getAdminPath() + "/updatefile/updateFileInfo/preHand?repage";
	}
	
	/**
	 * 多终端同时升级
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "doBatchHand")
	public String doBatchHand(String ids,RedirectAttributes redirectAttributes) throws Exception {
		String split[]=ids.split(",");
		//转化成id集合
		List<String> idList=Arrays.asList(split);
		for(String id:idList){
			UpdateFileInfo upFileInfo=updateFileInfoService.get(id);
			
			
			//改变升级状态
			upFileInfo.setEmpty1("1");
			Date date=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createTime=format.format(date);
			Date time=format.parse(createTime);
			//保存升级完成的时间
			upFileInfo.setUiDate(time);
			updateFileInfoService.update(upFileInfo);
		}
		addMessage(redirectAttributes, "升级成功");
		return "redirect:"+Global.getAdminPath()+"/updatefile/updateFileInfo/preHand?repage";
	}
	
	
	@RequiresPermissions("updatefile:updateFileInfo:view")
	@RequestMapping(value = "viewOne")
	public String viewOne(String veId,String teNo,Model model) {
		UpdateFileInfo updateFileInfo=updateFileInfoService.findUpInfo(veId, teNo);
		model.addAttribute("updateFileInfo", updateFileInfo);
		return "modules/updatefile/updateOne";
	}
	
	@RequiresPermissions("updatefile:updateFileInfo:view")
	@RequestMapping(value = "preUpdate")
	public String preUpdate(String veId,String teNo,Model model) {
		UpdateFileInfo updateFileInfo=updateFileInfoService.findUpInfo(veId, teNo);
		Term term=termService.findTerm(teNo);
		List<Term> termList=termService.findSysList();
		UpdateTermInfo updateMid=updateTermInfoService.findUpdateInfo(term.getId(), veId);
		model.addAttribute("updateMid", updateMid);
		model.addAttribute("termList",termList);
		model.addAttribute("updateFileInfo", updateFileInfo);
		return "modules/updatefile/updateUpdate";
	}
	
	@RequiresPermissions("updatefile:updateFileInfo:edit")
	@RequestMapping(value = "update")
	public String update(UpdateFileInfo updateFileInfo, Model model, RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request) throws Exception {
		MultipartFile file=request.getFile("file");
		if(file.isEmpty()){
			updateFileInfoService.updateNot(updateFileInfo);
		} else{
		String name1=UUID.randomUUID().toString().substring(0, 10);
		String name2=file.getOriginalFilename();
		String name=name1+name2;
		Property property=propertyService.getUpdatePath();
		File photo=new File(property.getKey()+name);
		file.transferTo(photo);
		//设置升级文件路径
		updateFileInfo.setUiFilePath(property.getKey()+name);
		//修改升级任务
		updateFileInfoService.update(updateFileInfo);
		}
		UpdateTermInfo updateMid=new UpdateTermInfo();
		updateMid.setUtUiId(updateFileInfo.getId());
		Term term=termService.findTerm(updateFileInfo.getTerm().getTeNo());
		updateMid.setUtTermId(term.getId());
		String midId=request.getParameter("midId");
		updateMid.setId(midId);
		//修改升级中间表
		updateTermInfoService.update(updateMid);
		addMessage(redirectAttributes, "修改升级任务成功");
		return "redirect:"+Global.getAdminPath()+"/updatefile/updateFileInfo/?repage";
	}
	
	@RequiresPermissions("updatefile:updateFileInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UpdateFileInfo updateFileInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UpdateFileInfo> page = updateFileInfoService.findUpdateFile(new Page<UpdateFileInfo>(request, response), updateFileInfo); 
		model.addAttribute("page", page);
		return "modules/updatefile/updateFileInfoList";
	}

	@RequiresPermissions("updatefile:updateFileInfo:view")
	@RequestMapping(value = "form")
	public String form(UpdateFileInfo updateFileInfo, Model model) {
		List<Term> termList=termService.findSysList();
		model.addAttribute("termList",termList);
		model.addAttribute("updateFileInfo", updateFileInfo);
		return "modules/updatefile/updateFileInfoForm";
	}

	@ResponseBody
	@RequestMapping(value = "asd")
	public List<Term> asd() {
		List<Term> termList=termService.findSysList();
		return termList;
	}
	
	@RequiresPermissions("updatefile:updateFileInfo:edit")
	@RequestMapping(value = "save")
	public String save(UpdateFileInfo updateFileInfo, Model model, RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request) throws Exception {
		if (!beanValidator(model, updateFileInfo)){
			return form(updateFileInfo, model);
		}
		MultipartFile file=request.getFile("file");
		//获取文件原始名字
		String name1=UUID.randomUUID().toString().substring(0, 10);
		String name2=file.getOriginalFilename();
		String name=name1+name2;
		Property property=propertyService.getUpdatePath();
		File updatePath=new File(property.getKey()+name);
		file.transferTo(updatePath);
		//设置文件路径
		updateFileInfo.setUiFilePath(property.getKey()+name);
		//获取页面的选择的终端（多个）
		String termList=request.getParameter("ids");
		String split[]=termList.split(",");
		//转化成id集合
		List<String> noList=Arrays.asList(split);
		for(String no:noList){
			String fileId=UUID.randomUUID().toString().substring(0, 31);
			updateFileInfo.setId(fileId);
			updateFileInfoService.mySave(updateFileInfo);
			Term term=termService.findTermByNo(no);
			UpdateTermInfo upmid=new UpdateTermInfo();
			upmid.setUtUiId(updateFileInfo.getId());
			upmid.setUtTermId(term.getId());
			updateTermInfoService.save(upmid);
		}
		addMessage(redirectAttributes, "保存升级任务成功");
		return "redirect:"+Global.getAdminPath()+"/updatefile/updateFileInfo/?repage";
	}
	
	@RequiresPermissions("updatefile:updateFileInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(String veId, RedirectAttributes redirectAttributes,
			String teNo) {
		Term term=termService.findTerm(teNo);
		UpdateTermInfo updateTermInfo=updateTermInfoService.findUpdateInfo(term.getId(), veId);
		updateTermInfoService.deleteUpdateInfo(updateTermInfo);
		addMessage(redirectAttributes, "删除升级文件记录成功");
		return "redirect:"+Global.getAdminPath()+"/updatefile/updateFileInfo/?repage";
	}

}