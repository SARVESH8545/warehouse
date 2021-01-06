package com.app.view;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import com.app.model.Part;
public class PartExcelView extends AbstractXlsView {

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
		List<Part> list=(List<Part>) model.get("obs");

		//create new Sheet
		Sheet sheet=workbook.createSheet("PART");
		setHead(sheet);
		setBody(sheet,list);	
	}

	//row#0 creation
	private void setHead(Sheet sheet) {
		Row row=sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("CODE");
		row.createCell(2).setCellValue("DIMENSIONS");
		row.createCell(3).setCellValue("COST");
		row.createCell(4).setCellValue("CURRENCY");
		row.createCell(5).setCellValue("DESCRIPTION");	
	}
	private void setBody(Sheet sheet, List<Part> list) {
		int rowNum = 1;
		for (Part part : list) 
		{
			Row row=sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(part.getId());
			row.createCell(1).setCellValue(part.getPartCode());
			row.createCell(2).setCellValue(part.getPartWdth());
			row.createCell(3).setCellValue(part.getPartHgh());
			row.createCell(4).setCellValue(part.getPartLen());
			row.createCell(5).setCellValue(part.getBaseCost());
			row.createCell(6).setCellValue(part.getBaseCurr());
			row.createCell(7).setCellValue(part.getDescription());
		}
	}
}
