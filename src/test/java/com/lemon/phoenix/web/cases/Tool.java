package com.lemon.phoenix.web.cases;




import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.lemon.phoenix.web.pojo.UIElement;
import com.lemon.web.phoenix.util.UILibraryUtil;


public class Tool {
	//日志对象
	private static Logger logger = Logger.getLogger(Tool.class);
	//浏览器封装方法
	public static WebDriver driver;
	@BeforeSuite
	@Parameters(value= {"browserType","driverPath"})
	public void init(String browserType,String driverPath) {
		logger.info("配置信息：----浏览器类型：【"+browserType+"】驱动文件路径：【"+driverPath+"】");
		if ("ie".equalsIgnoreCase(browserType)) {//如果是IE
			//设置系统变量,指定IE驱动文件路径
			System.setProperty("webdriver.ie.driver", driverPath);
			//创建DesiredCapabilitys对象，在对象中保存浏览器的设置信息，传入驱动对象中
			DesiredCapabilities capabilities = new DesiredCapabilities();
			//忽略浏览器受保护模式
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			//忽略浏览器缩放
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			//创建驱动对象，打开IE浏览器
			driver = new InternetExplorerDriver(capabilities);
			logger.info("*****************创建IE驱动对象，开始测试*****************");
		}else if ("chrome".equalsIgnoreCase(browserType)) {//如果是chrome
			System.setProperty("webdriver.chrome.driver", driverPath);
			//打开chrome
			driver = new ChromeDriver();
			
			driver.manage().window().maximize();
			String URL = "http://pre.op110.com.cn/sys/login";
			getURL(URL);
			getElement(By.id("username")).sendKeys("caixuan");//登录小强系统
			getElement(By.id("password")).sendKeys("123456");
			getElement(By.id("submit")).click();
			
			logger.info("*****************创建chrome驱动对象，开始测试*****************");	
		}else if ("firefox".equalsIgnoreCase(browserType)) {//如果是firefox
			//设置驱动系统变量，指定驱动文件路径
			System.setProperty("webdriver.gecko.driver", driverPath);
			//打开firefix
			driver = new FirefoxDriver();
			logger.info("*****************创建firefox驱动对象，开始测试*****************");
		}
	}
	
	@AfterSuite
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		logger.info("****************************测试完成****************************");
		driver.quit();
	}
	
	/**通过显示等待获取元素
	 * @param locator
	 * @return
	 */
	public static WebElement getElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		try {
			WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			logger.info("元素定位成功");
			return webElement;
		} catch (Exception e) {
			logger.error("元素定位失败，超时");
		}
		return null;
	}
	
	/**根据页面元素关键字+元素关键字来获取元素
	 * @param pageKeyword 页面关键字
	 * @param elementKeyword 元素关键字
	 * @return
	 */
	public WebElement getElement(String pageKeyword,String elementKeyword) {
		//先根据页面关键字与元素关键字拿到ui库中UIElement对象
		UIElement uiElement = UILibraryUtil.getUIElement(pageKeyword, elementKeyword);
		//通过拿到的UIElement对象，取出by和value属性值判断通过什么方式来定位页面元素
		String by = uiElement.getBy();
		String value = uiElement.getValue();
		By locator = null;
		//日志记录
		logger.info("根据【"+by+"】【"+value+"】定位【"+pageKeyword+"】页面的【"+elementKeyword+"】元素");
		//通过什么选择器来定位元素取决于xml by配的是什么信息
		if ("id".equalsIgnoreCase(by)) {
			locator = By.id(value);
		}else if ("xpath".equalsIgnoreCase(by)) {
			locator = By.xpath(value);
		}else if ("name".equalsIgnoreCase(by)) {
			locator = By.name(value);
		}else if ("className".equalsIgnoreCase(by)) {
			locator = By.className(value);
		}else if ("tagName".equalsIgnoreCase(by)) {
			locator = By.tagName(value);
		}else if ("partialLinkText".equalsIgnoreCase(by)) {
			locator = By.partialLinkText(value);
		}else if ("linkText".equalsIgnoreCase(by)) {
			locator = By.linkText(value);
		}else if ("cssSelector".equalsIgnoreCase(by)) {
			locator = By.cssSelector(value);
		}
//		return driver.findElement(locator);	
		return getElement(locator);
	}
	/**判断当前页面url路径是否包含了想要的信息，用显示等待处理
	 * @param part
	 * @return
	 */
	public boolean urlPresenceContent(String part) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			boolean flag = wait.until(ExpectedConditions.urlContains(part));
			return flag;
		} catch (Exception e) {
			System.out.println("加载超时");
		}
		return false;
	}
	
	/**访问测试页面（封装成一个方法，方便记录日志，节约成本）
	 * @param url
	 */
	public void getURL(String url) {
		logger.info("访问测试页面：【"+url+"】");
		driver.navigate().to(url);
	}
	
	/**往输入框输入数据
	 * @param element 定位的元素
	 * @param value 写入的值
	 */
	public void sendKey(WebElement element,String value) {
		logger.info("写入数据：【"+value+"】");
		element.sendKeys(value);
	}
	
//	/**清空元素value值
//	 * @param element
//	 */
//	public void cleanDate(WebElement element) {
//		element.clear();
//	}
	/**点击
	 * @param element 元素
	 */
	public void click(WebElement element ) {
		logger.info("完成元素点击事件");
		element.click();
	}
	
	/**获取元素文本值
	 * @param element 元素
	 */
	public String getText(WebElement element) {
		String value = element.getText();
		logger.info("获取元素文本值：【"+value+"】");
		return value;
	}
	
}

