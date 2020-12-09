package com.qa.restclient;

import com.qa.Sql.DBProxy;

public class Common {

	   // connect the database
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_NAME = "tj";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "123456";
	public static final String IP = "192.168.1.222";
	public static final String PORT = "3306";
	public static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DB_NAME;  
	
	public static final String EXCEL_PATH = "E:/1.xlsx";
	
	public static final String INSERT_ML_SQL = "INSERT INTO `country_test`(`casename`, `testcase_id`, `uri`, `parameter`, `code`, `result`, `runmode`,`method`) VALUES (?,?,?,?,?,?,?,?)";

	public static final String SELECT_ML_SQL = "select * from country_test where casename like ";
	
	

	
	


}
