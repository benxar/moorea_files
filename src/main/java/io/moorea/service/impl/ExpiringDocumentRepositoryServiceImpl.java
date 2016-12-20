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
	public ExpiringDocument checkExistence(UUID key) {
		ExpiringDocument result = null;
		try {
			Query<ExpiringDocument> query = RepositoryDatastore.getDatastore().createQuery(ExpiringDocument.class);
			ExpiringDocument aux = query.field("key").equal(key).get();
			if (aux != null) {
				result = aux;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
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
