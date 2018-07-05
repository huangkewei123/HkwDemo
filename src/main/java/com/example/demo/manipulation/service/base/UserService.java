package com.example.demo.manipulation.service.base;

import com.example.demo.jee.base.GenericService;
import com.example.demo.manipulation.entity.base.User;

/**
 * 用户 业务 接口
 * 
 * @author hkw
 * @since 2014年7月5日 上午11:53:33
 **/
public interface UserService extends GenericService<User, String> {

	public Integer updateByPrimaryKeySelective(User user);
	
    /**
     * 用户验证
     * 
     * @param user
     * @return
     */
    User authentication(User user);

    /**
     * 根据用户名查询用户
     * 
     * @param username
     * @return
     */
    User selectByUsername(String username);
    
    User findById(String id);
    
}
