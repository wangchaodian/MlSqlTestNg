package com.qa.excel;

import java.util.List;

import com.qa.base.MlTest;
import com.qa.restclient.Common;

public class SaveData2DB {
	@SuppressWarnings({ "rawtypes" })
	public void save() throws Exception {
		ReadExcel xlsMain = new ReadExcel();
		MlTest mlTest = null;
		List<MlTest> list = xlsMain.readxlsx();

		for (int i = 0; i < list.size(); i++) {
			mlTest = list.get(i);
			List l = DbUtil.selectOne(Common.SELECT_ML_SQL + "'%" + mlTest.getCasename() + "%'",mlTest);
			if (!l.contains(1)) {
				DbUtil.insert(Common.INSERT_ML_SQL, mlTest);
			} else {
				System.out.println("错误");
			}
		}
	}
}
