package com.example.demo.manipulation.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.jee.base.GenericDao;
import com.example.demo.jee.base.GenericServiceImpl;
import com.example.demo.manipulation.entity.base.Role;
import com.example.demo.manipulation.mapper.base.RoleMapper;
import com.example.demo.manipulation.service.base.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 角色Service实现类
 *
 * @author hkw
 * @since 2014年6月10日 下午4:16:33
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, Integer> implements RoleService {
	
	//private Logger logger = Logger.getLogger(RoleServiceImpl.class.getName());

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public GenericDao<Role, Integer> getDao() {
        return roleMapper;
    }

    @Override
    public List<Role> selectRolesByUserId(String userId) {
        return roleMapper.selectRolesByUserId(userId);
    }


    /**
     * 批量删除
     * @param
     * @return
     */
    public Integer batchDelete(String checkStr){
    	String [] roleIds = checkStr.split(",");
    	try {
			for (int i = 0; i < roleIds.length; i++) {
				System.out.println(Integer.parseInt(roleIds[i]));
				roleMapper.delete(Integer.parseInt(roleIds[i]));
			}
		} catch (Exception e) {
			//logger.error(e);
			System.out.println(e);
			return 0;
		}
    	return 1;
    }
    
    /**
     * 添加用户与角色的关联
     * @param map
     * @return
     */
    public Integer insertUserRole(Map<String, Object> map){
    	try {
			String userIds = (String)map.get("userIds");
			String [] userArr = userIds.split(",");
			for (int i = 0; i < userArr.length; i++) {
				map.put("userId", userArr[i]);
				roleMapper.insertUserRole(map);
			}
		} catch (Exception e) {
			//logger.error(e);
			return 0;
		}
    	return 1;
    }
    
    /**
     * 移除用户与角色的关联
     * @param userIds
     * @param roleId
     * @author hkw
     * @return
     */
    public Integer deleteUserRole(String userIds,Integer roleId){
    	String [] userArr = userIds.split(",");
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
    	try {
			for (int i = 0; i < userArr.length; i++) {
				map.put("userId", userArr[i]);
				roleMapper.deleteUserRole(map);
			}
		} catch (Exception e) {
			//logger.error(e);
			return 0;
		}
    	return 1;
    }

	public PageInfo<Role> selectAllRoles(Integer pageNum,Integer pageSize, Role role){
		PageHelper.startPage(pageNum,pageSize);
		return new PageInfo<Role>(roleMapper.selectAllRoles(role));
	}
}
