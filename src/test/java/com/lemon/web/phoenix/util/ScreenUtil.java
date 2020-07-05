package com.lemon.web.phoenix.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.lemon.phoenix.web.cases.Tool;


public class ScreenUtil {

	/**截图工具类
	 * @param basedir
	 */
	public static File takeScreenShot(String basedir) {
		//拿到driver对象
		WebDriver driver = Tool.driver;
		//声明一个文件对象
		File screenImg = null;
		if (driver instanceof FirefoxDriver) {
			//driver没有提供getScreenshotAs方法 ，是由子类提供，需要向下转型---多态
			FirefoxDriver firefoxDriver = (FirefoxDriver) driver;
			//拿到截图文件路径
			screenImg = firefoxDriver.getScreenshotAs(OutputType.FILE);
		}else if (driver instanceof ChromeDriver) {
			//driver没有提供getScreenshotAs方法 ，是由子类提供，需要向下转型---多态
			ChromeDriver chromeDriver = (ChromeDriver) driver;
			//拿到截图文件路径
			screenImg = chromeDriver.getScreenshotAs(OutputType.FILE);
		}else if (driver instanceof InternetExplorerDriver) {
			//driver没有提供getScreenshotAs方法 ，是由子类提供，需要向下转型---多态
			InternetExplorerDriver internetExplorerDriver = (InternetExplorerDriver) driver;
			//拿到截图文件路径
			screenImg = internetExplorerDriver.getScreenshotAs(OutputType.FILE);
		}
		//日期
		Date date = new Date();
		//毫秒值
		long time = date.getTime();
		//截图目录、文件拼接
		File destFile = new File(basedir+File.separator+time+".jpg");
		try {
			//截图拷贝到 destFile
			FileUtils.copyFile(screenImg,destFile);
		} catch (IOException e) {
			System.out.println("拷贝出错");
		}
		return destFile;
	}

}
