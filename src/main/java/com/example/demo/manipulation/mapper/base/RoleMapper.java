package com.example.demo.manipulation.mapper.base;

import java.util.List;
import java.util.Map;

import com.example.demo.jee.base.GenericDao;
import com.example.demo.manipulation.entity.base.Role;

/**
 * 角色Dao 接口
 * 
 * @author hkw
 * @since 2014年7月5日 上午11:55:59
 **/
public interface RoleMapper extends GenericDao<Role, Integer> {

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param
     * @return
     */
    List<Role> selectRolesByUserId(String userId);
    

    /**
     * 添加用户与角色的关联
     * @param map
     * @return
     */
    public Integer insertUserRole(Map<String, Object> map);
    
    /**
     * 添加用户与角色的关联
     * @param map
     * @return
     */
    public Integer deleteUserRole(Map<String, Object> map);

    public List<Role> selectAllRoles(Role role);
    
}