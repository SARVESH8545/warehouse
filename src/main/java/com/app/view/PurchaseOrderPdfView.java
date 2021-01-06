package com.app.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.app.model.PurchaseOrder;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PurchaseOrderPdfView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model,
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception {
		//download document
		response.addHeader("Content-Disposition", "attachment;filename=purchaseorder.pdf");


		//provide custom Font Details object
		Font font = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLUE);

		//create element >> paragraph with Font
		Paragraph p = new Paragraph("WELCOME TO PURCHASE ORDER ",font);
		p.setAlignment(Element.ALIGN_CENTER);

		//add element to document else  it will not display at PDF
		document.add(p);


		//reading data from Controller using Model
		@SuppressWarnings("unchecked")
		List<PurchaseOrder> list=(List<PurchaseOrder>) model.get("list");

		//Create one table with no.of columns to be display
		PdfPTable table = new PdfPTable(6); //no of columns
		table.setSpacingBefore(4.0f);
		table.setTotalWidth(new float[] {1.0f,1.0f,1.5f,1.0f,1.0f,1.5f});


		//add data to columns using addCell method
		//once 6 cells done then it will chnage row automatically
		table.addCell("ID");
		table.addCell("ORDER CODE");
		table.addCell("REFERENCE NUMBER");
		table.addCell("QUALITY CHECK");
		table.addCell("DEFAULT STATUS");
		table.addCell("DESCRIPTION");

		//add list data to table
		for(PurchaseOrder po:list) {
			table.addCell(po.getId().toString());
			table.addCell(po.getOrderCode().toString());
			table.addCell(po.getRefNumber().toString());
			table.addCell(po.getQualityCheck().toString());
			table.addCell(po.getStatus().toString());
			table.addCell(po.getDescription().toString());
		}
		//add table to document
		document.add(table);
	}    
}
