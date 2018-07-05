package com.example.demo.jee.utils;
 
import com.example.demo.manipulation.entity.base.User;
import org.springframework.util.StringUtils;
 
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;
 
 
/**
 * Cookie工具类
 */
public class CookieUtil {
	/**
	 * 默认Cookie过期时间（单位：秒）
	 */
	public static final int MAX_AGE = 60 * 30;

	/**
	 * 用户登陆信息Cookie名字
	 */
	public static final String USER_INFO = "ui";

	/**
	 * 向Cookie中写入用户信息
	 *
	 * @param response
	 * @param user
	 */
	public static void setLoginUser(HttpServletResponse response, User user) {
		if (null == response || null == user) {
			return;
		}
		String userId = user.getId();
		String username = user.getUsername();
		try {
			username = URLEncoder.encode(user.getUsername(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		StringBuilder cookieValue = new StringBuilder();
		//FIXME 用户ID此处写入是用于调试,上线需要去掉
		//cookieValue.append(userId).append("|").append(username).append("|").append(user.getLoginStatus());
		addCookie(response, USER_INFO, cookieValue.toString());

	}

	/*将构造好的信息放入coolie中*/
	public static void addCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(MAX_AGE);
		response.addCookie(cookie);
	}

	/*从cookie取出用户登陆信息并且构造User对象返回*/
	public static User getLoginUser(HttpServletRequest request) {
		if (null == request) {
			return null;
		}

		//从cookie里取出用户信息(三个字段)
		String value = getCookieValue(USER_INFO, request);
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		String[] array = value.split("\\|");

		User user = new User();
		user.setId(array[0]);
		try {
			user.setUsername(URLDecoder.decode(array[1], "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			user.setUsername(array[1]);
		}
		//user.setLoginStatus(Integer.parseInt(array[2]));
		return user;
	}

	/*从cookie中取出用户的登陆信息*/
	public static String getCookieValue(String name, HttpServletRequest request) {
		if (null == request || StringUtils.isEmpty(name)) {
			return null;
		}

		Cookie[] cookies = request.getCookies();
		if (null == cookies || 0 == cookies.length) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (Objects.equals(cookie.getName(), name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 删除Cookie
	 *
	 * @param response HttpServletResponse
	 * @param name     Cookie名
	 * @param path     Cookie Path
	 */
	public static void removeCookie(HttpServletResponse response, String name, String path) {
		if (null == response || StringUtils.isEmpty(name) || StringUtils.isEmpty(path)) {
			return;
		}
		Cookie cookie = new Cookie(name, "");
		cookie.setPath(path);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

}