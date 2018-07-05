package com.example.demo.manipulation.service.base.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.demo.jee.base.GenericDao;
import com.example.demo.jee.base.GenericServiceImpl;
import com.example.demo.manipulation.entity.base.Menu;
import com.example.demo.manipulation.mapper.base.MenuMapper;
import com.example.demo.manipulation.service.base.MenuService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MenuServiceImpl extends GenericServiceImpl<Menu, Integer> implements MenuService {
	
	//private Logger logger = Logger.getLogger(MenuServiceImpl.class);
	
	@Autowired
    private MenuMapper menuMapper;
	
	@Override
    public GenericDao<Menu, Integer> getDao() {
        return menuMapper;
    }

	public void deleteMenuById(Integer menuId){
		menuMapper.delete(menuId);
		
	}

	public Menu getMenuById(Integer menuId)  {
		return menuMapper.findById(menuId);
	}

	public List<Menu> listAllParentMenu()  {
		return menuMapper.listAllParentMenu();
		
	}

	public Integer saveMenu(Menu menu)  {
		if(menu.getMenuId()!=null){
			//dao.update("MenuMapper.updateMenu", menu);
			//menuMapper.add(menu);
			return menuMapper.update(menu);
		}else{
			if(0<menu.getPid()){
				Menu menuTemp = menuMapper.findById(menu.getPid());
				menu.setPname(menuTemp.getTitle());
			}
			return menuMapper.add(menu);
		}
	}

	public List<Menu> listSubMenuByParentId(Integer pid)  {
		return menuMapper.listSubMenuByParentId(pid);
	}
		
	public List<Menu> listAllMenu()  {
		List<Menu> rl = this.listAllParentMenu();
		for(Menu menu : rl){
			List<Menu> subList = this.listSubMenuByParentId(menu.getMenuId());
			if(!subList.isEmpty())
				menu.setChildren(subList);
		}
		return rl;
	}
	
	/**
	 * @return 根据用户id返回角色自己对应的主目录
	 * @
	 */
	public List<Menu> userListParentMenu(String sysUserId)  {
		return menuMapper.userListParentMenu(sysUserId);
		
	}
	/**
	 * @return 根据主目录和用户id返回角色自己对应的从目录
	 * @
	 */
	public List<Menu> userListSubMenuByParentId(Map< String, Object> param)  {
		return menuMapper.userListSubMenuByParentId(param);
		
	}
	/**
	 * @return 根据用户id返回角色自己对应所有的目录
	 * @
	 */
	public List<Menu> userListAllMenu(String sysUserId)  {
		Map< String, Object> param = new HashMap< String, Object> ();
		param.put("userId", sysUserId);
		List<Menu> rl = this.userListParentMenu(sysUserId);
		for(int i=0;i<rl.size();i++){
			param.put("pid", rl.get(i).getMenuId());
			List<Menu> subList = this.userListSubMenuByParentId(param);
			if(subList.size()>0){
				rl.get(i).setChildren(subList);
			}else{
				rl.remove(i);
				i--;
			}
		}
		return rl;
	}
	
	public List<Menu> allMenu(){
		List<Menu> rl = this.findAllParentMenu();
		for(int i=0;i<rl.size();i++){
			List<Menu> subList = this.subMenuByParentId(rl.get(i).getMenuId());
			if(subList.size()>0){
				rl.get(i).setChildren(subList);
			}else{
				rl.remove(i);
				i--;
			}
		}
		return rl;
	}
	
	/**
	 * @return 根据主目录和用户id返回角色自己未有的从目录
	 * @
	 */
	public List<Menu> notRoleListSubMenuByParentId(Map<String, Object> map)  {
		return menuMapper.notRoleListSubMenuByParentId(map);
		
	}
	/**
	 * @return 根据角色id返回角色自己未有所有的目录
	 * @
	 */
	public List<Menu> notRoleListAllMenu(Integer roleId)  {
		Map< String, Object> param = new HashMap< String, Object> ();
		param.put("roleId", roleId);
		List<Menu> rl = this.listAllParentMenu();
		for(int i=0;i<rl.size();i++){
			param.put("pid", rl.get(i).getMenuId());
			List<Menu> subList = this.notRoleListSubMenuByParentId(param);
			if(subList.size()>0){
				rl.get(i).setChildren(subList);
			}else{
				rl.remove(i);
				i--;
			}
		}
		return rl;
	}
	
	/**
	 * @return 根据角色id返回角色自己对应的主目录
	 * @
	 */
	public List<Menu> roleListParentMenu(Integer roleId)  {
		return menuMapper.roleListParentMenu(roleId);
		
	}
	/**
	 * @return 根据主目录和角色id返回角色自己对应的从目录
	 * @
	 */
	public List<Menu> roleListSubMenuByParentId(Map<String, Object> param)  {
		return menuMapper.roleListSubMenuByParentId(param);
		
	}
	/**
	 * @return 根据角色id返回角色自己对应所有的目录
	 * @
	 */
	public List<Menu> roleListAllMenu(Integer roleId)  {
		List<Menu> rl = this.roleListParentMenu(roleId);
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("roleId", roleId);
		for(int i=0;i<rl.size();i++){
			param.put("pid", rl.get(i).getMenuId());
			List<Menu> subList = this.roleListSubMenuByParentId(param);
			if(subList.size()>0){
				rl.get(i).setChildren(subList);
			}else{
				rl.remove(i);
				i--;
			}
		}
		return rl;
	}
	
	public List<Menu> listAllSubMenu() {
		return menuMapper.listAllSubMenu();
	}
	
	/**
	 * 编辑
	 */
	public Integer edit(Menu menu)  {
		return menuMapper.updateMenu(menu);
	}
	
	public List<Menu> findAllParentMenu(){
		return menuMapper.findAllParentMenu();
	}
	
	public List<Menu> subMenuByParentId(Integer menuId){
		return menuMapper.subMenuByParentId(menuId);
	}

	public List<Menu> findList(Menu menu){
		return menuMapper.findList(menu);
	}
	
	public Integer batchDelete(String checkStr){
		String [] menuIds = checkStr.split(",");
		try {
			for (int i = 0; i < menuIds.length; i++){
				menuMapper.delete(Integer.parseInt(menuIds[i]));
			}
		} catch (NumberFormatException e) {
			//logger.error(e);
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除角色与菜单的关联
	 * @param
	 * @return
	 */
	public Integer deleteRoleMenu(String menuIds,Integer roleId,String pids){
		String [] menuIdArr = menuIds.split(",");
		Map<String, Object > map = new HashMap<String, Object >();
		map.put("roleId", roleId);
		String [] pidArr = pids.split(",");
		List<Integer> pidList = new ArrayList<Integer>();
		for (int i = 0; i < pidArr.length; i++) {
			pidList.add(Integer.parseInt(pidArr[i]));
		}
		
		try {
			for (int i = 0; i < menuIdArr.length; i++) {
				map.put("menuId", menuIdArr[i]);
				menuMapper.deleteRoleMenu(map);
			}
			Map<String, Object> pidMap = listForMap(pidList);
			for (String key : pidMap.keySet()) {
				map.put("menuId", key);
				if(menuMapper.findMenuIdByRoleId(map).size() == 0){	//查询某个角色某个菜单下的子菜单
					menuMapper.deleteRoleMenu(map);
				}
			}
		} catch (Exception e) {
			//logger.error(e);
			return 0;
		}
		return 1;
	}
	
	/**
	 * 添加角色与菜单的关联
	 * @param
	 * @return
	 */
	public Integer insertRoleMenu(String menuIds,Integer roleId,String pids){
		String [] menuIdArr = menuIds.split(",");
		Map<String, Object > map = new HashMap<String, Object >();
		map.put("roleId", roleId);
		try {
			Map<String, String> roleMenu = getPid(roleId,0,pids);	//根据前台所选菜单的父菜单ID查询某个角色未关联的顶级菜单
			for (String menuId : roleMenu.keySet()) {
				map.put("menuId", menuId);
				menuMapper.insertRoleMenu(map);		//插入父菜单ID
			}
			for (int i = 0; i < menuIdArr.length; i++) {
				map.put("menuId", menuIdArr[i]);
				menuMapper.insertRoleMenu(map);		//插入子菜单ID
			}
		} catch (Exception e) {
			//logger.error(e);
			return 0;
		}
		return 1;
	}
	
	/**
	 * 
	 * @param roleId
	 * @param pid
	 * @param pids
	 */
	private Map<String, String> getPid(Integer roleId,Integer pid,String pids){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("pid", pid);
		List<Integer> list = menuMapper.findMenuIdByRoleId(map);	//查询某个角色某个菜单下的子菜单
		Map<String, Object> objMap = listForMap(list);
		Map<String, String> menuIdMap = new HashMap<String, String>();	//此角色没有被关联的父菜单ID
		String [] pidArr = pids.split(",");
		for (int i = 0; i < pidArr.length; i++) {
			if(!objMap.containsKey(pidArr[i]))		//如果此角色没有关联此菜单ID则加入一个list
				menuIdMap.put(pidArr[i],pidArr[i]);
		}
		return menuIdMap;
	}
	
	private Map<String, Object> listForMap(List<Integer> list){
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).toString(), list.get(i));
		}
		return map;
	}


	public Page<Menu> findPage(int pageNo, int pageSize,Menu menu){
		PageHelper.startPage(pageNo, pageSize);
		return menuMapper.findPage(menu);
	}
}
