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
import org.springframework.web.servlet.ModelAndView;

//import com.app.model.ShipmentType;
import com.app.model.Uom;
import com.app.service.IUomService;
import com.app.util.UomUtil;
import com.app.view.UomExcelView;
import com.app.view.UomPdfView;
@Controller
@RequestMapping("/uom")//Object +HTTP Request
public class UomController {
	private Logger log = LoggerFactory.getLogger(UomController.class);
	@Autowired
	private IUomService service;//HAS-A
	@Autowired
	private ServletContext context;
	@Autowired
	private UomUtil util;

	//1. Show Register Page
	/**
	 *  URL :/register, Type:GET 
	 *  Goto Page UomRegister.html
	 */

	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("uom",new Uom());
		return "UomRegister";
	}

	//2. save : on click submit
	/**
	 * URL: /save, Type: POST
	 * Goto : UomRegister
	 */
	@PostMapping("/save")
	public String save(
			//read form Data from UI
			@ModelAttribute Uom uom,
			Model model) //send data to ui
	{
		//perform save operation
		log.info("Entered into save method");
		try {
			Integer	id = service.saveUom(uom);
			String message = "Uom '"+id+"' saved";
			model.addAttribute("message", message);
			model.addAttribute("uom",new Uom());
		}catch(Exception e) {
			log.error("Unable to save:"+e.getMessage());
			e.printStackTrace();
		}
		log.info("Back to UI Page");
		//Goto Page
		return "UomRegister";
	}
	//3 Fetch all data
	@GetMapping("/all")
	public String fetchAll(@PageableDefault(page = 0,size = 3)Pageable pageable,Model model){
		try {
			log.info("Entered into method");
			Page<Uom> page=service.getAllUoms(pageable);
			model.addAttribute("page", page);
		}catch(Exception e) {
			log.error("Unable to fetch Data from DB:"+e.getMessage());
			e.printStackTrace();		
		}
		log.info("Back to UI page for data display");
		return "UomData";
	}
	
	//4.remove by id
	@GetMapping("/delete/{id}")
	public String removeById(@PathVariable Integer id,Model model) 
	{
		log.info("Entered for Delete with ID:"+id);
		try {
			String msg=null;
			if(service.isUomExist(id)) {
				service.deleteUom(id);
				msg="Uom '"+id+"' deleted!";
				log.debug(msg);

			}else {
				msg="Uom '"+id+"' Not exist!";
				log.warn(msg);
			}

			model.addAttribute("message", msg);
			log.info("FETCHING NEW DATA AFTER DELETE");
			//show other rows
			Page<Uom> page=service.getAllUoms(PageRequest.of(0, 3));
			model.addAttribute("page", page);
		}catch(Exception e) {
			log.error(e.getMessage() + " : Unable to Perform Delete operation");
			e.printStackTrace();	
		}

		return "UomData";
	}

	//5. Show Edit Page with data
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id,Model model)
	{
		log.info("Entered into EDIT Operation with id:"+id);
		String page=null;
		try {
			Optional<Uom> opt=service.getOneUom(id);
			log.info("Service called is made");
			if(opt.isPresent()) {
				log.info("Data exist with Id:"+id);	
				Uom um=opt.get();
				//FORM BACKING OBJECT WITH DATA
				model.addAttribute("uom", um);
				page="UomEdit";
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
			@ModelAttribute Uom uom,
			Model model)
	{
		log.info("Entered into Update operation");
		try {
			service.updateUom(uom);
			String msg="Uom '"+uom.getId()+"' Updated!";
			log.debug(msg);
			model.addAttribute("message", msg);
			log.info("Fetching latest data");

			//new data from Db
			Page<Uom> page=service.getAllUoms(PageRequest.of(0, 3));
			model.addAttribute("page", page);
		}catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();		
		}
		log.info("Back to UI page");
		return "UomData";
	}
	//7. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView mov=new ModelAndView();
		mov.setView(new UomExcelView());
		//send data to view class
		List<Uom> list = service.getAllUoms();
		mov.addObject("obs", list);
		return mov;
	}
	//8.Export One Row to Excel File
	@GetMapping("/excel/{id}")
	public ModelAndView exportToExcelOne(
			@PathVariable Integer id) {
		ModelAndView mov = new ModelAndView();
		mov.setView(new UomExcelView());
		Optional<Uom> opt = service.getOneUom(id);
		if(opt.isPresent()) {
			mov.addObject("obs",Arrays.asList(opt.get()));
		}//send data to View class one object as List
		return mov;
	}
	//9. export all pdf
	@GetMapping("/pdf")
	public ModelAndView exportPdfAll() {
		ModelAndView mov=new ModelAndView();
		mov.setView(new UomPdfView());
		//get data from service

		List<Uom> list=service.getAllUoms();
		mov.addObject("list", list);
		return mov;
	}
	//10. Export One Row
	@GetMapping("/pdf/{id}")
	public ModelAndView exportPdfOne(@PathVariable Integer id)
	{
		ModelAndView mov=new ModelAndView();
		mov.setView(new UomPdfView());
		//get data from service
		Optional<Uom> opt=service.getOneUom(id);
		if(opt.isPresent()) {
			mov.addObject("list", Arrays.asList(opt.get()));
		}
		return mov;
	}
	//11. Generate Charts
	@GetMapping("/charts")
	public String generateCharts() {
		//data to show at Chart
		List<Object[]>list=service.getUomTypeCount();
		//Dynamic Temp Folder location service instance
		String location = context.getRealPath("/");
		log.info("CHART LOCATION IS: "+location);

		//call chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		return "UomCharts";
	}
}











