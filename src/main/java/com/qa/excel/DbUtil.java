package com.qa.excel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qa.base.MlTest;
import com.qa.restclient.Common;

public class DbUtil {
	/**
	 * @param sql
	 */
	public static void insert(String sql, MlTest mlTest) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(Common.DRIVER);
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1, mlTest.getCasename());
			ps.setString(2, mlTest.getTestcaseId());
			ps.setString(3, mlTest.getUri());
			ps.setString(4, mlTest.getParameter());
			ps.setString(5, mlTest.getCode());
			ps.setString(6, mlTest.getResult());
			ps.setString(7, mlTest.getRunmode());
			ps.setString(8, mlTest.getMethod());
			boolean flag = ps.execute();
			if (!flag) {
				System.out.println(mlTest.getTestcaseId() + "：插入数据库成功！！！");
			}
		} catch (Exception e) {
			System.err.println(mlTest.getTestcaseId() + "：插入数据库失败");
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List selectOne(String sql, MlTest mlTest) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			Class.forName(Common.DRIVER);
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("casename").equals(mlTest.getCasename())
						|| rs.getString("testcase_id").equals(
								mlTest.getTestcaseId())
						|| rs.getString("uri").equals(mlTest.getUri())) {
					list.add(1);
				} else {
					list.add(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return list;
	}

	public static ResultSet selectAll(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(Common.DRIVER);
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return rs;
	}

}
