package com.qa.tests;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.restclient.KeyMethod;
import com.qa.restclient.RestClient;

public class CountryTset extends TestBase {

	TestBase testBase;
	String host1;
	String excelUrl;
	String  Sheet_Country;
	RestClient restClient;
	CloseableHttpResponse clos;

	@BeforeClass (groups = "country_user")
	public void setUp() throws Exception {
		testBase = new TestBase();
		host1 = prop.getProperty("CountryHost");   // 线上环境
//		host1 = prop.getProperty("CountryHost1");   // 测试环境
		Sheet_Country = prop.getProperty("Sheet_Country"); //country表名
		System.out.println("---------------");
		System.out.println("接口测试开始！！！！");
	}

	@Test(groups = "country_user",invocationCount = 1)
	public void loginTest() throws Exception {

		KeyMethod.MlsqlTestNg(host1,Sheet_Country);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);

	}

	//  --- 不填写则是（priority = 0 --- 数字越小越先执行）,dependsOnGroups 执行后面跟的方法后再执行@Test
	@Test(groups = "country_user1",enabled = false ,dependsOnGroups = {"country_user"},invocationCount = 1)  // invocationCount = 5 -- 执行次数
	public void getUserTest() throws Exception {                                

		KeyMethod.MlsqlTestNg(host1,Sheet_Country);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);
	}
	
	@AfterClass
	public void loginOut(){
		System.out.println("----------------");
		System.out.println("接口测试完成!!!");
	}

}
