package com.qa.restclient;

import com.qa.excel.SaveData2DB;

public class Client {

	public static void main (String [] args) throws Exception{
		
		SaveData2DB saveData2Db = new SaveData2DB();
		saveData2Db.save();
		System.out.println("插入结束");
	}
}
