/**
 * ѭ���������ݿ�
 */

package com.qa.Sql;

import java.util.ArrayList;
import java.util.List;

public class PptInsert {

	public static DBProxy proxy = new DBProxy(
			"jdbc:mysql://192.168.1.222:3306/ppt_test", "root", "123456");

	public static void main(String[] args) {
		
		for (int i = 2; i < 101; i++) {
			String sql = "INSERT INTO `ct_article_comment` ( `user_id`, `site_id`, `article_id`, `article_user_id`, `content`, `is_enabled`, `is_delete`, `create_time`, `create_by`) values (?,?,?,?,?,?,?,?,?)";
			List<Object> params = new ArrayList<Object>();
			params.add("399");
			params.add("361087");
			params.add("383");
			params.add("399");
			params.add(i);
			params.add("1");
			params.add("0");
			params.add("2020-09-07 15:30:41");
			params.add("399");
			try {
				proxy.nativeSql(sql, params);
				System.out.println(i);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
	}

}
