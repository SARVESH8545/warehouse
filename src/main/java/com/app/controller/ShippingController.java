package com.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.app.model.SaleDtl;
import com.app.model.Shipping;
import com.app.model.ShippingDtl;
import com.app.service.ISaleOrderService;
import com.app.service.IShippingService;
import com.app.view.ShippingExcelView;
import com.app.view.ShippingPdfView;

@Controller // =Object + HTTP Request
@RequestMapping("/shipping")
public class ShippingController {
	private Logger log = LoggerFactory.getLogger(ShippingController.class);
	@Autowired
	private IShippingService service;
	@Autowired
	private ISaleOrderService soservice;

	//1. Show Register Page
	/**
	 *  URL :/register, Type:GET 
	 *  Goto Page ShippingRegister.html
	 */

	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("shipping",new Shipping());
		return "ShippingRegister";
	}

	//2. save : on click submit
	/**
	 * URL: /save, Type: POST
	 * Goto : ShippingRegister
	 */
	@PostMapping("/save")
	public String save(
			//read form Data from UI
			@ModelAttribute Shipping shipping,
			Model model) //send data to ui
	{
		//perform save operation
		log.info("Entered into save method");
		try {
			Integer	id = service.saveShipping(shipping);
			String message = "shipping '"+id+"' saved";
			model.addAttribute("message", message);
			model.addAttribute("shipping",new Shipping());
		}catch(Exception e) {
			log.error("Unable to save:"+e.getMessage());
			e.printStackTrace();
		}
		log.info("Back to UI Page");
		//Goto Page
		return "ShippingRegister";
	}
	//3 Fetch all data
	@GetMapping("/all")
	public String fetchAll(
			@PageableDefault(page = 0,size = 3)
			Pageable pageable,
			Model model){
		try {
			log.info("Entered into method");
			Page<Shipping> page=service.getAllShipping(pageable);
			model.addAttribute("page", page);
		}catch(Exception e) {
			log.error("Unable to fetch Data from DB:"+e.getMessage());
			e.printStackTrace();		
		}
		log.info("Back to UI page for data display");
		return "ShippingData";
	}

	//4.remove by id
	@GetMapping("/delete/{id}")
	public String removeById(@PathVariable Integer id,Model model) 
	{
		log.info("Entered for Delete with ID:"+id);
		try {
			String msg=null;
			if(service.isShippingExist(id)) {
				service.deleteShipping(id);
				msg="shipping '"+id+"' deleted!";
				log.debug(msg);

			}else {
				msg="shipping '"+id+"' Not exist!";
				log.warn(msg);
			}

			model.addAttribute("message", msg);
			log.info("FETCHING NEW DATA AFTER DELETE");
			//show other rows
			//Page<Shipping> page=service.getAllShippings(PageRequest.of(0, 3));
			//model.addAttribute("page", page);
		}catch(Exception e) {
			log.error(e.getMessage() + " : Unable to Perform Delete operation");
			e.printStackTrace();	
		}

		return "ShippingData";
	}

	//5. Show Edit Page with data
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id,Model model)
	{
		log.info("Entered into EDIT Operation with id:"+id);
		String page=null;
		try {
			Optional<Shipping> opt=service.getOneShipping(id);
			log.info("Service called is made");
			if(opt.isPresent()) {
				log.info("Data exist with Id:"+id);	
				Shipping shipping=opt.get();
				//FORM BACKING OBJECT WITH DATA
				model.addAttribute("shipping", shipping);
				page="ShippingEdit";
			}else { 
				//id not exist
				log.warn("Data not exist with Id:"+id);
				page="redirect:../all";
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return page;
	}


	/**
	 * On click update button,read form data and perform update operation
	 * send back to Data page with success message.
	 */
	//6. Update: on click update
	@PostMapping("/update")
	public String update(
			@ModelAttribute Shipping shipping,
			Model model)
	{
		log.info("Entered into Update operation");
		try {
			service.updateShipping(shipping);
			String msg="shipping '"+shipping.getId()+"' Updated!";
			log.debug(msg);
			model.addAttribute("message", msg);
			log.info("Fetching latest data");

			//new data from Db
			//Page<Shipping> page=service.getAllShippings(PageRequest.of(0, 3));
			//model.addAttribute("page", page);
		}catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();		
		}
		log.info("Back to UI page");
		return "ShippingData";
	}
	//7. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShippingExcelView());

		//send data to View class
		List<Shipping> list=service.getAllShippings();
		m.addObject("obs", list);
		return m;
	}
	//8. Export One row to Excel File
	@GetMapping("/excel/{id}")
	public ModelAndView exportToExcelOne(
			@PathVariable Integer id) 
	{
		ModelAndView m = new ModelAndView();
		m.setView(new ShippingExcelView());

		Optional<Shipping> opt=service.getOneShipping(id);
		if(opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		//send data to View class one object as List
		return m;
	}

	//9. export all pdf
	@GetMapping("/pdf")
	public ModelAndView exportPdfAll() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShippingPdfView());

		//get data from service
		List<Shipping> list=service.getAllShippings();
		m.addObject("list", list);

		return m;
	}

	//10. Export One row to PDF
	@GetMapping("/pdf/{id}")
	public ModelAndView exportPdfOne(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new ShippingPdfView());

		//get data from service
		Optional<Shipping> opt=service.getOneShipping(id);
		if(opt.isPresent()) {
			m.addObject("list", Arrays.asList(opt.get()));
		}
		return m;
	}

	//---AJAX VALIDATION----------
	//.. /shipmenttype/validatecode?code=ABCD
	@GetMapping("/validatecode")
	public @ResponseBody String validateShippingCode(
			@RequestParam String code,
			@RequestParam Integer id) 
	{
		String message="";
		if(id==0 && service.isShippingCodeExist(code)) { //register
			message = "Shipment Code <b>'"+code+"' Already exist</b>!";
		} else if(service.isShippingCodeExistForEdit(code,id)) { //edit process
			message = "Shipment Code <b>'"+code+"' Already exist</b>!";
		}
		return message;
	}

	//*************************************************************************//
	//**                    SCREEN#2 OPERATIONS                             ***//
	//*************************************************************************//
	@SuppressWarnings("unused")
	private void convertSaleDtlToShippinDtl(Integer shippingId, Integer poId) {
		// 1. Get SaleDtls List using SoId
		List<SaleDtl> soDtlsList = soservice.getSaleDtlWithSoId(poId);

		// 2. convert one SoDtl object to one ShippingDtlObject
		for (SaleDtl soDtl : soDtlsList) {
			ShippingDtl shippingDtl = new ShippingDtl();
			shippingDtl.setItemCode(soDtl.getPart().getPartCode());

			shippingDtl.setBaseCost(soDtl.getPart().getBaseCost());
			shippingDtl.setQty(soDtl.getQty());
			shippingDtl.setItemValue(shippingDtl.getQty() * shippingDtl.getBaseCost());

			// link with shippingDtl with Shipping object (Parent)
			Shipping shipping = new Shipping();
			shipping.setId(shippingId); // only ID is OK.
			shippingDtl.setShipping(shipping);

			// save in Database
			service.saveShippingDtl(shippingDtl);

		}
	}

	// Display all Shipping Dtls here
	@GetMapping("/viewDtls/{id}")
	public String showDtls(@PathVariable Integer id, // SHIPPING ID
			Model model) {
		List<ShippingDtl> dtls = service.getAllDtlsByShippingId(id);
		model.addAttribute("dtls", dtls);
		return "ShippingDtlView";
	}

	//Change status for ACCEPTED and REJECTED
	@GetMapping("/status")
	public String updateDtlStatus(
			@RequestParam Integer shippingId,
			@RequestParam Integer dtlId,
			@RequestParam String status
			) 
	{
		service.updateStatusByShippinDtlId(status, dtlId);
		return "redirect:viewDtls/"+shippingId;
	}

}
