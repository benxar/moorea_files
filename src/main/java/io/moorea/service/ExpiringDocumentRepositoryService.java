package io.moorea.service;

import java.util.UUID;

public interface ExpiringDocumentRepositoryService {
	
	int getLastPendingOfInsertDocument(UUID docId);
	
	int checkExistence(UUID docId,int number);
	
	boolean addPendingofInsertDocument(UUID docId,int number);

}
