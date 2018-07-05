package com.example.demo.manipulation.service.base.impl;

import com.example.demo.jee.base.GenericDao;
import com.example.demo.jee.base.GenericServiceImpl;
import com.example.demo.manipulation.entity.base.User;
import com.example.demo.manipulation.mapper.base.UserMapper;
import com.example.demo.manipulation.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 用户Service实现类
 *
 * @author hkw
 * @since 2014年7月5日 上午11:54:24
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    public Integer updateByPrimaryKeySelective(User user){
    	return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int insert(User model) {
        return userMapper.insertSelective(model);
    }

    @Override
    public int update(User model) {
        return userMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public User authentication(User user) {
    	try {
			userMapper.authentication(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return userMapper.authentication(user);
    }

    @Override
    public GenericDao<User, String> getDao() {
        return userMapper;
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

	@Override
	public int delete(String id) {
		return userMapper.delete(id);
	}

	@Override
	public User selectById(String id) {
		 return userMapper.findById(id);
	}

	@Override
	public User findById(String id) {
		 return userMapper.selectByPrimaryKey(id);
	}
	

}
