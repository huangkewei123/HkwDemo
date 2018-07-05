package com.example.demo.manipulation.service.base.impl;

import java.util.Map;

import com.example.demo.jee.base.GenericDao;
import com.example.demo.jee.base.GenericServiceImpl;
import com.example.demo.manipulation.entity.base.RoleMenu;
import com.example.demo.manipulation.mapper.base.RoleMenuMapper;
import com.example.demo.manipulation.service.base.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleMenuServiceImpl extends GenericServiceImpl<RoleMenu, Integer> implements RoleMenuService {

	 @Autowired
    private RoleMenuMapper roleMenuMapper;


    @Override
    public GenericDao<RoleMenu, Integer> getDao() {
        return roleMenuMapper;
    }
	
	/*
	* 找最大ID
	*/
	public Integer findMaxId(){
		return roleMenuMapper.findMaxId();
	}
	
	/*
	* 根据角色Id新增角色菜单
	*/
	public Integer insertRoleMenu(RoleMenu rm){
		int id=findMaxId();
		rm.setId(id+1);
		return roleMenuMapper.insertRoleMenu(rm);
	}
	
	/*
	* 根据角色Id和菜单Id删除角色菜单
	*/
	public void deleteRoleMenuByRIDAndMID(Map<String, Object> param){
		roleMenuMapper.deleteRoleMenuByRIDAndMID(param);
	}
	
	/*
	* 根据角色Id和菜单Id查询角色菜单
	*/
	public RoleMenu selectRoleMenuByRIDAndMID(Map<String, Object> param){
		return roleMenuMapper.selectRoleMenuByRIDAndMID(param);
	}
}
