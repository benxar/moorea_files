package io.moorea.service;

import java.util.List;
import java.util.UUID;
import io.moorea.entity.Document;
import io.moorea.entity.ExpiringDocument;
import io.moorea.entity.Signer;
import io.moorea.model.JsonResult;
import io.moorea.parser.request.FilePostRequest;

public interface DocumentRepositoryService {

	public UUID save(Document document);

	public JsonResult getDocuments(String type, int page, int limit, String order_filed, String order_direction);

	public JsonResult getDocumentById(UUID id);

	public JsonResult searchDocument(String officeId, String categoryId, int year);

	public JsonResult getDocumentFileById(UUID id, int fileId);

	public JsonResult saveDocumentFile(UUID id, int number, UUID key, List<Signer> lSigners, FilePostRequest req);

	public ExpiringDocument nextNumber(UUID id, List<Signer> lSigners);
}
