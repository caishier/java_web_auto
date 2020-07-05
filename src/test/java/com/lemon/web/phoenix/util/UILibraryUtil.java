package com.lemon.web.phoenix.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import org.dom4j.Document;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lemon.phoenix.web.pojo.Page;
import com.lemon.phoenix.web.pojo.UIElement;

/**
 * 解析UI库，提供页面元素信息
 * 
 * @author 17665
 *
 */
public class UILibraryUtil {
	// 准备一个list集合保存所有Page对象
	public static List<Page> pageList = new ArrayList<Page>();
	// 静态代码块（准备元素数据）
	static {
		parse();
	}

	/**
	 * 从pageList里面取出满足条件的页面元素返回
	 * 
	 * @param pageword
	 * @param element
	 * @return
	 */
	public static UIElement getUIElement(String pageKeyword, String elementKeyword) {
		for (Page page : pageList) {
			if (pageKeyword.equals(page.getKeyword())) {
				List<UIElement> elements = page.getElements();
				for (UIElement uiElement : elements) {
					if (elementKeyword.equals(uiElement.getKeyword())) {
						return uiElement;
					}
				}
			}
		}
		return null;
	}

	private static void parse() {
		// UI库文件路径
		String filePath = "src/test/resources/UILibrary.xml";
		// 创建解析器
		SAXReader saxReader = new SAXReader();

		try {
			// 获取文档对象
			Document document = saxReader.read(new File(filePath));
			// 获取根节点
			Element rootElement = document.getRootElement();
			// 获取根元素底下所有Page元素
			List<Element> pList = rootElement.elements("Page");
			// 循环处理每一个Page元素
			for (Element page : pList) {
				String keyword = page.attributeValue("keyword");
				// 获取当前page底下的所有UIElement
				List<Element> uiElements = page.elements("UIElement");
				List<UIElement> elements = new ArrayList<UIElement>();
				for (Element element : uiElements) {
					String elementKeyword = element.attributeValue("keyword");
					String elementBy = element.attributeValue("by");
					String elementValue = element.attributeValue("value");
					UIElement uiElement = new UIElement();
					uiElement.setKeyword(elementKeyword);
					uiElement.setBy(elementBy);
					uiElement.setValue(elementValue);
					elements.add(uiElement);
				}
				Page pg = new Page();
				pg.setKeyword(keyword);
				pg.setElements(elements);
				// 将解析出来的Page对象统一保存到pageList集合
				pageList.add(pg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
