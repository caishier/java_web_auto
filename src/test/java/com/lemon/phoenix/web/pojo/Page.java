package com.lemon.phoenix.web.pojo;

import java.util.List;

public class Page {
	/**
	 * 关键字
	 */
	private String keyword;
	/**
	 * List集合Page底下所有页面元素对象
	 */
	private List<UIElement> elements;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<UIElement> getElements() {
		return elements;
	}
	public void setElements(List<UIElement> elements) {
		this.elements = elements;
	}
	
	
}
