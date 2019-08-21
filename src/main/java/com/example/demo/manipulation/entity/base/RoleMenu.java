package com.example.demo.manipulation.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hkw
 */
@Data
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

	
	
	
    
}
