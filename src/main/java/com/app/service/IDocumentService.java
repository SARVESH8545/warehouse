package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.model.Document;

public interface IDocumentService {
	public void saveDocument(Document doc);
	public List<Object[]> findIdAndName();
	public Optional<Document> getOneDocument(Integer id);
	//public Optional<Document> findDocument(Integer id);
}
