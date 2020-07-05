package com.lemon.phoenix.web.cases;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.inject.Key;
import com.lemon.web.phoenix.util.ExcelUtil;

public class ChangeOrders extends Tool {
	@Test(dataProvider = "changeOrders")
	public void changeOrders(String parities, String abst, String realPay, String datePay, String accountPay,
			String typePay) throws Exception {
	
		click(getElement("财务-付款管理", "财务"));// 点击财务
		click(getElement("财务-付款管理", "付款管理"));// 点击付款管理
		
		Thread.sleep(2000);
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'main?xwl=frames')]")));
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@scrolling,'auto')]")));
		Thread.sleep(2000);
		
		click(getElement("财务-付款管理", "状态"));// 点击状态
		Thread.sleep(2000);
		click(getElement("财务-付款管理", "第一条记录"));// 勾选第一条记录
		click(getElement("财务-付款管理", "修改"));// 点击修改
		
		getElement("财务-付款管理", "汇率").clear();
		sendKey(getElement("财务-付款管理", "汇率"), parities);
		getElement("财务-付款管理", "汇率").sendKeys(Keys.ENTER);
		
		Thread.sleep(5000);
		sendKey(getElement("财务-付款管理", "摘要说明"), abst);
		Thread.sleep(2000);
		sendKey(getElement("财务-付款管理", "实付金额"), realPay);
		Thread.sleep(2000);
		sendKey(getElement("财务-付款管理", "付款日期"), datePay);
		Thread.sleep(2000);
		sendKey(getElement("财务-付款管理", "付款账号"), accountPay);
		Thread.sleep(2000);
		sendKey(getElement("财务-付款管理", "付款方式"), typePay);
		Thread.sleep(3000);
		click(getElement("财务-付款管理", "确定"));

	}

	@DataProvider
	public Object[][] changeOrders() {
		String[] CellNames = { "汇率", "摘要说明", "实付金额", "付款日期", "付款账号", "付款方式" };
		Object[][] datas = ExcelUtil.read("src/test/resources/fkgl.xlsx", "DL-1", CellNames);
		return datas;
	}
}
