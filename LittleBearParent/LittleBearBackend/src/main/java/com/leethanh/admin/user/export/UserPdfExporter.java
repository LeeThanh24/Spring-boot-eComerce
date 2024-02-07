package com.leethanh.admin.user.export;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.leethanh.common.entity.Roles;
import com.leethanh.common.entity.Users;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class UserPdfExporter  extends AbstractExporter{

	
	public void export(List<Users>listUsers,HttpServletResponse response) throws IOException
	{
		super.setResponseHeader(response, "application/pdf",".pdf");
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		
		//font
		Font font= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLACK);
		
		//Title
		Paragraph paragraph= new Paragraph("List of users",font);
		paragraph.setAlignment(paragraph.ALIGN_CENTER);
		document.add(paragraph);
		
		//Add table 
		PdfPTable table= new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		table.setWidths(new float[] {1.5f,3.5f,2.5f,3.0f,3.0f,1.5f});
		writeTableHeader(table);
		writeTableData(table,listUsers);
		document.add(table);
		
		
		document.close();

		
	}

	private void writeTableData(PdfPTable table, List<Users> listUsers) {
		// TODO Auto-generated method stub
		for(Users user : listUsers)
		{
			
			table.addCell(String.valueOf(user.getId()));
			table.addCell(user.getEmail());
			table.addCell(user.getFirstName());
			table.addCell(user.getLastName());
			table.addCell(join(user.getRoles()));
			table.addCell(String.valueOf(user.getEnabled()));
		}
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell= new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);
		
		Font font= FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("User ID",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("E-mail",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("First Name",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Last Name",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Roles",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Enabled",font));
		table.addCell(cell);
		
		
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
