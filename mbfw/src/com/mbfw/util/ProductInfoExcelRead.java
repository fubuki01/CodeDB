package com.mbfw.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ProductInfoExcelRead {
	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号:默认开始传入的时候设置为2，这个根据Excl表格形式来
	 * @param startcol //开始列号:默认为0开始
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> productInfoReadExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();
		try{
			File target = new File(filepath,filename);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);// 创建一个Excel文件
			HSSFSheet sheet = wb.getSheetAt(sheetnum);// sheet 从0开始
			int rowNum = sheet.getLastRowNum()+1;// 取得最后一行的行号 5
			
			for(int i=startrow;i<rowNum ; i++){
				PageData varpd = new PageData();//这里用的是自己封装的对象，这里只需要用你需要读取Excl数据封装成的对象即可
				HSSFRow row = sheet.getRow(i); // 获取对应行
				int cellNum = row.getLastCellNum();// 每行的最后一个单元格位置 8
				for(int j=startcol;j<cellNum; j++){
					HSSFCell cell = row.getCell(Short.parseShort(j+""));//获取对应列
					String cellValue = null;
					if(null != cell){
						switch(cell.getCellType()){// 判断excel单元格每列的内容的格式，并对其进行转换，以便插入数据库
						case 0:
							cellValue = String.valueOf((int) cell.getNumericCellValue()); break;
						case 1:
							cellValue = cell.getStringCellValue(); break;
						case 2:
							cellValue = cell.getNumericCellValue()+"";
						case 3:
							cellValue = ""; break;
						case 4:
							cellValue = String.valueOf(cell.getBooleanCellValue()); break;
						case 5:
							cellValue = String.valueOf(cell.getErrorCellValue());
						}
					}else{
						cellValue = "" ;
					}
					
					if(i==2){//判断导入的Excl表格是否符合规范格式(只需要判断第二行即可)
						if(j==0 && !"编号".equals(cellValue)){
							return null;
						}else if(j==1 && !"产品名称".equals(cellValue)){
							return null;
						}
						else if(j==2 && !"产品类别".equals(cellValue)){
							return null;
						}
						else if(j==3 && !"供应商".equals(cellValue)){
							return null;
						}
						else if(j==4 && !"价格".equals(cellValue)){
							return null;
						}
						else if(j==5 && !"计量单位".equals(cellValue)){
							return null;
						}
						else if(j==6 && !"其他配置".equals(cellValue)){
							return null;
						}
						else if(j==7 && !"备注".equals(cellValue)){
							return null;
						}
					}else{ //因为第二行的标题不需要加到数据库中
					    //符合规范则添加到对应的集合中，方面后面进行保存数据到数据库中
						varpd.put("var"+j, cellValue);//在行中，添加对应的列的内容
					}
				}
				if(!varpd.isEmpty()){//非空的才加入到里面
					varList.add(varpd);//添加每一行的内容
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return varList;//将封装好的数据对象，返回从Excel表中读取的内容
	}
}
