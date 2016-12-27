package io.moorea.service;

import java.util.List;
import java.util.UUID;

import io.moorea.entity.ExpiringDocument;
import io.moorea.entity.Signer;

public interface ExpiringDocumentRepositoryService {

	int getLastPendingOfInsertDocument(UUID docId);

	ExpiringDocument checkExistence(UUID key);

	ExpiringDocument addPendingofInsertDocument(UUID docId, int number, List<Signer> lSigners);

	boolean deletePendingOfInsertDocument(UUID docId, int number);
}
