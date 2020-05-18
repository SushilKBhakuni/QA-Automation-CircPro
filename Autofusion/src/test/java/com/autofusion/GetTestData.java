package com.autofusion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GetTestData {
	int rowIndex = 0, columnIndex = 0;
	XSSFWorkbook wb;
	Sheet ws;
	Row wr;
	String fileName = "";
	String sheetName = "";
	HashMap<String, String> data = null;
	HashMap<String, String> ExecutionData = null;

	// **********************************************************************************************
	public GetTestData(String getFileName, String getSheetName) 
	{
		fileName = getFileName;
		sheetName = getSheetName;
	}
	// **********************************************************************************************

	public HashMap<String, String> getData() throws IOException 
	{
		data = new HashMap<String, String>();
		wb = new XSSFWorkbook(new FileInputStream(new File(fileName)));		
		ws = wb.getSheet(sheetName);

		for (rowIndex = 1; rowIndex <= ws.getLastRowNum(); rowIndex++) {
			try 
			{
				String key = ws.getRow(rowIndex).getCell(0).toString();
				//String value = regEx(ws.getRow(rowIndex).getCell(1).toString());
				String value = ws.getRow(rowIndex).getCell(1).toString();
				//System.out.println("Key=" + key + ",Value=" + value);
				// ws.getRow(rowIndex).getCell(1).getCellType()
				data.put(key, value); // this is where the data is copied into
										// Hash
			} 
			catch (Exception e) 
			{
				data.put(String.valueOf(ws.getRow(rowIndex).getCell(0)), ""); 
				// Put Empty String in case of no data in excel
			}
		}

		return data;
	}
	// **********************************************************************************************

	public String regEx(String testString) 
	{
		String formattedString = testString.replaceAll("(?<=^\\d+)\\.0*$", "");
		return formattedString;
	}


}
