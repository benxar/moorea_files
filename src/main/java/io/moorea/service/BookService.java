package io.moorea.service;

import io.moorea.entity.Document;
import io.moorea.entity.ExpiringDocument;
import io.moorea.model.JsonResult;

public interface BookService {
	JsonResult getNextNumber(String b64, ExpiringDocument number, Document parentDocument);
}
