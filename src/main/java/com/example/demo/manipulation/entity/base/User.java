package com.example.demo.manipulation.entity.base;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * 用户模型
 * 
 * @author StarZou
 * @since 2014年7月5日 下午12:07:20
 **/
@Data
public class User {
    private String id;

    @NotEmpty(message="用户名不能为空！")
    private String username;

    //@Size(min=6,max=10,message = "密码长度必须6到10位")
    private String password;

    private Integer state;

    private Timestamp createTime;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

	@Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", state=" + state + ", createTime=" + createTime + "]";
    }

}