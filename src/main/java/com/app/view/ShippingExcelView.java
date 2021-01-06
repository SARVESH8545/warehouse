package com.app.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.app.model.Shipping;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
//write class that extends AbstractVpdf View class
public class ShippingExcelView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model,
			Document document, 
			PdfWriter writer,
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception {
		//download document
		response.addHeader("Content-Disposition", "attachment;filename=shipping.pdf");


		//provide custom Font Details object
		Font font = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLUE);

		//create element >> paragraph with Font
		Paragraph p = new Paragraph("WELCOME TO SHIPPING",font);
		p.setAlignment(Element.ALIGN_CENTER);

		//add element to document else  it will not display at PDF
		document.add(p);


		//reading data from Controller using Model
		@SuppressWarnings("unchecked")
		List<Shipping> list=(List<Shipping>) model.get("list");

		//Create one table with no.of columns to be display
		PdfPTable table = new PdfPTable(6); //no of columns
		table.setSpacingBefore(4.0f);
		table.setTotalWidth(new float[] {1.0f,1.0f,1.5f,1.0f,1.0f,1.5f});
		//add data to columns using addCell method
		//once 6 cells done then it will chnage row automatically
		table.addCell("ID");
		table.addCell("SHIP CODE");
		table.addCell("SHIP ReFNumber");
		table.addCell("COURIER RefNumber");
		table.addCell("CONTACT DETAILS");
		table.addCell("BILL TO ADDREAA");
		table.addCell("SHIP TO ADDRESS");
		table.addCell("DESCRIPTION");
		//add list data to table
		for(Shipping ship:list) {
			table.addCell(ship.getId().toString());
			table.addCell(ship.getShippingCode().toString());
			table.addCell(ship.getShippingRefNum().toString());
			table.addCell(ship.getCourierRefNum().toString());
			table.addCell(ship.getContactDtls().toString());
			table.addCell(ship.getBillToAddress().toString());
			table.addCell(ship.getShipToAddress().toString());
			table.addCell(ship.getDescription().toString());
		}

		//add table to document
		document.add(table);

	}

}
