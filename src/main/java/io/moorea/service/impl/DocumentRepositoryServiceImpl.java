package io.moorea.service.impl;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.moorea.entity.*;
import io.moorea.model.JsonResult;
import io.moorea.persistence.RepositoryDatastore;
import io.moorea.service.DocumentRepositoryService;
import io.moorea.service.ExpiringDocumentRepositoryService;

@Service
public class DocumentRepositoryServiceImpl implements DocumentRepositoryService {
	@Autowired
	private ExpiringDocumentRepositoryService edService;

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

	@Override
	public JsonResult getDocumentFileById(UUID id, String fileId) {
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> q = RepositoryDatastore.getDatastore().createQuery(Document.class);
				Document auxResult = q.field("id").equal(id).field("files.doc_id").equal(fileId).project("files", true)
						.get();
				if (auxResult != null)
					for (DocumentFile aux : auxResult.getFiles())
						if (aux.getDoc_id() == fileId)
							return new JsonResult(true, "Success", aux);
				return new JsonResult(false, "No result was found");
			} else
				return new JsonResult(false, "Error while connecting to database");
		} catch (Exception e) {
			return new JsonResult(false, "Error while performing search");
		}
	}

	@Override
	public JsonResult saveDocumentFile(UUID id, int number, byte[] toSave) {
		JsonResult toReturn = null;
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> q = RepositoryDatastore.getDatastore().createQuery(Document.class);
				Document auxResult = q.field("id").equal(id).get();
				if (auxResult != null) {
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
	public int nextNumber(UUID id) {
		int result = -1;
		try {
			if (RepositoryDatastore.getDatastore() != null) {
				Query<Document> q = RepositoryDatastore.getDatastore().createQuery(Document.class);
				Document auxResult = q.field("id").equal(id).get();
				if (auxResult != null) {
					result = auxResult.getFiles().size() + 1;
					int lastPendingOfInsertDocument = edService.getLastPendingOfInsertDocument(id);
					if (result <= lastPendingOfInsertDocument)
						result = lastPendingOfInsertDocument + 1;
				} else
					result = -1;
			} else
				result = -1;
		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
}
