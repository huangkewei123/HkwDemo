package com.example.demo.manipulation.mapper.base;

import com.example.demo.jee.base.GenericDao;
import com.example.demo.manipulation.entity.base.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户Dao接口
 * 
 * @author hkw
 * @since 2014年7月5日 上午11:49:57
 **/
public interface UserMapper extends GenericDao<User, String> {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    Integer updateByPrimaryKeySelective(User record);

    Integer updateByPrimaryKey(User record);

    /**
     * 用户登录验证查询
     * 
     * @param record
     * @return
     */
    User authentication(@Param("record") User record);
    

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User selectByUsername(String username);
    


}