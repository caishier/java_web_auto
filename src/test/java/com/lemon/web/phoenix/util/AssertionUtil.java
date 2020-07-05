package com.lemon.web.phoenix.util;

import org.apache.log4j.Logger;
import org.testng.Assert;



/**断言工具类
 * @author 17665
 *
 */
public class AssertionUtil {
	private static Logger logger = Logger.getLogger(AssertionUtil.class);
	
	/**断言两者值是否一致
	 * @param actual 实际值
	 * @param expected 期望值
	 */
	public static void asserTestEquals(String actual,String expected) {
		logger.info("断言两者值是否一致："+actual+"实际值"+expected+"期望值");
		Assert.assertEquals(actual, expected);
	}
	
	
	/**断言是否为真
	 * @param actual
	 */
	public static void asserTrue(boolean actual) {
		Assert.assertTrue(actual);
	}
}
	