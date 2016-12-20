package io.moorea.service;

import java.util.UUID;

import io.moorea.entity.ExpiringDocument;

public interface ExpiringDocumentRepositoryService {
	
	int getLastPendingOfInsertDocument(UUID docId);
	
	ExpiringDocument checkExistence(UUID key);
	
	ExpiringDocument addPendingofInsertDocument(UUID docId,int number);
	
	boolean deletePendingOfInsertDocument(UUID docId, int number);
}
