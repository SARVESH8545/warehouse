package com.app.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.app.model.Grn;

public class GRNExcelView extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception {
		//download + file name
				response.addHeader("Content-Dispositon", "attachment; filename=Part.xls");

				//read data from controller
				@SuppressWarnings("unchecked")
				List<Grn> list=(List<Grn>) model.get("obs");

				//create new Sheet
				Sheet sheet=workbook.createSheet("GRN");
				setHead(sheet);
				setBody(sheet,list);	
			}
	//row#0 creation
		private void setHead(Sheet sheet) {
			Row row=sheet.createRow(0);
			row.createCell(0).setCellValue("ID");
			row.createCell(1).setCellValue("CODE");
			row.createCell(2).setCellValue("TYPE");
			row.createCell(3).setCellValue("DESCRIPTION");
		}
		private void setBody(Sheet sheet, List<Grn> list) {
			int rowNum = 1;
			for (Grn grn : list) 
			{
				Row row=sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(grn.getId());
				row.createCell(1).setCellValue(grn.getGrnCode());
				row.createCell(2).setCellValue(grn.getGrnType());
				row.createCell(3).setCellValue(grn.getDescription());
			}
		
	}

}
