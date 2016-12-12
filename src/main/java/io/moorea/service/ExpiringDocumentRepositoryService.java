package io.moorea.service;

import java.util.UUID;

import io.moorea.entity.ExpiringDocument;

public interface ExpiringDocumentRepositoryService {
	
	int getLastPendingOfInsertDocument(UUID docId);
	
	int checkExistence(UUID docId,int number);
	
	ExpiringDocument addPendingofInsertDocument(UUID docId,int number);

}
