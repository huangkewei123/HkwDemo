package com.example.demo.manipulation.mapper.base;

import java.util.List;

import com.example.demo.jee.base.GenericDao;
import com.example.demo.manipulation.entity.base.Permission;

/**
 * 权限 Dao 接口
 * 
 * @author hkw
 * @since 2014年7月5日 上午11:59:03
 **/
public interface PermissionMapper extends GenericDao<Permission, Long> {

    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    /**
     * 通过角色id 查询角色 拥有的权限
     * 
     * @param roleId
     * @return
     */
    List<Permission> selectPermissionsByRoleId(Integer roleId);
}