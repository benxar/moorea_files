package io.moorea.service;

import java.io.InputStream;

import io.moorea.model.JsonResult;

public interface PdfService {

	public JsonResult htmlToPdf(InputStream html);
}
