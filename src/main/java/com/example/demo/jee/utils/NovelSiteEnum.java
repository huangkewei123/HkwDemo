package com.example.demo.jee.utils;

/**
 * 已经被支持的小说网站枚举
 * @author LiuKeFeng
 * @date   2016年10月9日
 */
public enum NovelSiteEnum {
	xinxindongman(1, "zapet.cn");
	private int id;
	private String url;
	private NovelSiteEnum(int id, String url) {
		this.id = id;
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static NovelSiteEnum getEnumById(int id) {
		switch (id) {
		case 1 : return xinxindongman;
		default : throw new RuntimeException("id=" + id + "是不被支持的小说网站");
		}
	}
	public static NovelSiteEnum getEnumByUrl(String url) {
		for (NovelSiteEnum novelSiteEnum : values()) {
			if (url.contains(novelSiteEnum.url)) {
				return novelSiteEnum;
			}
		}
		throw new RuntimeException("url=" + url + "是不被支持的小说网站");
	}
}