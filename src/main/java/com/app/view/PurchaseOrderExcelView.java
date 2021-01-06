package com.app.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.app.model.PurchaseOrder;
public class PurchaseOrderExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception {
		//Download + file name
				response.addHeader("Content-Disposition", "attachment;filename=PurchaseOrder.xls");
				
				//read data from controller
				@SuppressWarnings("unchecked")
				List<PurchaseOrder> list = (List<PurchaseOrder>) model.get("obs");
				
				//create new Sheet
				Sheet sheet=workbook.createSheet("PURCHASE ORDER");
				
				setHead(sheet);
				setBody(sheet,list);
			}
	//row#0 creation
		private void setHead(Sheet sheet) {
			Row row=sheet.createRow(0);
			row.createCell(0).setCellValue("ID");
			row.createCell(1).setCellValue("ORDER CODE");
			row.createCell(2).setCellValue("REF NUMBER");
			row.createCell(3).setCellValue("QUALITY CHECK");
			row.createCell(4).setCellValue("DEFAULT STATUS");
			row.createCell(5).setCellValue("DESCRIPTION");
		}
		
		private void setBody(Sheet sheet, List<PurchaseOrder> list) {
			int rowNum=1;
			for(PurchaseOrder po:list) {
				Row row=sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(po.getId());
				row.createCell(1).setCellValue(po.getOrderCode());
				row.createCell(2).setCellValue(po.getRefNumber());
				row.createCell(3).setCellValue(po.getQualityCheck());
				row.createCell(4).setCellValue(po.getStatus());
				row.createCell(5).setCellValue(po.getDescription());
			
			}
		}
}
