package com.example.demo.manipulation.mapper.base;

import java.util.List;
import java.util.Map;


import com.example.demo.jee.base.GenericDao;
import com.example.demo.manipulation.entity.base.Menu;
import com.github.pagehelper.Page;

public interface MenuMapper  extends GenericDao<Menu, Integer> {
	
	public void deleteMenuById(Integer menuId);

	public Menu getMenuById(Integer id) ;

	public List<Menu> listAllParentMenu();
	public void saveMenu(Menu menu) ;

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
	public List<Menu> roleListAllMenu(String roleId) ;
	
	public List<Menu> listAllSubMenu();
	
	public Integer updateMenu(Menu menu);
	
	/**
	 * 查询所有主菜单
	 * @return
	 */
	public List<Menu> findAllParentMenu();
	
	public List<Menu> subMenuByParentId(Integer menuId);
	
	public List<Menu> findList(Menu menu);
	


	/**
	 * 删除角色与菜单的关联
	 * @param map
	 * @return
	 */
	public Integer deleteRoleMenu(Map<String, Object> map);
	
	/**
	 * 添加角色与菜单的关联
	 * @param map
	 * @return
	 */
	public Integer insertRoleMenu(Map<String, Object> map);
	
	/**
	 * 根据角色和菜单查询所有的子ID
	 * @param map
	 * @return
	 */
	public List<Integer> findMenuIdByRoleId(Map<String, Object> map);

	public Page<Menu> findPage(Menu menu);
}
