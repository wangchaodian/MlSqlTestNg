package com.qa.tests;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.restclient.KeyMethod;
import com.qa.restclient.RestClient;


public class MlTest extends TestBase {

	TestBase testBase;
	String host;
	String excelUrl;
	String Sheet_Ml;
	RestClient restClient;
	CloseableHttpResponse clos;

	@BeforeClass
	public void setUp() throws Exception {
		testBase = new TestBase();
		host = prop.getProperty("WowoHost");   // 线上环境
//		host = prop.getProperty("WowoHost1");   // 测试环境
		Sheet_Ml = prop.getProperty("Sheet_Ml");
		System.out.println("-------------------------------------");
		System.out.println("接口测试开始！！！");
	}
	@Test(groups = "ml_user",invocationCount = 1)
	public void loginTest() throws Exception {

		KeyMethod.MlsqlTestNg(host,Sheet_Ml);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);

	}
	
	//  --- 不填写则是（priority = 0 --- 数字越小越先执行）,dependsOnGroups 执行后面跟的方法后再执行@Test
	@Test(groups = "ml_user1",enabled = false ,dependsOnGroups = {"ml_user"},invocationCount = 1)  // invocationCount = 5 -- 执行次数
	public void getUserTest() throws Exception {                                

		KeyMethod.MlsqlTestNg(host,Sheet_Ml);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);
	}
	
	@AfterClass
	public void loginOut(){
		System.out.println("--------------------------------------------");
		System.out.println("接口测试完成!!!");
	}

}
