package io.moorea.service;

import java.io.InputStream;

import io.moorea.model.JsonResult;

public interface PdfService {

	public JsonResult htmlToPdf(InputStream html);
	
	public JsonResult validatePdfFormat(String string);
	
	public JsonResult addDocument(InputStream html);
}
