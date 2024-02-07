package com.leethanh.admin.user.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.leethanh.common.entity.Users;

import jakarta.servlet.http.HttpServletResponse;

public class AbstractExporter {

	public void setResponseHeader(HttpServletResponse response,String contentType,String extension) throws IOException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestampString = dateFormatter.format(new Date());
		String fileName = "users_" + timestampString + extension;

		response.setContentType(contentType);

		String headerkey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;

		response.setHeader(headerkey, headerValue);
	}
}
