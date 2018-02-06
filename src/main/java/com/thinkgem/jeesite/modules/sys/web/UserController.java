/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.adver.entity.Adver;
import com.thinkgem.jeesite.modules.adver.service.AdverService;
import com.thinkgem.jeesite.modules.gridman.entity.Gridman;
import com.thinkgem.jeesite.modules.gridman.service.GridmanService;
import com.thinkgem.jeesite.modules.importantinfo.entity.ImportantInformation;
import com.thinkgem.jeesite.modules.importantinfo.service.ImportantInformationService;
import com.thinkgem.jeesite.modules.path.entity.Property;
import com.thinkgem.jeesite.modules.path.service.PropertyService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.term.entity.Term;
import com.thinkgem.jeesite.modules.term.service.TermService;
import com.thinkgem.jeesite.modules.vedioadver.entity.AdverVedio;
import com.thinkgem.jeesite.modules.vedioadver.service.AdverVedioService;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private GridmanService gridmanService;
	@Autowired
	private TermService termService;
	@Autowired
	private AdverService adverService;
	@Autowired
	private AdverVedioService adverVedioService;
	@Autowired
	private ImportantInformationService informationService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	/**
	 * 获取二级网格员对应的所有一级网格员
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value ="adverList")
	public String adverList(Model model) {
		//二级网格员id
		String id=UserUtils.getUser().getId();
		List<Gridman> gridList=gridmanService.findFirstById(id);
		List<User> userList =new ArrayList<User>();
		for(Gridman grid:gridList){
			User user=systemService.getUser(grid.getSysU1Id());
			userList.add(user);
		}
//		model.addAttribute("userList", userList );
		List<Adver> adverList=new ArrayList<Adver>();
		for(User user:userList){
		List<Term> termList=termService.findTermBySysId(user.getId());
		List<Adver> adverList1=new ArrayList<Adver>();
		for(Term term:termList){
			Adver adver=new Adver();
			adver.setUser(user);
			adver.setTerm(term);
			//查询其所有图片广告信息（状态为未审核的 0 ）
		List<Adver>	adverList2=adverService.findAllListNot(adver);
		adverList1.addAll(adverList2);
		adverList.addAll(adverList1);
			}
		}
		model.addAttribute("adverList", adverList );
		return "modules/grid/allAdver";
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value ="vedioAdverList")
	public String vedioAdverList(Model model) {
		String id=UserUtils.getUser().getId();
		List<Gridman> gridList=gridmanService.findFirstById(id);
		List<User> userList =new ArrayList<User>();
		for(Gridman grid:gridList){
			User user=systemService.getUser(grid.getSysU1Id());
			userList.add(user);
		}
		List<AdverVedio> veList=new ArrayList<AdverVedio>();
		for(User user:userList){
		List<Term> termList=termService.findTermBySysId(user.getId());
		List<AdverVedio> veList1=new ArrayList<AdverVedio>();
		for(Term term:termList){
			AdverVedio adverVedio=new AdverVedio();
			adverVedio.setTerm(term);
			adverVedio.setUser(user);
			//查询其所有视频广告的信息
			List<AdverVedio> veList2=adverVedioService.findAllListNot(adverVedio);
			veList1.addAll(veList2);
			veList.addAll(veList1);
			}
		}
		model.addAttribute("veList", veList);
		return "modules/grid/allVedio";
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value ="imformationList")
	public String imformationList(Model model) {
		String id=UserUtils.getUser().getId();
		List<Gridman> gridList=gridmanService.findFirstById(id);
		List<User> userList =new ArrayList<User>();
		for(Gridman grid:gridList){
			User user=systemService.getUser(grid.getSysU1Id());
			userList.add(user);
		}
		List<ImportantInformation> inforList=new ArrayList<ImportantInformation>();
		for(User user:userList){
		List<Term> termList=termService.findTermBySysId(user.getId());
		List<ImportantInformation> inforList1=new ArrayList<ImportantInformation>();
		for(Term term:termList){
			ImportantInformation imInformation=new ImportantInformation();
			imInformation.setTerm(term);
			imInformation.setUser(user);
			//查询其所有停电信息
			List<ImportantInformation> inforList2=informationService.findAllListNot(imInformation);
			inforList1.addAll(inforList2);
			inforList.addAll(inforList1);
			}
		}
		model.addAttribute("inforList", inforList);
		return "modules/grid/allInfo";
	}
	
	/**
	 * 根据一级网格员id查询出其对应终端的图文广告
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value ="adverPro")
	public String adverPro(Model model,String id){
		List<Term> termList=termService.findTermBySysId(id);
		List<Adver> adverList=new ArrayList<Adver>();
		for(Term term:termList){
			Adver adver=new Adver();
			adver.setTerm(term);
			//查询其所有图片广告信息（状态为未审核的 0 ）
		List<Adver>	adverList1=adverService.findAllListNot(adver);
		adverList.addAll(adverList1);
		}
		model.addAttribute("adverList", adverList );
		return "modules/grid/allAdver";
	}
	
	/**
	 * 根据一级网格员id查询出其对应终端的视频广告
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value ="vedioPro")
	public String vedioPro(Model model,String id){
		List<Term> termList=termService.findTermBySysId(id);
		List<AdverVedio> veList=new ArrayList<AdverVedio>();
		for(Term term:termList){
			AdverVedio adverVedio=new AdverVedio();
			adverVedio.setTerm(term);
			//查询其所有视频广告的信息
			List<AdverVedio> veList1=adverVedioService.findAllListNot(adverVedio);
			veList.addAll(veList1);
		}
		model.addAttribute("veList", veList );
		return "modules/grid/allVedio";
	}
	
	/**
	 * 根据一级网格员id查询出其对应终端的停电信息
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value ="inforPro")
	public String inforPro(Model model,String id){
		List<Term> termList=termService.findTermBySysId(id);
		List<ImportantInformation> inforList=new ArrayList<ImportantInformation>();
		for(Term term:termList){
			ImportantInformation imInformation=new ImportantInformation();
			imInformation.setTerm(term);
			//查询其所有停电信息
			List<ImportantInformation> inforList1=informationService.findAllListNot(imInformation);
			inforList.addAll(inforList1);
		}
		model.addAttribute("inforList", inforList );
		return "modules/grid/allInfo";
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "viewOne")
	public String viewOne(String adId,String teNo,Model model) {
		Adver adver=adverService.findAdver(adId, teNo);
		model.addAttribute("adver", adver);
		return "modules/adver/adverOne";
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "viewOne2")
	public String viewOne2(String adId,String teNo,Model model) {
		AdverVedio adverVedio=adverVedioService.findVeAdver(adId, teNo);
		model.addAttribute("adverVedio", adverVedio);
		return "modules/vedioadver/adverVedioOne";
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "viewOne3")
	public String viewOne3(String adId,String teNo,Model model) {
		ImportantInformation information=informationService.findOne(adId, teNo);
		model.addAttribute("information", information);
		return "modules/importantinfo/importantOne";
	}
	
	/**
	 * 批准图文广告（通过）
	 * @param adId
	 * @param redirectAttributes
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "adverOpen")
	public String adverOpen(String adId,RedirectAttributes redirectAttributes) throws Exception{
		Adver adver=adverService.get(adId);
		adver.setUserCheck(UserUtils.getUser());
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		adver.setAdCheckTime(time);
		//设置广告状态————0：发布的未审核  1：通过审核  2：已过期的  3:删除掉了 4：审核未通过
		adver.setAdStatus("1");
		adverService.updateNot(adver);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/sys/user/adverList?repage";
	}
	
	/**
	 * 批准图文广告（未通过）
	 * @param adId
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "adverStop")
	public String adverStop(String adId,RedirectAttributes redirectAttributes) throws Exception{
		Adver adver=adverService.get(adId);
		adver.setUserCheck(UserUtils.getUser());
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		adver.setAdCheckTime(time);
		//设置广告状态————0：发布的未审核  1：通过审核  2：已过期的  3:删除掉了 4：审核未通过
		adver.setAdStatus("4");
		adverService.updateNot(adver);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/sys/user/adverList?repage";
	}
	
	/**
	 * 批准视频广告（通过）
	 * @param adId
	 * @param redirectAttributes
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "vedioOpen")
	public String vedioOpen(String adId,RedirectAttributes redirectAttributes) throws Exception{
		AdverVedio adverVedio=adverVedioService.get(adId);
		adverVedio.setUserChecker(UserUtils.getUser());
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		adverVedio.setVeCheckTime(time);
		//设置广告状态————0：发布的未审核  1：通过审核  2：已过期的  3:删除掉了 4：审核未通过
		adverVedio.setVeStatus("1");
		adverVedioService.updateNot(adverVedio);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/sys/user/vedioAdverList?repage";
	}
	
	/**
	 * 批准视频广告（未通过）
	 * @param adId
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "vedioStop")
	public String vedioStop(String adId,RedirectAttributes redirectAttributes) throws Exception{
		AdverVedio adverVedio=adverVedioService.get(adId);
		adverVedio.setUserChecker(UserUtils.getUser());
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		adverVedio.setVeCheckTime(time);
		//设置广告状态————0：发布的未审核  1：通过审核  2：已过期的  3:删除掉了 4：审核未通过
		adverVedio.setVeStatus("4");
		adverVedioService.updateNot(adverVedio);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/sys/user/vedioAdverList?repage";
	}
	
	/**
	 * 批准停电信息（通过）
	 * @param adId
	 * @param redirectAttributes
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "infoOpen")
	public String infoOpen(String adId,RedirectAttributes redirectAttributes) throws Exception{
		ImportantInformation information=informationService.get(adId);
		information.setUserChecker(UserUtils.getUser());
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		information.setImCheckTime(time);
		//设置广告状态————0：发布的未审核  1：通过审核  2：已过期的  3:删除掉了 4：审核未通过
		information.setImStatus("1");
		informationService.update(information);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/sys/user/imformationList?repage";
	}
	
	/**
	 * 批准停电信息（未通过）
	 * @param adId
	 * @param redirectAttributes
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "infoStop")
	public String infoStop(String adId,RedirectAttributes redirectAttributes) throws Exception{
		ImportantInformation information=informationService.get(adId);
		information.setUserChecker(UserUtils.getUser());
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=format.format(date);
		Date time=format.parse(createTime);
		information.setImCheckTime(time);
		//设置广告状态————0：发布的未审核  1：通过审核  2：已过期的  3:删除掉了 4：审核未通过
		information.setImStatus("4");
		informationService.update(information);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/sys/user/imformationList?repage";
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "useCreate")
	public String useCreate(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userCreate";
	}
	
	@RequestMapping(value = "doSave")
	public String doSave(User user,MultipartHttpServletRequest request,Model model,RedirectAttributes redirectAttributes) throws Exception {
		MultipartFile file=request.getFile("file");
		String name=file.getOriginalFilename();
		Property property=propertyService.getImagePath();
		File photo=new File(property.getKey()+name);
		file.transferTo(photo);
		//设置用户照片
		user.setPhoto(property.getKey()+name);
		//设置登录状态(0为不可登录状态，1可以登录)
		user.setLoginFlag("0");
		//设置用户密码
		String password=request.getParameter("newPassword");
		user.setPassword(SystemService.entryptPassword(password));
		//验证登录名
		String loginName=request.getParameter("loginName");
		if (!"true".equals(checkName(loginName))){
			addMessage(model, "保存用户'" + loginName + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		String roleName=request.getParameter("role");
		Role role=systemService.getByRoleName(roleName);
		List<Role> roleList = Lists.newArrayList();
		roleList.add(role);
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, Model model, RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request) throws Exception {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
//		String roleName=request.getParameter("role");
//		Role role=systemService.getByRoleName(roleName);
		List<Role> roleList = Lists.newArrayList();
//		roleList.add(role);
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		MultipartFile file=request.getFile("file");
		if(file.isEmpty()){
			User users=systemService.getUser(user.getId());
			user.setPhoto(users.getPhoto());
			systemService.saveUser(user);
		} else{
			String name1=UUID.randomUUID().toString().substring(0, 15);
			String fileName=file.getOriginalFilename();
			String name=name1+fileName;
			Property property=propertyService.getImagePath();
			File photo=new File(property.getKey()+name);
			file.transferTo(photo);
			user.setPhoto(property.getKey()+name);
			systemService.saveUser(user);
		}
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	
	
	/**
	 * 注册验证登录名是否有效
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "checkName")
	public String checkName(String loginName) {
		if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	/**
	 * 进行注册验证，注册成功则保存用户信息
	 * @param user
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveRegist")
	public String saveRegist(User user) {
		systemService.insertSysUser(user);
		//注册成功，返回登陆界面
		return "redirect:" + adminPath + "/login";
	}
	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
}
