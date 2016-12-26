package io.moorea.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import io.moorea.entity.Attachment;
import io.moorea.entity.Signer;
import io.moorea.model.JsonResult;
import io.moorea.parser.request.AttachToPdfRequest;

public interface PdfService {

	public JsonResult htmlToPdf(InputStream html);

	public JsonResult validatePdfFormat(String string);

	public JsonResult addDocument(AttachToPdfRequest req, boolean encrypted);

	public List<Signer> getSigners(String b64Pdf);
	
	public List<Attachment> getAttachments(String b64Pdf);

	public UUID getKey(String b64Pdf);
}
