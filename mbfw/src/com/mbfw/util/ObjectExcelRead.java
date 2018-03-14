package com.mbfw.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 从EXCEL导入到数据库 创建人：研发中心 创建时间：2014年12月23日
 * 
 * @version
 */
public class ObjectExcelRead {

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号:默认开始传入的时候设置为2，这个根据Excl表格形式来
	 * @param startcol //开始列号:默认为0开始
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi); // 创建一个Excel文件
			HSSFSheet sheet = wb.getSheetAt(sheetnum); // sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; // 取得最后一行的行号
									
			for (int i = startrow; i < rowNum; i++) { // 行循环开始

				PageData varpd = new PageData();//这里用的是自己封装的对象，这里只需要用你需要读取Excl数据封装成的对象即可
				HSSFRow row = sheet.getRow(i); // 获取对应行
				int cellNum = row.getLastCellNum(); // 每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) { // 列循环开始

					HSSFCell cell = row.getCell(Short.parseShort(j + ""));//获取对应列
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellType()) { // 判断excel单元格每列的内容的格式，并对其进行转换，以便插入数据库
							case 0: //获取的类型是数字HSSFCell.CELL_TYPE_NUMERIC
								cellValue = String.valueOf((int) cell.getNumericCellValue());
								break;
							case 1: //获取的类型就是字符串HSSFCell.CELL_TYPE_STRING
								cellValue = cell.getStringCellValue();
								break;
							case 2://获取的类型是时间
								cellValue = cell.getNumericCellValue() + "";
								// cellValue = String.valueOf(cell.getDateCellValue());
								break;
							case 3://获取的是空值,HSSFCell.CELL_TYPE_BLANK
								cellValue = "";
								break;
							case 4://获取的是Boolean,HSSFCell.CELL_TYPE_BOOLEAN
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							case 5://获取的是非法字符,HSSFCell.CELL_TYPE_ERROR
								cellValue = String.valueOf(cell.getErrorCellValue());
								break;
						}
					} else {
						cellValue = "";
					}
					//判断导入的Excl表格是否符合规范格式(只需要判断第二行即可)
					if( i == 1){
						if(j==0 && !"柜台号".equals(cellValue)){
							return null;
						}else if(j==1 && !"姓名".equals(cellValue)){
							return null;
						}else if(j==2 && !"所属部门".equals(cellValue)){
							return null;
						}else if(j==3 && !"上一级部门".equals(cellValue)){
							return null;
						}else if(j==4 && !"工号".equals(cellValue)){
							return null;
						}
					}
					else{ //因为第二行的标题不需要加到数据库中
				    //符合规范则添加到对应的集合中，方面后面进行保存数据到数据库中
					varpd.put("var" + j, cellValue); //在行中，添加对应的列的内容
					}
				}
				if(!varpd.isEmpty()){ //非空的才加入到里面
					varList.add(varpd);//添加每一行的内容
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return varList; //将封装好的数据对象，返回从Excel表中读取的内容
	}
}
