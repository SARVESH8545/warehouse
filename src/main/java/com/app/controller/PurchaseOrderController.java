package com.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.PurchaseDetail;
import com.app.model.PurchaseOrder;
import com.app.service.IPartService;
import com.app.service.IPurchaseOrderService;
import com.app.service.IShipmentTypeService;
import com.app.service.IWhUserTypeService;
import com.app.util.PurchaseOrderUtil;
import com.app.view.PurchaseOrderExcelView;
import com.app.view.PurchaseOrderPdfView;
import com.app.view.VendorInvoicePdf;
@Controller // =Object + HTTP Request
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

	private Logger log= LoggerFactory.getLogger(PurchaseOrderController.class);

	@Autowired
	private IPurchaseOrderService service;

	@Autowired
	private ServletContext context;

	@Autowired
	private PurchaseOrderUtil util;

	@Autowired
	private IShipmentTypeService shipmentTypeservice;

	@Autowired
	private IWhUserTypeService whUserTypeService;

	@Autowired
	private IPartService partService;
	//call this method inside other controller methods
	// where return page is Register or Edit.
	
	private void addDorpDownUi(Model model) {
		model.addAttribute("shipmentTypes",
				shipmentTypeservice.getShipmentIdAndCode());
		model.addAttribute("whUserTypes",
				whUserTypeService.getWhUserTypeIdAndCode("Vendor"));
	}


	//1. Show Register Page
	/**
	 *  URL :/register, Type:GET 
	 *  Goto Page PurchaseOrderRegister.html
	 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		//form backing object
		model.addAttribute("purchaseOrder", new PurchaseOrder());
		addDorpDownUi(model);
		return "PurchaseOrderRegister";	
	}

	//2. save : on click submit
	/**
	 * URL: /save, Type: POST
	 * Goto : ShipmentTypeRegister
	 */
	@PostMapping("/save")
	public String save(
			//read from Data from UI(given by container)

			@ModelAttribute PurchaseOrder purchaseOrder,
			Model model) //to send data to UI
	{
		//perform save operation
		log.info("Entered into save method");
		try {
			Integer id=service.savePurchaseOder(purchaseOrder);
			//construct one message
			String message = "PurchaseOrder '"+id+"' saved";
			//send message to UI
			model.addAttribute("message", message);
			//Form backing Object
			model.addAttribute("purchaseOrder", new PurchaseOrder());
		}catch (Exception e) {
			log.error("Unable to save:"+e.getMessage());
			e.printStackTrace();
		}
		log.info("Back to UI Page");
		//GO TO UI Page
		addDorpDownUi(model);
		return "PurchaseOrderRegister";
	}

	//3. Display data
	@GetMapping("/all")
	public String fetchAll(
			@PageableDefault(page=0,size=3)
			Pageable pageable,Model model) {
		try {
			log.info("Entered into method");
			Page<PurchaseOrder> page = service.getAllPurchaseOrders(pageable);
			model.addAttribute("page", page);
			log.info("Got Data from DB using service:size of List is:"+page.getContent().size());

		}catch (Exception e) {
			log.error("Unable to fetch Data from DB:"+e.getMessage());
			e.printStackTrace();
		}
		log.info("Back to UI page for data display");

		return "PurchaseOrderData";
	}

	//4. Remove one by Id
	@GetMapping("/delete/{id}")
	public String removeById(
			@PathVariable Integer id, 
			Model model) {
		log.info("Entered for Delete with ID:"+id);
		try {
			String msg = null;
			if(service.isPurchaseOrderExist(id)) {
				service.deletePurchaseOrder(id);
				msg="PurchaseOrder '"+id+"'deleted ";
				log.debug(msg);
			}else {
				msg="PurchaseOrder '"+id+"' Not exist";
				log.warn(msg);	
			}
			model.addAttribute("message",msg);
			log.info("FETCHING NEW DATA AFTER DELETE");
			//show other rows
			Page<PurchaseOrder>page = service.getAllPurchaseOrders(PageRequest.of(0, 3));
			model.addAttribute("page",page);
		}catch (Exception e) {
			log.error(e.getMessage() + " : Unable to Perform Delete operation");
			e.printStackTrace();
		}
		return "PurchaseOrderData";
	}

	/**
	 * On click Edit HyperLink at UI,
	 * read one PathVariable and fetch data from 
	 * service, if exist send to edit page
	 * else redirect to data page
	 */
	//5. Show Edit Page with data
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id,  Model model) {
		log.info("Entered into EDIT Operation with id:"+id);
		String page=null;
		try {
			Optional<PurchaseOrder>opt= service.getOnePurchaseOrder(id);
			log.info("Service called is made");
			if(opt.isPresent()) {
				log.info("Data exist with Id:"+id);
				PurchaseOrder po=opt.get();
				//FORM BACKING OBJECT WITH DATA
				model.addAttribute("purchaseOrder", po);
				addDorpDownUi(model);
				page = "PurchaseOrderEdit";
			}else {
				//id not exist
				log.warn("Data not exist with Id:"+id);
				page = "redirect:../all";
			}
		}catch (Exception e) {
			log.error(e.getMessage());

			e.printStackTrace();		}
		return page;
	}

	//6. Update: on click update
	/**
	 * On click update button,read form data and perform update operation
	 * send back to Data page with success message.
	 */
	@PostMapping("/update")
	public String update(
			@ModelAttribute
			@PageableDefault(page = 0, size = 3)
			Pageable pageable,
			PurchaseOrder purchaseOrder,
			Model model) {
		log.info("Entered into Update operation");
		try {
			service.updatePurchaseOrder(purchaseOrder);
			String msg = "PurchaseOrder '"+purchaseOrder.getId()+"' updated";
			log.debug(msg);
			model.addAttribute("message", msg);
			log.info("Fetching latest data");
			//new data from Db
			Page<PurchaseOrder>page = service.getAllPurchaseOrders(pageable);
			model.addAttribute("page", page);
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();	
		}
		log.info("Back to UI page");	
		return "PurchaseOrderData";	
	}
	//7. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderExcelView());

		//send data to View class
		List<PurchaseOrder> list=service.getAllPurchaseOrders();
		m.addObject("obs", list);
		return m;
	}
	//8. Export One row to Excel File
	@GetMapping("/excel/{id}")
	public ModelAndView exportToExcelOne(
			@PathVariable Integer id) 
	{
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderExcelView());

		Optional<PurchaseOrder> opt=service.getOnePurchaseOrder(id);
		if(opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		//send data to View class one object as List
		return m;
	}
	//9. Export Data to pdf file
	/*@GetMapping("/pdf")
		public ModelAndView exportPdfAll() {
			ModelAndView m = new ModelAndView();
			m.setView(new PurchaseOrderPdfView());

			//get data from service
			List<PurchaseOrder> list=service.getAllPurchaseOrders();
			m.addObject("list", list);
			return m;
		}*/

	@GetMapping("/pdf")
	public ModelAndView showPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderPdfView());

		List<PurchaseOrder> list = service.getAllPurchaseOrders();
		m.addObject("list", list);
		return m;
	}


	//10. Export One row to PDF
	@GetMapping("/pdf/{id}")
	public ModelAndView exportPdfOne(
			@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderPdfView());

		//get data from service
		Optional<PurchaseOrder> opt=service.getOnePurchaseOrder(id);
		if(opt.isPresent()) {
			m.addObject("list", Arrays.asList(opt.get()));
		}
		return m;
	}
	//11. Generate Chart
	@GetMapping("/charts")
	public String generateCharts() {
		//data to show at Chart
		List<Object[]> list=service.getQualityCheckCount();

		//Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");
		log.info("CHART LOCATION IS: " + location );

		//call chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);

		return "PurchaseOrderCharts";
	}

	//*************************************************************************//
	//**                    SCREEN#2 OPERATIONS                             ***//
	//*************************************************************************//

	private void addDorpDownUiForDtls(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	//1. show Dtls page
	@GetMapping("/dtls/{id}")
	public String showDtls(
			@PathVariable Integer id, //PO Id,
			Model model) 
	{
		String page=null;
		//get PO using id
		Optional<PurchaseOrder> po = service.getOnePurchaseOrder(id);
		if(po.isPresent()) {
			model.addAttribute("po", po.get());
			//It will show PartsDropDown
			addDorpDownUiForDtls(model); 
			//form backing Object for Adding PART + Linked with PO
			PurchaseDetail purchaseDtl = new PurchaseDetail();
			purchaseDtl.setPo(po.get());
			model.addAttribute("purchaseDtl",purchaseDtl);

			//Display All Added Parts as HTML table
			model.addAttribute("dtlList",service.getPurchaseDtlWithPoId(po.get().getId()));

			page ="PurchaseDetails";

		}else {
			page ="redirect:../all";
		}

		return page;
	}

	//2. on click add button 
	/**
	 * Read PurchaseDtl object and save DB
	 * redirect to /dtls/{id} -> showDtl() method
	 */
	@PostMapping("/addPart")
	public String addPartToPo(
			@ModelAttribute PurchaseDetail purchaseDtl) 
	{
		service.addPartToPo(purchaseDtl);
		Integer poId = purchaseDtl.getPo().getId();
		service.updatePurchaseOrderStatus("PICKING",poId);
		return "redirect:dtls/"+poId; //POID
	}
	//3 on click delete remove one dtl from PurchaseDtls table
	@GetMapping("/removePart")
	public String removePart(
			@RequestParam Integer dtlId,
			@RequestParam Integer poId
			)
	{
		service.deletePurchaseDtl(dtlId);
		Integer dtlCount = service.getPurchaseDtlCountWithPoId(poId);
		if(dtlCount==0) {
			service.updatePurchaseOrderStatus("OPEN",poId);
		}
		return "redirect:dtls/"+poId; //POID
	}
	//4. On click conform button change status from PICKING to ORDERED
	@GetMapping("/conformOrder/{id}")
	public String placeOrder(@PathVariable Integer id)
	{
		Integer dtlCount = service.getPurchaseDtlCountWithPoId(id);
		if(dtlCount>0) {
			service.updatePurchaseOrderStatus("ORDERED",id);
		}
		return "redirect:../dtls/"+id; //POID
	}
	//5. chnage status from ORDERED to INVOICED
	@GetMapping("/invoiceOrder/{id}")
	public String invoiceOrder(@PathVariable Integer id)
	{
		service.updatePurchaseOrderStatus("INVOICED",id);
		return "redirect:../all"; //POID
	}


	//6. chnage status from ORDERED to INVOICED
	@GetMapping("/printInvoice/{id}")
	public ModelAndView printInvoice(@PathVariable Integer id)
	{
		ModelAndView m = new ModelAndView();
		m.setView(new VendorInvoicePdf());
		m.addObject("po", service.getOnePurchaseOrder(id).get());
		return m;
	}
}
