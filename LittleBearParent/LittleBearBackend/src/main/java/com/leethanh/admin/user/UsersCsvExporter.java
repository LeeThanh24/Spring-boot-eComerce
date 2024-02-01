package com.leethanh.admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.leethanh.common.entity.Users;

import jakarta.servlet.http.HttpServletResponse;

public class UsersCsvExporter extends AbstractExporter {
	
	public void export(List<Users> listUsers,HttpServletResponse response) throws IOException
	{
		super.setResponseHeader(response, "text/csv", ".csv");
		
		ICsvBeanWriter csvBeanWriter= new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader= {"User ID","Email","First Name","Last Name","Roles","Enabled"};
		String[] fieldMapping= {"id","email","firstName","lastName","roles","enabled"};
		csvBeanWriter.writeHeader(csvHeader);
		
		for (Users user : listUsers) {
			csvBeanWriter.write(user, fieldMapping);
		}
		
		csvBeanWriter.close();
		
	}

}
