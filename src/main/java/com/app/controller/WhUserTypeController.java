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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.app.model.WhUserType;
import com.app.service.IWhUserTypeService;
import com.app.util.EmailUtil;
import com.app.util.WhUserTypeUtil;
import com.app.view.WhUserTypeExcelView;
import com.app.view.WhUserTypePdfView;

@Controller
@RequestMapping("/whusertype")

public class WhUserTypeController {

	private Logger log = LoggerFactory.getLogger(WhUserTypeController.class);
	@Autowired
	private IWhUserTypeService service; //HAS-A
	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private ServletContext context;
	@Autowired
	private WhUserTypeUtil util;

	//1. show Reg page
	/**
	 *  URL :/register, Type:GET 
	 *  Goto Page WhUserTypeRegister.html
	 */

	@GetMapping("/register")
	public String showReg(Model model) {
		model.addAttribute("whUserType",new WhUserType());
		return "WhUserTypeRegister";
	}

	//2. sava data
	/**
	 * URL: /save, Type: POST
	 * Goto : WhUserTypeRegister
	 */
	/*@PostMapping("/save")
	public String save(	//read from Data from UI(given by container)
			@ModelAttribute WhUserType whUserType ,
			Model model) //to send data to UI
	{
		//perform save operation
		Integer id=service.saveWhUserType(whUserType);
		//construct one message
		String message="WhUser Type '"+id+"' saved";
		//send email on save
		if(id!=0) {
			new Thread(new Runnable() {
				public void run() {
					boolean flag=emailUtil.send(whUserType.getUserMail(), "WELCOME", 
							"Hello User:"+whUserType.getUserCode() 
							+", You are type:"+whUserType.getUserIdType());
					System.out.println(flag);
				}
			}).start();
			//if(flag) message+=", Email also sent!";
			//else message+=", Email is not sent!";
		}
		//send message to UI

		model.addAttribute("message", message);
		//Form backing Object 
		model.addAttribute("whUserType", new WhUserType());
		return "WhUserTypeRegister";
	}*/
	//document attachment
	@PostMapping("/save")
	public String save(
			//read from Data from UI(given by container)
			@ModelAttribute WhUserType whUserType,
			@RequestParam("fileOb") MultipartFile fileOb,
			Model model //to send data to UI
			)
	{
		//perform save operation
		Integer id=service.saveWhUserType(whUserType);

		//construct one message
		String message="WhUserType '"+id+"' saved successfully";

		//send email on save

		boolean flag=emailUtil.send(
				whUserType.getUserMail(), 
				"WELCOME", 
				"Hello User:"+whUserType.getUserCode() 
				+", You are type:"+whUserType.getUserIdType(),
				fileOb);
		System.out.println(flag);

		if(flag) message+=", Email also sent!";
		else message+=", Email is not sent!";

		//send message to UI
		model.addAttribute("message", message);
		//Form backing Object 
		model.addAttribute("whUserType", new WhUserType());
		//Goto Page
		return "WhUserTypeRegister";
	}

	//3.show all
	@GetMapping("/all")
	public String getAll(
			@PageableDefault(page = 0, size = 3)
			Pageable pageable,
			Model model)
	{
		Page<WhUserType> page=service.getAllWhUserTypes(pageable);
		model.addAttribute("page", page);
		return "WhUserTypeData";
	}

	//4. delete the record
	@GetMapping("/delete/{id}")
	public String delete(
			@PathVariable Integer id,
			Model model) {
		String message=null;
		if(service.isWhUserTypeExist(id)) {
			service.deleteWhUserType(id);
			message="WhUser Type '"+id+"' Deleted";
		}else {
			message="WhUser Type '"+id+"' Not exist";
		}
		model.addAttribute("message", message);
		Page<WhUserType> page=service.getAllWhUserTypes(PageRequest.of(0, 3));
		model.addAttribute("page", page);
		return "WhUserTypeData";
	}

	//5. edit page
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id,Model model) {
		String page=null;
		Optional<WhUserType> opt=service.getOneWhUserType(id);
		if(opt.isPresent()) {
			model.addAttribute("whUserType",opt.get());
			page="WhUserTypeEdit";

		}else {
			page="redirect:../all";
		}
		return page;
	}
	//6. do update
	@PostMapping("/update")
	public String update(
			@ModelAttribute
			@PageableDefault(page = 0, size = 3)
			Pageable pageable,
			WhUserType whUserType, Model model) {
		service.updateWhUserType(whUserType);
		model.addAttribute("message", "WhUser Type '"+whUserType.getId()+"' Updated!");
		Page<WhUserType>page=service.getAllWhUserTypes(pageable);
		model.addAttribute("page", page);
		return "WhUserTypeData";
	}
	//7 show one
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		String page=null;
		Optional<WhUserType>opt=service.getOneWhUserType(id);
		if(opt.isPresent()) {
			WhUserType wut=opt.get();
			model.addAttribute("ob", wut);
			page="WhUserTypeView";	
		}else {
			page="redirect:../all";
		}
		return page;
	}
	//8. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView excelToExport() {
		ModelAndView m=new ModelAndView();
		m.setView(new WhUserTypeExcelView());
		//send data to View class
		List<WhUserType> list=service.getAllWhUserTypes();
		m.addObject("obs",list);
		return m;
	}
	//9 Export One Row to Excel File
	@GetMapping("/excel/{id}")
	public ModelAndView exportToExcelOne(@PathVariable Integer id) 
	{
		ModelAndView m=new ModelAndView();
		m.setView(new WhUserTypeExcelView());
		Optional<WhUserType> opt = service.getOneWhUserType(id);
		if(opt.isPresent()) {
			m.addObject("obs",Arrays.asList(opt.get()));
		}
		//send data to View class one object as List
		return m;
	}
	//9. export all pdf
	@GetMapping("/pdf")
	public ModelAndView exportPdfAll() {
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypePdfView());

		//get data from service
		List<WhUserType> list=service.getAllWhUserTypes();
		m.addObject("list", list);
		return m;
	}
	//10. Export One row to PDF
	@GetMapping("/pdf/{id}")
	public ModelAndView exportPdfOne(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypePdfView());

		//get data from service
		Optional<WhUserType> opt=service.getOneWhUserType(id);
		if(opt.isPresent()) {
			m.addObject("list", Arrays.asList(opt.get()));
		}

		return m;
	}
	//11. Generate Chart
	@GetMapping("/charts")
	public String generateCharts() {
		//data to show at Chart
		List<Object[]> list=service.getWhUserTypeCount();

		//Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");
		log.info("CHART LOCATION IS: " + location );

		//call chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);

		return "WhUserTypeChart";
	}

	//----------AJAX Validation--------------
	@GetMapping("/mailcheck")
	public @ResponseBody String validateEmail(@RequestParam String mail) 
	{
		String message="";
		if(service.isWhUserTypeEmailExist(mail)) {
			message = mail+" <b>already exist!</b>";
		}
		return message;
	}

}
