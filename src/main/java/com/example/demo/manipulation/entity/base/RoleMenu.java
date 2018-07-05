package com.example.demo.manipulation.entity.base;

import java.io.Serializable;

/**
 * @author hkw
 */
public class RoleMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7807793854796692377L;
	/**
	 * 角色菜单表
	 */
	
	private Integer id;//主键Id
	private Integer roleId;//角色Id
	private Integer menuId;//菜单Id
	private String remark;//说明
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
    
}
