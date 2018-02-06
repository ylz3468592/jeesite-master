package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

/**
 * @author TZJ
 *	二级网格员VO
 */
public class UserVo {
	private String id;
	private String name;
	private List<User> userList; //一级网格员列表
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
}
