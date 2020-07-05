package com.lemon.phoenix.web.listener;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;


import com.lemon.web.phoenix.util.ScreenUtil;

/**通过继承TestListenerAdapter来自定义一个监听器
 * @author 17665
 *
 */
public class RepostListener extends TestListenerAdapter{
	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
	 * 当某一个用例执行失败就进入此方法
	 */
	@Override
	public void onTestFailure(ITestResult tr) {
		//准备截图保存文件夹
		String basedir = "target/surefire-reports/screenshot";
		//tr.getTestContext() 获取测试上下文，相当于一个容器，可以缓存所有测试数据
		//tr.getTestContext().getCurrentXmlTest() 获取当前正在执行的测试集（Testng中的Test）
		//tr.getTestContext().getCurrentXmlTest().getName() 获取当前正在执行的测试集中的name属性值
		String testname = tr.getTestContext().getCurrentXmlTest().getName();
		//basedir+=("/"+testname);
		//Test 目录拼接，需要加一个目录分隔符
		basedir+=(File.separator+testname);
		//拿到日期--用来命名目录
		String dateString = DateFormatUtils.format(new Date(),"yyyy-MM-dd");
		//日期目录拼接
		basedir+=(File.separator+dateString);
		//在basedir目录下截图
		File destFile = ScreenUtil.takeScreenShot(basedir);
		//获取截图的绝对路径
		String absolutePath = destFile.getAbsolutePath();
		/**
		 * 绝对路径E:\HomeSpace\phoenix_web\target\surefire-reports\screenshot\register\2018-01-22\xx.jpg
		 * 想到得到的路径（截图展示在报表）http：//localhost/screenshot\register\2018-01-22\xx.jpg
		 * */
		//截取\HomeSpace\phoenix_web\target\surefire-reports\
		String toBeReplaced = absolutePath.substring(0, absolutePath.indexOf("screenshot"));
		//替换\HomeSpace\phoenix_web\target\surefire-reports\ 成localhost
		String accessPath = absolutePath.replace(toBeReplaced, "http://localhost/");
		//通过Reporter.log写入accessPath到报表（先准备一个标签）
		Reporter.log("<img src='"+accessPath+"' width='100' height='100'><a href='"+accessPath+"' target='_blank'>查看图片</a></img>");
	}
}
