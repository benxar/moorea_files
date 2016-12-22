package io.moorea.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.moorea.entity.*;
import io.moorea.enums.ExpiringDocumentErrorCode;
import io.moorea.model.JsonResult;
import io.moorea.parser.request.FilePostRequest;
import io.moorea.persistence.DocumentFileDAO;
import io.moorea.persistence.RepositoryDatastore;
import io.moorea.service.DocumentRepositoryService;
import io.moorea.service.ExpiringDocumentRepositoryService;

@Service
public class DocumentRepositoryServiceImpl implements DocumentRepositoryService {
	@Autowired
	private ExpiringDocumentRepositoryService edService;

	@Autowired
	private DocumentFileDAO fileArchive;

	@Override
	public UUID save(Document document) {
		if (RepositoryDatastore.getDatastore() != null)
			return (UUID) RepositoryDatastore.getDatastore().save(document).getId();
		else
			return null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public JsonResult getDocuments(String type, int page, int limit, String order_filed, String order_direction) {
		JsonResult toReturn = null;
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> query = RepositoryDatastore.getDatastore().createQuery(Document.class);
				List<Document> result = query.field("type").equal(type)
						.order((order_direction.compareToIgnoreCase("asc") == 0 ? "" : "-") + order_filed).offset(page)
						.limit(limit).asList();
				if (result != null && result.size() > 0)
					toReturn = new JsonResult(true, "Success", result);
				else
					toReturn = new JsonResult(false, "No results where found");
			} else
				toReturn = new JsonResult(false, "Error while connecting to database");
		} catch (Exception e) {
			toReturn = new JsonResult(false, "Error while performing search");
		}
		return toReturn;
	}

	@Override
	public JsonResult getDocumentById(UUID id) {
		JsonResult toReturn = null;
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> query = RepositoryDatastore.getDatastore().createQuery(Document.class);
				Document result = query.field("id").equal(id).get();
				if (result != null)
					toReturn = new JsonResult(true, "Success", result);
				else
					toReturn = new JsonResult(false, "No result was found");
			} else
				toReturn = new JsonResult(false, "Error while connecting to database");
		} catch (Exception e) {
			toReturn = new JsonResult(false, "Error while performing search");
		}
		return toReturn;
	}

	public JsonResult searchDocument(String officeId, String categoryId, int year) {
		JsonResult result = null;
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> q = RepositoryDatastore.getDatastore().createQuery(Document.class);
				Document auxResult = q.field("office.id").equal(officeId).field("category.id").equal(categoryId)
						.field("year").equal(year).get();
				if (auxResult != null) {
					result = new JsonResult(true, "Success", auxResult.getId().toString());
				} else
					result = new JsonResult(false, "No result was found");
			} else
				result = new JsonResult(false, "Error while connecting to database");
		} catch (Exception e) {
			e.printStackTrace();
			result = new JsonResult(false, "Error while performing search");
		}
		return result;
	}

	@Override
	public JsonResult getDocumentFileById(UUID id, int fileId) {
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> q = RepositoryDatastore.getDatastore().createQuery(Document.class);
				Document auxResult = q.field("id").equal(id).field("files.doc_id").equal(fileId).project("files", true)
						.get();
				if (auxResult != null)
					for (DocumentFile aux : auxResult.getFiles())
						if (aux.getDoc_id() == fileId) {
							DocumentFile df = fileArchive.retrieveFile(id, fileId);
							aux.setB64(df.getB64());
							return new JsonResult(true, "Success", aux);
						}
				return new JsonResult(false, "No result was found");
			} else
				return new JsonResult(false, "Error while connecting to database");
		} catch (Exception e) {
			return new JsonResult(false, "Error while performing search");
		}
	}

	@Override
	public JsonResult saveDocumentFile(UUID id, int number, UUID key, FilePostRequest req) {
		JsonResult toReturn = null;
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> q = RepositoryDatastore.getDatastore().createQuery(Document.class);
				Document auxResult = q.field("id").equal(id).get();
				if (auxResult != null) {
					if (fileArchive.saveFile(id, number, req)) {
						UpdateOperations<Document> uq = RepositoryDatastore.getDatastore()
								.createUpdateOperations(Document.class);
						DocumentFile toAdd = new DocumentFile(id, number, req.getName(),
								id.toString() + "_" + number + ".pdf");
						RepositoryDatastore.getDatastore().update(auxResult, uq.push("files", toAdd));
						RepositoryDatastore.getDatastore().update(auxResult,
								uq.set("updates", auxResult.getUpdates() + 1));
						RepositoryDatastore.getDatastore().update(auxResult,
								uq.set("lastUpdate", new Date(System.currentTimeMillis())));
						edService.deletePendingOfInsertDocument(id, number);
						toReturn = new JsonResult(true, "Success", toAdd);
					} else
						toReturn = new JsonResult(false, "Error while saving file");
					// TODO: se un update del registro de la lista de
					// documetfiles en el documento con el path correcto
				} else
					toReturn = new JsonResult(false, "The file wasn't found in database");
			} else
				toReturn = new JsonResult(false, "Error while connecting to database");
		} catch (Exception e) {
			e.printStackTrace();
			toReturn = new JsonResult(false, "Error while saving document");
		}
		return toReturn;
	}

	@Override
	public ExpiringDocument nextNumber(UUID id) {
		int nextNumber = -1;
		ExpiringDocument result = null;
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> q = RepositoryDatastore.getDatastore().createQuery(Document.class);
				Document auxResult = q.field("id").equal(id).get();
				if (auxResult != null) {
					nextNumber = auxResult.getFiles().size() + 1;
					int lastPendingOfInsertDocument = edService.getLastPendingOfInsertDocument(id);
					if (lastPendingOfInsertDocument == 0)
						result = edService.addPendingofInsertDocument(id, nextNumber);
					else
						result = new ExpiringDocument(ExpiringDocumentErrorCode.FILE_LOCKED);
				} else
					result = new ExpiringDocument(ExpiringDocumentErrorCode.FILE_NOT_FOUND);
			} else
				result = new ExpiringDocument(ExpiringDocumentErrorCode.DATASTORE_NOT_AVAILABLE);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}
}
