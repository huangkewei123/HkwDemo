package com.example.demo.manipulation.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限模型
 * 
 * @author hkw
 * @since 2014年7月17日 下午1:02:55
 **/
@Data
public class Permission  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7807793335796692377L;
    private Long id;

    private String permissionName;

    private String permissionSign;

    private String description;

    @Override
    public String toString() {
        return "Permission [id=" + id + ", permissionName=" + permissionName + ", permissionSign=" + permissionSign + ", description=" + description + "]";
    }

}