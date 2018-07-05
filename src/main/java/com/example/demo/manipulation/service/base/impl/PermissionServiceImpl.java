package com.example.demo.manipulation.service.base.impl;

import java.util.List;

import com.example.demo.jee.base.GenericDao;
import com.example.demo.jee.base.GenericServiceImpl;
import com.example.demo.manipulation.entity.base.Permission;
import com.example.demo.manipulation.mapper.base.PermissionMapper;
import com.example.demo.manipulation.service.base.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 权限Service实现类
 *
 * @author hkw
 * @since 2014年6月10日 下午12:05:03
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public GenericDao<Permission, Long> getDao() {
        return permissionMapper;
    }

    @Override
    public List<Permission> findPermissionsByRoleId(Integer roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }
}
