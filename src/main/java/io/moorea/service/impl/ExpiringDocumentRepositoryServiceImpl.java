package io.moorea.service.impl;

import java.util.UUID;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Service;

import io.moorea.entity.ExpiringDocument;
import io.moorea.persistence.RepositoryDatastore;
import io.moorea.service.ExpiringDocumentRepositoryService;

@Service
public class ExpiringDocumentRepositoryServiceImpl implements ExpiringDocumentRepositoryService {

	@Override
	public int checkExistence(UUID docId, int number) {
		try {
			Query<ExpiringDocument> query = RepositoryDatastore.getDatastore().createQuery(ExpiringDocument.class);
			return query.field("parentDocument").equal(docId).field("number").equal(number).get().equals(null) ? 0 : 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean addPendingofInsertDocument(UUID docId, int number) {
		try {
			ExpiringDocument auxEd = new ExpiringDocument();
			auxEd.setParentDocument(docId);
			auxEd.setNumber(number);
			RepositoryDatastore.getDatastore().save(auxEd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

}
