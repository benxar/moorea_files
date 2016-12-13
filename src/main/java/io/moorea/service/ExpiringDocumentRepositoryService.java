package io.moorea.service;

import java.util.UUID;

import io.moorea.entity.ExpiringDocument;
import io.moorea.enums.ExpiringDocumentErrorCode;

public interface ExpiringDocumentRepositoryService {
	
	int getLastPendingOfInsertDocument(UUID docId);
	
	ExpiringDocumentErrorCode checkExistence(UUID docId,int number, UUID key);
	
	ExpiringDocument addPendingofInsertDocument(UUID docId,int number);
	
	boolean deletePendingOfInsertDocument(UUID docId, int number);
}
