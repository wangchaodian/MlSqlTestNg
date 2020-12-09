package com.qa.excel;

import java.util.List;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.qa.base.MlTest;
import com.qa.restclient.Common;
import com.qa.restclient.Constants;
public class ReadExcel {
	
	public List<MlTest> readxlsx() throws Exception{
		

		InputStream is = new FileInputStream(Common.EXCEL_PATH);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		MlTest mlTest = null;
		List<MlTest> list = new ArrayList<MlTest>();
		// 循环工作表Sheet
//		System.out.println(xssfWorkbook.getNumberOfSheets());
		 for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			 String sheetName = xssfWorkbook.getSheetName(numSheet);
//			 System.out.println(sheetName);
			 XSSFSheet  xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			 if (xssfSheet == null){
				 continue;
			 }
//			 System.out.println(xssfSheet.getLastRowNum());
			 // 循环行Row
			 for (int rowNum = 2; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				 XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				 if (xssfRow !=null) {
					 mlTest = new MlTest();
					 XSSFCell casename = xssfRow.getCell(Constants.Col_Name);
					 XSSFCell testcaseId = xssfRow.getCell(Constants.Col_TestCaseID);
					 XSSFCell uri = xssfRow.getCell(Constants.Col_url);
					 XSSFCell parameter = xssfRow.getCell(Constants.Col_Parameter);
					 XSSFCell code = xssfRow.getCell(Constants.Col_Code);
					 XSSFCell result = xssfRow.getCell(Constants.Col_TestStepResult);
					 XSSFCell runmode = xssfRow.getCell(Constants.Col_RunMode);
					 mlTest.setCasename(getValue(casename));
					 mlTest.setTestcaseId(getValue(testcaseId));
					 mlTest.setUri(getValue(uri));
					 mlTest.setParameter(getValue(parameter));
					 mlTest.setCode(getValue(code));
					 mlTest.setResult(getValue(result));
					 mlTest.setRunmode(getValue(runmode));
					 mlTest.setMethod(sheetName);
					 list.add(mlTest);
				}	 
			 }
		 }		
		return list;	
	}
	
    @SuppressWarnings({ "static-access", "deprecation" })
     private String getValue(XSSFCell xssfCell) {
             if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
                 // 返回布尔类型的值
                 return String.valueOf(xssfCell.getBooleanCellValue());
              } else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
                 // 返回数值类型的值
                 return String.valueOf(xssfCell.getNumericCellValue());
             } else {
                // 返回字符串类型的值
                 return String.valueOf(xssfCell.getStringCellValue());
             }
         }
}
