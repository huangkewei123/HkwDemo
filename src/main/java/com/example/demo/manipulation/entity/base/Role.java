package com.example.demo.manipulation.entity.base;

import lombok.Data;

/**
 * 角色模型
 * 
 * @author hkw
 * @since 2014年7月17日 下午1:02:25
 **/
@Data
public class Role {
    private Integer id;

    private String roleName;

    private String roleSign;

    private String description;


    @Override
    public String toString() {
        return "Role [id=" + id + ", roleName=" + roleName + ", roleSign=" + roleSign + ", description=" + description + "]";
    }

}