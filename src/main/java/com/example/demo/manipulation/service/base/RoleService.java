package com.example.demo.manipulation.service.base;

import java.util.List;
import java.util.Map;

import com.example.demo.jee.base.GenericService;
import com.example.demo.manipulation.entity.base.Role;
import com.github.pagehelper.PageInfo;

/**
 * 角色 业务接口
 * 
 * @author hkw
 * @since 2014年6月10日 下午4:15:01
 **/
public interface RoleService extends GenericService<Role, Integer> {
    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
    public List<Role> selectRolesByUserId(String userId);
    

    /**
     * 添加用户与角色的关联
     * @param map
     * @return
     */
    public Integer insertUserRole(Map<String, Object> map);
    
    /**
     * 移除用户与角色的关联
     * @param userIds
     * @param roleId
     * @return
     */
    public Integer deleteUserRole(String userIds, Integer roleId);

    public PageInfo<Role> selectAllRoles(Integer pageNum, Integer pageSize, Role role);
}
