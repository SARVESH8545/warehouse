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
import org.springframework.web.servlet.ModelAndView;

import com.app.model.OrderMethod;
import com.app.service.IOrderMethodService;
import com.app.util.OrderMethodUtil;
import com.app.view.OrderMethodExcelView;
import com.app.view.OrderMethodPdfView;

@Controller
@RequestMapping("/ordermethod")
public class OrderMethodController
{
	private Logger log = LoggerFactory.getLogger(OrderMethodController.class);
	@Autowired
	private IOrderMethodService service;
	@Autowired
	private ServletContext context;
	@Autowired
	private OrderMethodUtil util;

	//1. show Reg page
	@GetMapping("/register")
	public String showReg(Model model) {
		model.addAttribute("orderMethod",new OrderMethod());
		return "OrderMethodRegister";
	}
	//2. sava data
	@PostMapping("/save")
	public String save(
			@ModelAttribute OrderMethod orderMethod ,
			Model model) 
	{
		Integer id=service.saveOrderMethod(orderMethod);
		String message="Order Method '"+id+"' saved";
		model.addAttribute("message", message);
		model.addAttribute("orderMethod", new OrderMethod());
		return "OrderMethodRegister";
	}
	//3.show all
	@GetMapping("/all")
	public String getAll(
			@PageableDefault(page = 0, size = 3)
			Pageable pageable,
			Model model)
	{
		Page<OrderMethod>page=service.getAllOrderMethods(pageable);
		model.addAttribute("page",page);
		return "OrderMethodData";
	}

	//4. delete the record
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, Model model) {
		String message=null;
		if(service.isOrderMethodeExist(id)) {
			service.deleteOrderMethod(id);
			message="Order Method '"+id+"' Deleted";
		}else {
			message="Order Method '"+id+"' Not exist";
		}
		model.addAttribute("message", message);
		Page<OrderMethod>page=service.getAllOrderMethods(PageRequest.of(0, 3));
		model.addAttribute("page",page);
		return "OrderMethodData";

	}
	//5. edit page
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id,Model model) {
		String page=null;
		Optional<OrderMethod> opt=service.getOneOrderMethod(id);
		if(opt.isPresent()) {
			model.addAttribute("orderMethod",opt.get());
			page="OrderMethodEdit";

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
			OrderMethod orderMethod, 
			Model model) {
		service.updateOrderMethod(orderMethod);
		model.addAttribute("message", "Order Method '"+orderMethod.getId()+"' Updated!");
		Page<OrderMethod>page=service.getAllOrderMethods(pageable);
		model.addAttribute("page",page);
		return "OrderMethodData";
	}

	//7. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView showExcel() {
		ModelAndView m=new ModelAndView();

		//link with view class object
		m.setView(new OrderMethodExcelView()); //Output is excel

		//fetch data from db
		List<OrderMethod> list=service.getAllOrderMethods();

		//add data to ModelAndView that is shared with OrderMethodExcelView
		m.addObject("list", list);

		return m;
	}
	//8. Export One row to Excel File
	@GetMapping("/excel/{id}")
	public ModelAndView exportExcelOne(
			@PathVariable Integer id) {
		ModelAndView mov = new ModelAndView();
		mov.setView(new OrderMethodExcelView());
		Optional<OrderMethod>opt = service.getOneOrderMethod(id);
		if(opt.isPresent()) {
			mov.addObject("obs", Arrays.asList(opt.get()));
		}
		return mov;
	}
	//9. Export Data to pdf file
	/*@GetMapping("/pdf")
	public ModelAndView exportPdfAll() {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodPdfView());

		//get data from service
		List<OrderMethod> list=service.getAllOrderMethods();
		m.addObject("list", list);
		return m;
	}*/

	@GetMapping("/pdf")
	public ModelAndView showPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodPdfView());

		List<OrderMethod> list = service.getAllOrderMethods();
		m.addObject("list", list);
		return m;
	}


	//10. Export One row to PDF
	@GetMapping("/pdf/{id}")
	public ModelAndView exportPdfOne(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodPdfView());

		//get data from service
		Optional<OrderMethod> opt=service.getOneOrderMethod(id);
		if(opt.isPresent()) {
			m.addObject("list", Arrays.asList(opt.get()));
		}
		return m;
	}

	//11. Convert data into JFreeCharts
	@GetMapping("/charts")
	public String generateCharts() {
		//data to show at Chart
		List<Object[]> list=service.getOrderModeCount();

		//Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");
		log.info("CHART LOCATION IS: " + location );

		//call chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);

		return "OrderMethodCharts";
	}
	//Google Chart
	@GetMapping("/gcharts")
	public @ResponseBody List<Object[]> showGoogleCharts() {
		//Get data from Service as Object[]
		List<Object[]> list =service.getOrderModeCount();
		return list;
	}	



	//--AJAX VALIDATIONS-------------
	@GetMapping("/validatecode")
	public @ResponseBody String validateCode(
			@RequestParam String code,@RequestParam Integer id)
	{
		String msg="";
		if(id==0 && service.isOrderMethodCodeExist(code)) {
			msg = "Order Method <b>code '"+code+"' already exist</b>!";
		}else if(service.isOrderMethodCodeExistEdit(code,id)) {
			msg = "Order Method <b>code '"+code+"' already exist</b>!";
		}
		return msg;
	}
}
