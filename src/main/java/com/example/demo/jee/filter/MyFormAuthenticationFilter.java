package com.example.demo.jee.filter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.jee.constants.SysConstants;
import com.example.demo.manipulation.entity.base.Menu;
import com.example.demo.manipulation.entity.base.User;
import com.example.demo.manipulation.service.base.MenuService;
import com.example.demo.manipulation.service.base.UserService;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	@Resource
	private UserService userService;
	@Resource
	private MenuService menuService;

	private Logger logger = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);

	/**
	 * 登录成功执行
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
									 Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		logger.info("登录成功===========================================================123");
		Session session = subject.getSession();

		//清理request中的请求信息
		WebUtils.getAndClearSavedRequest(request);
		//获取登录用户名
		String username = String.valueOf(subject.getPrincipals().getPrimaryPrincipal());
		try{
			// 验证成功在Session中保存用户信息
			final User authUserInfo = userService.selectByUsername(username);

			/**
			 * 查询登录用户拥有的菜单
			 * 根据用户的id查询出所有权限菜单
			 */
			List<Menu> allmenuList = new ArrayList<Menu>();
			if("000000".equals(authUserInfo.getId())){
				allmenuList = menuService.allMenu();		//超级管理员获取所有菜单
			}else{
				allmenuList = menuService.userListAllMenu(authUserInfo.getId());
			}

			//切换菜单=====
			List<Menu> menuList = new ArrayList<Menu>();

			//拆分菜单
			for(int i=0;i<allmenuList.size();i++){
				Menu menu = allmenuList.get(i);
				menuList.add(menu);
			}
			JSONArray arr = (JSONArray) JSONArray.toJSON(menuList);
			System.out.println(arr.toString());
			session.setAttribute("menuJson", JSONArray.toJSON(menuList));
			session.setAttribute("menuList", menuList);
			session.setAttribute(SysConstants.CURRENT_USER, authUserInfo);
			WebUtils.issueRedirect(request, response, SysConstants.DEFAULT_PAGE);
			logger.info("登录结束===========================================================123");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			e.getStackTrace();
		}
		return false;

	}
}
