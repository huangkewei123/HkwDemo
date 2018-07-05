package com.example.demo.manipulation.entity.base;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * 用户模型
 * 
 * @author StarZou
 * @since 2014年7月5日 下午12:07:20
 **/
public class User {
    private String id;

    @NotEmpty(message="用户名不能为空！")
    private String username;

    @Size(min=6,max=10,message = "密码长度必须6到10位")
    private String password;

    private Integer state;

    private Timestamp createTime;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }


    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


    public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", state=" + state + ", createTime=" + createTime + "]";
    }

}