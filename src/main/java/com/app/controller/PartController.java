package com.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.app.model.Part;

import com.app.service.IPartService;
import com.app.service.IUomService;
import com.app.view.PartExcelView;
import com.app.view.PartPdfView;

@Controller
@RequestMapping("/part")
public class PartController {
	@Autowired
	private IPartService service;

	@Autowired
	private IUomService uomService;
	
	//call this method inside other controller methods
	// where return page is Register or Edit.
	private void addDorpDownUi(Model model) {
		model.addAttribute("uoms",uomService.getUomIdAndModel());
	}


	//1. Show Register Page
	/**
	 *  URL :/register, Type:GET 
	 *  Goto Page PartRegister.html
	 */
	@GetMapping("/register")
	public String showRegister(Model model) 
	{
		//form backing object
		model.addAttribute("part", new Part());
		addDorpDownUi(model);
		return "PartRegister";
	}
	//2. save : on click submit
	/**
	 * URL: /save, Type: POST
	 * Goto : PartRegister
	 */
	@PostMapping("/save")
	public String save(@ModelAttribute Part part,
			Model model//to send data to UI
			//perform save operation
			) {
		Integer	id=service.savePart(part);
		String message="Part '"+id+"' saved ";
		//send message to UI
		model.addAttribute("message",message);
		//Form backing Object
		model.addAttribute("part", new Part());
		addDorpDownUi(model);
		//Goto Page
		return "PartRegister";
	}
	//3. Display data
	@GetMapping("/all")
	public String 	fetchAll(
			@PageableDefault(page=0, size=3)
			Pageable pageable,
			Model model) {
		Page<Part>page=service.getAllParts(pageable);
		model.addAttribute("page",page);
		return "PartData";	
	}

	//4. Remove one by Id
	@GetMapping("/delete/{id}")
	public String removeById(
			@PathVariable Integer id,
			Model model) 
	{
		String message=null;
		if(service.isPartExist(id)) {
			service.deletePart(id);
			message="Part '"+id+"' deleted";
		}else {
			message="Part '"+id+"' Not Exist";
		}
		model.addAttribute("message",message);
		Page<Part>page=service.getAllParts(PageRequest.of(0, 3));
		model.addAttribute("page",page);
		return "PartData";	
	}
	//5. edit page
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id,Model model) {
		String page=null;
		Optional<Part> opt=service.getOnePart(id);
		if(opt.isPresent()) {
			model.addAttribute("part",opt.get());
			addDorpDownUi(model);
			page="PartEdit";

		}else {
			page="redirect:../all";
		}
		return page;
	}
	//6. do update
	@PostMapping("/update")
	public String update(
			@ModelAttribute Part part,
			@PageableDefault(page=0, size=0)
			Pageable pageable,
			Model model) {
		service.updatePart(part);
		model.addAttribute("message", "Part'"+part.getId()+"' Updated!");
		Page<Part>page=service.getAllParts(pageable);
		model.addAttribute("page",page);
		return "PartData";
	}
	//7. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView mov=new ModelAndView();
		mov.setView(new PartExcelView());
		//send data to view class
		List<Part> list = service.getAllParts();
		mov.addObject("obs", list);
		return mov;
	}
	//8.Export One Row to Excel File
	@GetMapping("/excel/{id}")
	public ModelAndView exportToExcelOne(
			@PathVariable Integer id) {
		ModelAndView mov = new ModelAndView();
		mov.setView(new PartExcelView());
		Optional<Part> opt = service.getOnePart(id);
		if(opt.isPresent()) {
			mov.addObject("obs",Arrays.asList(opt.get()));
		}//send data to View class one object as List
		return mov;
	}

	//9. export all pdf
	@GetMapping("/pdf")
	public ModelAndView exportPdfAll() {
		ModelAndView mov=new ModelAndView();
		mov.setView(new PartPdfView());
		//get data from service

		List<Part> list=service.getAllParts();
		mov.addObject("list", list);
		return mov;
	}
	//10. Export One Row
	@GetMapping("/pdf/{id}")
	public ModelAndView exportPdfOne(@PathVariable Integer id)
	{
		ModelAndView mov=new ModelAndView();
		mov.setView(new PartPdfView());
		//get data from service
		Optional<Part> opt=service.getOnePart(id);
		if(opt.isPresent()) {
			mov.addObject("list", Arrays.asList(opt.get()));
		}
		return mov;
	}
	//---AJAX VALIDATION----------
	//.. /ordermethod/validatecode?code=ABCD
	@GetMapping("/validatecode")
	public @ResponseBody String validatePartCode(
			@RequestParam String code) 
	{
		String message="";
		if(service.isPartCodeExist(code)) {
			message = "Part Code <b>'"+code+"' Already exist</b>!";
		}
		return message;
	}
}
