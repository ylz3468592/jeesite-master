/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gridman.web;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.gridman.entity.Gridman;
import com.thinkgem.jeesite.modules.gridman.service.GridmanService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.UserVo;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 一、二级网格员关联表Controller
 * @author tzj
 * @version 2018-01-29
 */
@Controller
@RequestMapping(value = "${adminPath}/gridman/gridman")
public class GridmanController extends BaseController {

	@Autowired
	private GridmanService gridmanService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Gridman get(@RequestParam(required=false) String id) {
		Gridman entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gridmanService.get(id);
		}
		if (entity == null){
			entity = new Gridman();
		}
		return entity;
	}
	
	/**
	 * 获取所有一级网格员
	 * @param gridman
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("gridman:gridman:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = gridmanService.findFirstGrid(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		return "modules/rangeGrid/firstGridList";
	}

	/**
	 * 获取所有二级网格员
	 * @param gridman
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("gridman:gridman:view")
	@RequestMapping(value = "second")
	public String second(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = gridmanService.findSecondGrid(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		return "modules/rangeGrid/secondGridList";
	}
	
	@RequiresPermissions("gridman:gridman:view")
	@RequestMapping(value = "form")
	public String form(Gridman gridman, Model model) {
		model.addAttribute("gridman", gridman);
		return "modules/gridman/gridmanForm";
	}

	/**
	 * 一级网格员预分配
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("gridman:gridman:edit")
	@RequestMapping(value = "preFirstPost")
	public String preFirstPost(String id, Model model) {
		User user=systemService.getUser(id);
		Gridman grid=gridmanService.findSecondById(id);
		if(grid!=null){
			User secondUser=systemService.getUser(grid.getSysU2Id());
			model.addAttribute("secondUser", secondUser);
		}
		String roleName="二级网格管理员";
		String flag="0";
		List<User> secondList=systemService.getAllFirstGrid(roleName, flag);
		model.addAttribute("secondList", secondList);
		model.addAttribute("user",user );
		return "modules/rangeGrid/firstGridForm";
	}
	
	/**
	 * 二级网格员预分配
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("gridman:gridman:edit")
	@RequestMapping(value = "preSecondPost")
	public String preSecondPost(String id, Model model){
		User user=systemService.getUser(id);
		UserVo userVo=new UserVo();
		userVo.setId(user.getId());
		userVo.setName(user.getName());
		//获取二级网格员下面的所有一级网格员
		List<Gridman> gridList=gridmanService.findFirstById(id);
		List<User> userList =new ArrayList<User>();
		for(Gridman grid:gridList){
			User users=systemService.getUser(grid.getSysU1Id());
			userList.add(users);
		}
		userVo.setUserList(userList);
		//获取所有没有上级的一级网格员
		String roleName="一级网格管理员";
		String flag="0";
		List<User> userNotList=systemService.getAllFirstGridNot(roleName, flag);
		//集合成一个集合
		List<User> userAllList =new ArrayList<User>();
		userAllList.addAll(userList);
		userAllList.addAll(userNotList);
		model.addAttribute("userVo", userVo);
		model.addAttribute("userList", userList );
		model.addAttribute("userAllList", userAllList );
		return "modules/rangeGrid/secondGridForm";
	}
	
	/**
	 * 为二级网格员分配一级网格员
	 * @param userVo
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("gridman:gridman:edit")
	@RequestMapping(value = "secondSave")
	public String secondSave(UserVo userVo,HttpServletRequest request,RedirectAttributes redirectAttributes){
		String secondId=userVo.getId();
		//选中的一级网格员列表
		List<User> userList=userVo.getUserList();
		//以前选择的，这次没有选择，就应该删掉没有选择的记录
		List<Gridman> gridList=gridmanService.findFirstById(userVo.getId());
		List<User> userListOld =new ArrayList<User>();
		for(Gridman grid:gridList){
			User users=systemService.getUser(grid.getSysU1Id());
			userListOld.add(users);
		}
		for(User userOld:userListOld){
			boolean flag=false;
			for(User user:userList){
				if(userOld.getId()==user.getId()){
					flag=true;
				}
			}
			if(flag==false){
				String u1Id=userOld.getId();
				String u2Id=secondId;
				gridmanService.myDelete(u1Id, u2Id);
			}
		}
		for(User user:userList){
			String firstId=user.getId();
			int sum=gridmanService.getCount(firstId);
			//不存在记录的保存
			if(sum==0){
				Gridman gridman=new Gridman();
				gridman.setSysU1Id(firstId);
				gridman.setSysU2Id(secondId);
				gridmanService.save(gridman);
			}
		}
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/gridman/gridman/second?repage";
	}
	
	/**
	 * 获取所有二级网格员
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "asd")
	public List<User> asd(){
		String roleName="二级网格管理员";
		String flag="0";
		List<User> secondUser=systemService.getAllFirstGrid(roleName, flag);
		return secondUser;
	}
	
	@RequiresPermissions("gridman:gridman:edit")
	@RequestMapping(value = "firstSave")
	public String firstSave(User user,HttpServletRequest request,RedirectAttributes redirectAttributes){
		String secondId=request.getParameter("secondGrid");
		String firstId=user.getId();
		int sum=gridmanService.getCount(firstId);
		if(sum==0){
			Gridman gridman=new Gridman();
			gridman.setSysU1Id(firstId);
			gridman.setSysU2Id(secondId);
			gridmanService.save(gridman);
		}else{
			//执行更新
			Gridman gridman=gridmanService.getGrid(firstId);
			gridman.setSysU2Id(secondId);
			gridmanService.update(gridman);
		}
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/gridman/gridman/list?repage";
	}
	
	@RequiresPermissions("gridman:gridman:edit")
	@RequestMapping(value = "save")
	public String save(Gridman gridman, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gridman)){
			return form(gridman, model);
		}
		gridmanService.save(gridman);
		addMessage(redirectAttributes, "保存一、二级网格员关联表成功");
		return "redirect:"+Global.getAdminPath()+"/gridman/gridman/?repage";
	}
	
	@RequiresPermissions("gridman:gridman:edit")
	@RequestMapping(value = "delete")
	public String delete(Gridman gridman, RedirectAttributes redirectAttributes) {
		gridmanService.delete(gridman);
		addMessage(redirectAttributes, "删除一、二级网格员关联表成功");
		return "redirect:"+Global.getAdminPath()+"/gridman/gridman/?repage";
	}

}