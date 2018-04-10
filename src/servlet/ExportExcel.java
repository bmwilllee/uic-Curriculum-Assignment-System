package servlet; 
import java.io.FileNotFoundException;  

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import table.Table;  

public class ExportExcel {
	
	public ExportExcel() {};
	
	public static HSSFWorkbook Export(Table table) throws FileNotFoundException {
		HSSFWorkbook wb = new HSSFWorkbook();
		//FileOutputStream fileOut = new FileOutputStream(fileName);
		HSSFSheet sheet1 = wb.createSheet("timeTable");
		String[] titleList = {"Course", "Group", "Student", "Instructor", "TA1", "TA2", "Time1", "Time2"};
		HSSFRow title = sheet1.createRow((short)0);
		for(int i=0; i<8; i++) {
			title.createCell((short)i).setCellValue(titleList[i]); // create title row and set title for each column
		}
		for(int i=0; i<table.courseSize(); i++) {
			sheet1.createRow(i);
			String[] courseInfo = {table.getCourses().get(i).getCourseTitle(), String.valueOf(table.getCourses().get(i).getGroup()),
								  table.getCourses().get(i).getStudent(), table.getCourses().get(i).getInstructor(),
								  table.getCourses().get(i).getTa1(), table.getCourses().get(i).getTa2(),
								  table.getCourses().get(i).getTime1(), table.getCourses().get(i).getTime2()};
			for(int j=0; j<8; j++) {
				sheet1.getRow(i).createCell((short)j).setCellValue(courseInfo[j]);
			}
		}
		return wb;
	}
	
	public static HSSFWorkbook PDExport(Table table) throws FileNotFoundException {
		HSSFWorkbook wb = new HSSFWorkbook();
		//FileOutputStream fileOut = new FileOutputStream(fileName);
		HSSFSheet sheet1 = wb.createSheet("timeTable");
		String[] titleList = {"Course", "Group", "Student", "Instructor"};
		HSSFRow title = sheet1.createRow((short)0);
		for(int i=0; i<4; i++) {
			title.createCell((short)i).setCellValue(titleList[i]); // create title row and set title for each column
		}
		for(int i=0; i<table.courseSize(); i++) {
			sheet1.createRow(i);
			String[] courseInfo = {table.getCourses().get(i).getCourseTitle(), String.valueOf(table.getCourses().get(i).getGroup()),
								  table.getCourses().get(i).getStudent(), table.getCourses().get(i).getInstructor()};
			for(int j=0; j<4; j++) {
				sheet1.getRow(i).createCell((short)j).setCellValue(courseInfo[j]);
			}
		}
		return wb;
	}

}
