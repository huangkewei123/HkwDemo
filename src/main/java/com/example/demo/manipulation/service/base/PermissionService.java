package com.example.demo.manipulation.service.base;

import java.util.List;

import com.example.demo.jee.base.GenericService;
import com.example.demo.manipulation.entity.base.Permission;

/**
 * 权限 业务接口
 * 
 * @author hkw
 * @since 2014年6月10日 下午12:02:39
 **/
public interface PermissionService extends GenericService<Permission, Long> {

    /**
     * 通过角色id 查询角色 拥有的权限
     * 
     * @param roleId
     * @return
     */
    List<Permission> findPermissionsByRoleId(Integer roleId);

}
