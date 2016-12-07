package io.moorea.service;

import java.util.UUID;
import io.moorea.entity.Document;
import io.moorea.model.JsonResult;

public interface DocumentRepositoryService {
	
	public UUID save(Document document);
	
	public JsonResult getDocuments(String type,int page,int limit,String order_filed,String order_direction);
	
	public JsonResult getDocumentById(UUID id);
	
	public JsonResult getDocumentFileById(UUID id,String fileId);
	
	public JsonResult saveDocumentFile(UUID id,int number, byte[] toSave);
	
	public int nextNumber(UUID id);
}
