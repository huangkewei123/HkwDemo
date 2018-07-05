package com.example.demo.manipulation.mapper.base;

import java.util.Map;

import com.example.demo.jee.base.GenericDao;
import com.example.demo.manipulation.entity.base.RoleMenu;

public interface RoleMenuMapper extends GenericDao<RoleMenu, Integer> {
	/*
	* 找最大ID
	*/
	public Integer findMaxId();
	
	/*
	* 根据角色Id新增角色菜单
	*/
	public Integer insertRoleMenu(RoleMenu rm);
	
	/*
	* 根据角色Id和菜单Id删除角色菜单
	*/
	public void deleteRoleMenuByRIDAndMID(Map<String, Object> param);
	
	/*
	* 根据角色Id和菜单Id查询角色菜单
	*/
	public RoleMenu selectRoleMenuByRIDAndMID(Map<String, Object> param);
}
