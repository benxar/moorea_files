package io.moorea.service;

import io.moorea.model.JsonResult;

public interface BookService {
	JsonResult getNextNumber(String b64, int number);
}
