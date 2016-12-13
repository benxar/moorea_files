package io.moorea.service.impl;

import java.util.UUID;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;

import io.moorea.entity.ExpiringDocument;
import io.moorea.enums.ExpiringDocumentErrorCode;
import io.moorea.persistence.RepositoryDatastore;
import io.moorea.service.ExpiringDocumentRepositoryService;

@Service
public class ExpiringDocumentRepositoryServiceImpl implements ExpiringDocumentRepositoryService {

	@Override
	public ExpiringDocumentErrorCode checkExistence(UUID docId, int number, UUID key) {
		ExpiringDocumentErrorCode result = ExpiringDocumentErrorCode.NO_ERROR;
		try {
			Query<ExpiringDocument> query = RepositoryDatastore.getDatastore().createQuery(ExpiringDocument.class);
			ExpiringDocument aux = query.field("parentDocument").equal(docId).field("number").equal(number).get();
			if (aux != null) {
				if (aux.getKey().compareTo(key) == 0)
					result = ExpiringDocumentErrorCode.NO_ERROR;
				else
					result = ExpiringDocumentErrorCode.INVALID_KEY;
			} else
				result = ExpiringDocumentErrorCode.FILE_NOT_FOUND;
		} catch (Exception e) {
			e.printStackTrace();
			result = ExpiringDocumentErrorCode.GENERAL_ERROR;
		}
		return result;
	}

	@Override
	public ExpiringDocument addPendingofInsertDocument(UUID docId, int number) {
		try {
			ExpiringDocument auxEd = new ExpiringDocument();
			auxEd.setParentDocument(docId);
			auxEd.setNumber(number);
			RepositoryDatastore.getDatastore().save(auxEd);
			return auxEd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getLastPendingOfInsertDocument(UUID docId) {
		int lastNumber = -1;
		try {
			Query<ExpiringDocument> query = RepositoryDatastore.getDatastore().createQuery(ExpiringDocument.class);
			ExpiringDocument auxEd = query.field("parentDocument").equal(docId).order("-number").get();
			if (auxEd != null)
				lastNumber = auxEd.getNumber();
			else
				lastNumber = 0;
		} catch (Exception e) {
			e.printStackTrace();
			lastNumber = -1;
		}
		return lastNumber;
	}

	@Override
	public boolean deletePendingOfInsertDocument(UUID docId, int number) {
		try {
			Query<ExpiringDocument> query = RepositoryDatastore.getDatastore().createQuery(ExpiringDocument.class);
			ExpiringDocument auxEd = query.field("parentDocument").equal(docId).order("-number").get();
			WriteResult result = RepositoryDatastore.getDatastore().delete(auxEd);
			return result.getN() > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
