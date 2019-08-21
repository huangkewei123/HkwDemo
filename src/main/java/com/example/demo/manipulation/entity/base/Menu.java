package com.example.demo.manipulation.entity.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
* 类名称：Menu.java
* 类描述： 
* @author hkw
* @version 1.0
 */
@Data
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -78073356696692377L;
	
	private Integer menuId;
	private String title;
	private String menuCode;
	private String href;
	private Integer pid;
	private String pname;
	private String seq;
	private String icon;
	private String level;
	
	private Menu parentMenu;
	private List<Menu> children;
	
	private boolean hasMenu = false;
	private boolean spread = false;

}

