package com.leethanh.admin.user;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.aspectj.weaver.ast.Instanceof;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.leethanh.common.entity.Roles;
import com.leethanh.common.entity.Users;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserExcelExporter extends AbstractExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public UserExcelExporter() {
		workbook = new XSSFWorkbook();
	}

	public void writeHeaderLine() {
		sheet = workbook.createSheet("Users");
		XSSFRow row=sheet.createRow(0);
		XSSFCellStyle cellStyle= workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);
		
		createCell(row, 0, "User ID", cellStyle);
		createCell(row, 1, "E-mail", cellStyle);
		createCell(row, 2, "First Name", cellStyle);
		createCell(row, 3, "Last Name", cellStyle);
		createCell(row, 4, "Roles", cellStyle);
		createCell(row, 5, "Enabled", cellStyle);
	}

	public void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof String) {
			cell.setCellValue((String) value);
		} 
		cell.setCellStyle(style);
	}

	public void export(List<Users> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx");

		writeHeaderLine();
		writeDataLine(listUsers);
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}
	
	
	private void writeDataLine(List<Users> listUsers) {
		// TODO Auto-generated method stub
		int rowIndex =1 ; 
		XSSFCellStyle cellStyle= workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setFontHeight(12);
		cellStyle.setFont(font);
		for (Users user : listUsers) {
			XSSFRow row=sheet.createRow(rowIndex++);
			createCell(row, 0, user.getId(), cellStyle);
			createCell(row, 1, user.getEmail(), cellStyle);
			createCell(row, 2, user.getFirstName(), cellStyle);
			createCell(row, 3, user.getLastName(), cellStyle);
			createCell(row, 4, join( user.getRoles()), cellStyle);
			createCell(row, 5, user.getEnabled(), cellStyle);
		}
		
	}
	
	private String join(Set<Roles> roles)
	{
		String finalString = "";
		Object[] listRolesArray =  roles.toArray();
		int length = listRolesArray.length;
		for (int i = 0; i <length ; i++) {
			
			finalString+=(((Roles) listRolesArray[i]).getName()+(i!=(length-1) ?", ":""));
		}
		return finalString;
	}
}
