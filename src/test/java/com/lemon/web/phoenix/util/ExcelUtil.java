package com.lemon.web.phoenix.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class ExcelUtil {
	/**
	 * @param filePath 要读取的文件路径
	 * @param sheetName	表单名
	 * @param cellNames	要读取的列名（数组）
	 * @return
	 */
	public static Object[][] read(String filePath,String sheetName,String [] cellNames) {
		InputStream iStream = null;
		ArrayList<ArrayList<String>> groups = null;
		try {
			//准备输入流对象
			iStream = new FileInputStream(new File(filePath));
			//获取WorkBook对象
			Workbook workbook = WorkbookFactory.create(iStream);
			//获取表单对象
			Sheet sheet = workbook.getSheet(sheetName);
			//准备map，保存标题和它所在的列索引之间的映射
			Map<String, Integer> cellNamesAndCellnumMap = new HashMap<String, Integer>();
			//取出标题行，获取所有标题数据及每一个标题列索引
			Row titleRow = sheet.getRow(0);
			//取出表单中列的个数
			int lastCellNum = titleRow.getLastCellNum();
			//循环取出标题行的每一列，即每一个标题
			for (int i = 0; i < lastCellNum; i++) {
				Cell cell = titleRow.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				//获取标题
				String title = cell.getStringCellValue();
				//取出当前标题列所在列位置（列索引）
				int cellnum = cell.getAddress().getColumn();
				cellNamesAndCellnumMap.put(title, cellnum);
			}
			//获取最后一行的索引
			int lastRowNum = sheet.getLastRowNum();
			//保存每一组数据（一行就是一组数据）
			groups = new ArrayList<ArrayList<String>>();
			//从第二行开始解析，读取表单中所有测数据行
			for (int i = 1; i <= lastRowNum; i++) {
				//获取一行
				Row row = sheet.getRow(i);
				//如果是空行
				if (isEmpty(row)) {
					continue;//忽略，跳过
				}
				//处理每一行之前，先准备一个list集合将后面读到的列数据保存在这个list集合中，此list集合就是一组数据
				ArrayList<String> cellValuesPerRow = new ArrayList<String>();
				//取出此行上面对应的列数据
				for (int j = 0; j < cellNames.length; j++) {
					String cellName = cellNames[j];
					//根据列名，从map中获取列索引
					int cellnum = cellNamesAndCellnumMap.get(cellName);
					//根据列索引取出列数据
					Cell cell = row.getCell(cellnum,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					//取出列的值
					String vaule = cell.getStringCellValue();
					//将数据保存到list集合
					cellValuesPerRow.add(vaule);
				}
				//处理完了一行，就将此行的数据添加到组当中
				groups.add(cellValuesPerRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (iStream!=null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//将list集合中的数据保存在二维数据中
		return listToArray(groups);
	}

	/**list集合转换为二维数组
	 * @param groups
	 * @return
	 */
	private static Object[][] listToArray(ArrayList<ArrayList<String>> groups) {
		//获取总共的组数
		int size = groups.size();
		//取出每一组的数据个数
		int size1 = groups.get(0).size();
		//声明二维数组
		Object[][] datas = new Object[size][size1];
		for (int i = 0; i < size; i++) {
			//取出每一组
			ArrayList<String> group = groups.get(i);
			for (int j = 0; j < size1; j++) {
				//取出每组中的每个数据
				String value = group.get(j);
				//将数据保存到二维数组当中
				datas[i][j] = value;
			}
		}
		return datas;
	}

	private static boolean isEmpty(Row row) {
		int lastCellnum = row.getLastCellNum();
		for (int i = 0; i < lastCellnum; i++) {
			Cell cell = row.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellType(CellType.STRING);
			String value = cell.getStringCellValue();
			if (value!=null&&value.trim().length()>0) {
				return false;
			}
		}
		return true;
	}

	
}
