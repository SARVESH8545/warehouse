package com.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Document;
import com.app.repo.DocumentRepository;
import com.app.service.IDocumentService;
@Service
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private DocumentRepository repo;

	@Transactional
	public void saveDocument(Document doc) {
		repo.save(doc);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findIdAndName() {
		return repo.findIdAndName();
	}
	@Override
	@Transactional(readOnly = true)
	public Optional<Document> getOneDocument(Integer id) {
		return repo.findById(id);
	}
}

