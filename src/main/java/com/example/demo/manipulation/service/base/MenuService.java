package com.example.demo.manipulation.service.base;

import com.example.demo.manipulation.entity.base.Menu;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;



public interface MenuService {
		
	public void deleteMenuById(Integer menuId);

	public Menu getMenuById(Integer id) ;

	//取最大id
	//public Integer findMaxId(PageData pd);
	
	public List<Menu> listAllParentMenu();
	public Integer saveMenu(Menu menu) ;

	public List<Menu> listSubMenuByParentId(Integer pid);
		
	public List<Menu> listAllMenu();
	
	/**
	 * @return 根据用户id返回角色自己对应的主目录
	 * @throws Exception
	 */
	public List<Menu> userListParentMenu(String sysUserId);
	/**
	 * @return 根据主目录和用户id返回角色自己对应的从目录
	 * @throws Exception
	 */
	public List<Menu> userListSubMenuByParentId(Map<String, Object> param);
	/**
	 * @return 根据用户id返回角色自己对应所有的目录
	 * @throws Exception
	 */
	public List<Menu> userListAllMenu(String sysUserId);
	
	/**
	 * @return 根据主目录和用户id返回角色自己未有的从目录
	 * @throws Exception
	 */
	public List<Menu> notRoleListSubMenuByParentId(Map<String, Object> map);
	/**
	 * @return 根据角色id返回角色自己未有所有的目录
	 * @throws Exception
	 */
	public List<Menu> notRoleListAllMenu(Integer roleId) ;
	
	/**
	 * @return 根据角色id返回角色自己对应的主目录
	 * @throws Exception
	 */
	public List<Menu> roleListParentMenu(Integer roleId);
	/**
	 * @return 根据主目录和角色id返回角色自己对应的从目录
	 * @throws Exception
	 */
	public List<Menu> roleListSubMenuByParentId(Map<String, Object> param) ;
	/**
	 * @return 根据角色id返回角色自己对应所有的目录
	 * @throws Exception
	 */
	public List<Menu> roleListAllMenu(Integer roleId) ;
	
	public List<Menu> listAllSubMenu();
	
	public Integer edit(Menu menu) ;
	
	public List<Menu> findAllParentMenu();
	
	public List<Menu> allMenu();
	

	public List<Menu> findList(Menu menu);
	
	public Integer batchDelete(String checkStr);
	

	/**
	 * 删除角色与菜单的关联
	 * @param
	 * @return
	 */
	public Integer deleteRoleMenu(String menuIds, Integer roleId, String pids);
	
	/**
	 * 添加角色与菜单的关联
	 * @param
	 * @return
	 */
	public Integer insertRoleMenu(String menuIds, Integer roleId, String pids);

	public PageInfo<Menu> findPage(Integer pageNum, Integer pageSize, Menu menu);
}
