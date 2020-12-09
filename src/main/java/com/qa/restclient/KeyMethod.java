package com.qa.restclient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.util.BodyHead;
import com.qa.util.TJDecode;
import com.qa.utils.fatjson.FastjsonUtils;
import com.qa.Sql.*;

public class KeyMethod {

	public static boolean bResult;
	public static String sTestCaseID; // 用例id
	public static String sRunMode; // 用例RunMode
	public static int iTestCase, iTotalTestCases;
	public static int statusCode; // 响应状态码
	public static String url, parameter; // 取表中的三个参数值
	public static String authorization; // token
	public static String cl; // 接口响应正文
	public static String dataCode; // 响应结果中json的code值
	public static String interfaceName; // 用例描述
	public static String st; // post请求参数
	public static String result; // Result响应正文期望值
	public static String method; // post还是get方法
	public static long startTime, endTime;
	public static String responseTime;

	static RestClient restClient;
	static CloseableHttpResponse clos;

	public static DBProxy proxy = new DBProxy(
			"jdbc:mysql://192.168.1.222:3306/tj", "tjtest", "123456");

	// 读取Excel表中数据进行接口测试
	public static void MlsqlTestNg(String dns,String table) throws Exception {

		getTestCase(table, iTotalTestCases);

		// 外层for循环，有多少个测试用例就执行多少次循环
		for (int iTestCase = 0; iTestCase < iTotalTestCases; iTestCase++) {
			getTestCase(table, iTestCase);
			// runmode的值控制用例是否被执行
			if (sRunMode.equals("Yes") && method.equals("post")) {

				postActions(dns + url,table);
			} else if (sRunMode.equals("Yes") && method.equals("get")) {
				getActions(dns + url + parameter,table);
			}
		}
	}

	public static void getTestCase(String table, int number) {

		List<Map<String, Object>> listLinkedMap = proxy
				.getListLinkedMap("SELECT * FROM " + table);
		interfaceName = (String) listLinkedMap.get(number).get("casename");
		sTestCaseID = (String) listLinkedMap.get(number).get("testcase_id");
		url = (String) listLinkedMap.get(number).get("uri");
		parameter = (String) listLinkedMap.get(number).get("parameter");
		result = (String) listLinkedMap.get(number).get("result");
		sRunMode = (String) listLinkedMap.get(number).get("runmode");
		iTotalTestCases = listLinkedMap.size();
		method = (String) listLinkedMap.get(number).get("method");
	}

	public static void setTestCase(String table) {
		String sql = "UPDATE "+table+" SET real_code=" + statusCode
				+ ", real_result='" + cl + "',successful='" + bResult
				+ "',responsetime=" + responseTime + " WHERE testcase_id='"
				+ sTestCaseID+"'";
		try {
			proxy.update(sql);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// post请求
	public static void postActions(String InterfaceUrl,String table) throws Exception {

		// 建立一个新连接
		restClient = new RestClient();
		// 准备请求头信息
		String token = authorization;
		HashMap<String, String> headermap = BodyHead.headMap(
				"application/x-www-form-urlencoded", token);

		if (parameter.contains("{")) {

			headermap = BodyHead.headMap("application/json; charset=utf-8",
					token);
		} else {
			headermap = BodyHead.headMap(" application/x-www-form-urlencoded",
					token);
		}

		// 获得body参数
		try {
			st = BodyHead.body3(parameter);
		} catch (Exception e) {
			st = parameter;
		}
		// 接口请求开始时间
		long currentTimeMillis = System.currentTimeMillis();
		startTime = currentTimeMillis;
		// 发送请求
		clos = restClient.post(InterfaceUrl, st, headermap);

		// 把响应内容存储在字符串对象 (加密后的密文)
		String responseString = EntityUtils.toString(clos.getEntity(), "UTF-8");
		// System.out.println("结果是："+responseString);

		// 调用解密方法进行解密
		// String responseString1 = TJDecode.decryptStr(responseString);

		// 接口结束时间
		long currentTimeMillis1 = System.currentTimeMillis();
		endTime = currentTimeMillis1;
		// 接口响应时长
		responseTime = String.valueOf(endTime - startTime);
		// System.out.println(responseString1);
		// 判断响应结果是否包含期望结果
		boolean re = responseString.contains(result);
		// System.out.println(re);
		if (re) {
			bResult = true;
		} else {
			bResult = false;
		}

		// 创建Json对象，把上面字符串序列化成json对象
		JSONObject responseJson = JSON.parseObject(responseString);

		// 如果用例是用户登录则读取结果中token
		if (interfaceName.equals("用户登录")) {
			authorization = getDataJosn(responseJson, "token");
		}
		System.out.println("接口响应时间为：" + responseTime + "--" + sTestCaseID + ":"
				+ bResult + "--响应正文：" + responseJson);
		cl = String.valueOf(responseJson);
		statusCode = clos.getStatusLine().getStatusCode();
		setTestCase(table);

		// Assert.assertEquals(statusCode,
		// TestBase.RESPNSE_STATUS_COOE_200,"响应状态码不是200");

	}

	// 传入json对象，根据参数获得josn数组对象（data）
	public static String getDataJosn(JSONObject responseJson, String js) {
		try {
			authorization = FastjsonUtils.toMap(responseJson.getString("data"))
					.get(js);
			return authorization;
		} catch (Exception e) {
			authorization = null;
			return authorization;
		}

	}

	// 传入json对象，根据参数获得josn数组对象
	public static String getJosn(JSONObject responseJson, String js) {
		try {
			dataCode = responseJson.getString(js);
			return dataCode;
		} catch (Exception e) {
			dataCode = null;
			return dataCode;
		}

	}

	// get请求方法发送
	public static void getActions(String InterfaceUrl,String table) throws Exception {

		// 建立一个新连接
		restClient = new RestClient();
		// 准备请求头信息
		String token = authorization;
		HashMap<String, String> headermap = BodyHead.headMap(
				"application/x-www-form-urlencoded", token);
		// 接口请求开始时间
		long currentTimeMillis = System.currentTimeMillis();
		startTime = currentTimeMillis;
		// 发送请求
		clos = restClient.get(InterfaceUrl, headermap);

		// 把响应内容存储在字符串对象 (加密后的密文)
		String responseString = EntityUtils.toString(clos.getEntity(), "UTF-8");

		// 调用解密方法进行解密
		// String responseString1 = TJDecode.decryptStr(responseString);
		// 接口请求结束时间
		long currentTimeMillis1 = System.currentTimeMillis();
		endTime = currentTimeMillis1;
		// 接口响应时长
		responseTime = String.valueOf(endTime - startTime);
		// 判断响应结果是否包含期望结果
		boolean re = responseString.contains(result);

		if (re) {
			bResult = true;
		} else {
			bResult = false;
		}

		// 创建Json对象，把上面字符串序列化成json对象
		JSONObject responseJson = JSON.parseObject(responseString);

		System.out.println("接口响应时间为：" + responseTime + "--" + sTestCaseID + ":"
				+ bResult + "--响应正文：" + responseJson);

		// 将响应正文填入tj.xlsx中
		cl = String.valueOf(responseJson);
		// 将响应状态码填入tj.xlsx中
		statusCode = clos.getStatusLine().getStatusCode();
		setTestCase(table);
		// String code = String.valueOf(statusCode);

		// Assert.assertEquals(statusCode,TestBase.RESPNSE_STATUS_COOE_200,"响应状态码不是200");

	}
}
