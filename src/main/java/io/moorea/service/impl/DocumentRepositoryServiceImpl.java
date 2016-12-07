package io.moorea.service.impl;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;

import io.moorea.entity.*;
import io.moorea.model.JsonResult;
import io.moorea.persistence.DocumentRepositoryDatastore;
import io.moorea.service.DocumentRepositoryService;

@Service
public class DocumentRepositoryServiceImpl implements DocumentRepositoryService {

	public UUID save(Document document) {
		if (DocumentRepositoryDatastore.getDatastore() != null)
			return (UUID) DocumentRepositoryDatastore.getDatastore().save(document).getId();
		else
			return null;
	}

	@SuppressWarnings("deprecation")
	public JsonResult getDocuments(String type, int page, int limit, String order_filed, String order_direction) {
		JsonResult toReturn = null;
		try {
			if (DocumentRepositoryDatastore.getDatastore() != null) {
				Query<Document> query = DocumentRepositoryDatastore.getDatastore().createQuery(Document.class);
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

	public JsonResult getDocumentById(UUID id) {
		JsonResult toReturn = null;
		try {
			if (DocumentRepositoryDatastore.getDatastore() != null) {
				Query<Document> query = DocumentRepositoryDatastore.getDatastore().createQuery(Document.class);
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

	public JsonResult getDocumentFileById(UUID id, String fileId) {
		try {
			if (DocumentRepositoryDatastore.getDatastore() != null) {
				Query<Document> q = DocumentRepositoryDatastore.getDatastore().createQuery(Document.class);
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

	public JsonResult saveDocumentFile(UUID id, int number, byte[] toSave) {
		JsonResult toReturn = null;
		try {
			if (DocumentRepositoryDatastore.getDatastore() != null) {
				Query<Document> q = DocumentRepositoryDatastore.getDatastore().createQuery(Document.class);
				Document auxResult = q.field("id").equal(id).get();
				if (auxResult != null) {

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
}
