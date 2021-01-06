package com.app.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Document;
import com.app.service.IDocumentService;

@Controller
@RequestMapping("/documents")
public class DocumentController {
	@Autowired
	private IDocumentService service;

	@GetMapping("/all")
	public String showDocs(Model model) {
		model.addAttribute("list", service.findIdAndName());
		return "Documents";
	}

	@PostMapping("/save")
	public String upload(
			@RequestParam Integer fileId,
			@RequestParam MultipartFile fileOb

			)
	{
		if(fileOb!=null && fileId!=null) {
			Document doc = new Document();
			doc.setDocId(fileId);
			doc.setDocName(fileOb.getOriginalFilename()); //file name
			try {
				doc.setDocData(fileOb.getBytes()); //file data
			} catch (IOException e) {
				e.printStackTrace();
			}
			service.saveDocument(doc);
		}
		return "redirect:all";
	}
	@GetMapping("/download/{id}")
	public void downloadDoc(
			@PathVariable Integer id,
			HttpServletResponse resp
			) 
	{
		// Get data from DB using ID
		Optional<Document> opt = service.getOneDocument(id);

		if(opt.isPresent()) {
			//read document object
			Document doc = opt.get();

			//Add Header Parameter (key-val) to response
			resp.addHeader("Content-Disposition", 
					"attachment;filename=" + doc.getDocName() );

			try {
				//copy byte[] data from Model class object to Servlet Output stream
				FileCopyUtils.copy(
						doc.getDocData(),    //from
						resp.getOutputStream() //to
						);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}