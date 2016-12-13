package io.moorea.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.moorea.entity.Category;
import io.moorea.entity.Document;
import io.moorea.entity.ExpiringDocument;
import io.moorea.entity.Office;
import io.moorea.enums.ExpiringDocumentErrorCode;
import io.moorea.model.JsonResult;
import io.moorea.parser.IJsonParser;
import io.moorea.parser.impl.FilePostRequestParserImpl;
import io.moorea.parser.impl.NewFileRequestParserImpl;
import io.moorea.parser.request.FilePostRequest;
import io.moorea.parser.request.NewFileRequest;
import io.moorea.service.DocumentRepositoryService;
import io.moorea.service.ExpiringDocumentRepositoryService;
import io.moorea.service.PdfService;

@RestController
public class DocServiceController {

	@Autowired
	private PdfService pdfService;

	// @Autowired
	// private BookService bookService;

	@Autowired
	private DocumentRepositoryService documentService;

	@Autowired
	private ExpiringDocumentRepositoryService expDocService;

	@RequestMapping(value = "/alive")
	public JsonResult getName() throws Exception {
		return new JsonResult(true, "is Alive!");
	}

	@RequestMapping(value = "/htmlToPdf", method = RequestMethod.POST)
	public JsonResult htmlToPdf(@RequestBody String postPayload) throws Exception {
		InputStream is = new ByteArrayInputStream(postPayload.getBytes());
		return pdfService.htmlToPdf(is);
	}

	@RequestMapping(value = "/api/files/next_number/{id}", method = RequestMethod.POST)
	public JsonResult next_number(@PathVariable UUID id, @RequestBody String postPayload) throws Exception {
		// comentado por ahora
		/*
		 * try { JsonParser parser = new JsonParser(); JsonObject obj =
		 * parser.parse(postPayload).getAsJsonObject(); // Validate pdf base64
		 * file JsonResult jsonResult =
		 * pdfService.validatePdfFormat(obj.get("b64").getAsString()); if
		 * (!jsonResult.getSuccess()) { return jsonResult; } // Get Document w
		 * temp number jsonResult =
		 * bookService.getNextNumber(jsonResult.getObject(),
		 * obj.get("number").getAsString());
		 * 
		 * System.out.println(jsonResult.getSuccess()); return jsonResult; }
		 * catch (Exception e) { e.printStackTrace(); } return new
		 * JsonResult(false, "Existe un error en los parametros");
		 */
		JsonResult result = null;
		try {
			ExpiringDocument nextDocument = documentService.nextNumber(id);
			if (nextDocument != null) {
				switch (nextDocument.getErrorCode()) {
				case DATASTORE_NOT_AVAILABLE:
					result = new JsonResult(false, "Datastore not available");
					break;
				case FILE_LOCKED:
					result = new JsonResult(false, "The file is locked: another document is pending of insert");
					break;
				case FILE_NOT_FOUND:
					result = new JsonResult(false, "The file wasn't found");
					break;
				case NO_ERROR:
					result = new JsonResult(true, "Success", nextDocument);
					break;
				default:
					break;
				}
			} else
				result = new JsonResult(false, "General error while retrieving next number");
		} catch (Exception e) {
			e.printStackTrace();
			result = new JsonResult(false, "There's an error in parameters");
		}
		return result;
	}

	@RequestMapping(value = "/api/files/manager", method = RequestMethod.POST)
	public JsonResult managerPostDocument(@RequestBody String postPayload) throws Exception {
		String error = "";
		UUID generatedKey = null;
		boolean hayError = false;
		try {
			IJsonParser parser = new NewFileRequestParserImpl();
			NewFileRequest req = (NewFileRequest) parser.parseJson(postPayload);
			Document toPersist = new Document();
			toPersist.generateRandomId();
			toPersist.setCategory(new Category(req.getCategoryId(), req.getCategoryText()));
			toPersist.setOffice(new Office(req.getOfficeId(), req.getOfficeText()));
			toPersist.setType(req.getType());
			toPersist.setYear(req.getYear());
			try {
				generatedKey = documentService.save(toPersist);
				if (generatedKey == null) {
					hayError = true;
					error = "There was an error while saving the file";
				}
			} catch (Exception e) {
				e.printStackTrace();
				error = "Error while saving file";
				hayError = true;
			}
			if (!hayError) {
				JsonResult result = new JsonResult(true, "Success", generatedKey);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			error = "There's an error in parameters";
		}
		return new JsonResult(false, error);
	}

	@RequestMapping(value = "/api/files/manager/{type}/{page}/{limit}/{order_filed}/{order_direction}", method = RequestMethod.GET)
	public JsonResult managerGet(@PathVariable String type, @PathVariable int page, @PathVariable int limit,
			@PathVariable String order_filed, @PathVariable String order_direction) throws Exception {
		return documentService.getDocuments(type, page, limit, order_filed, order_direction);
	}

	@RequestMapping(value = "/api/files/manager/{id}", method = RequestMethod.GET)
	public JsonResult managerGetById(@PathVariable UUID id) throws Exception {
		return documentService.getDocumentById(id);
	}

	@RequestMapping(value = "/api/files/manager/{id}/{number}", method = RequestMethod.GET)
	public JsonResult managerGetDocumentFileByDocumentIdAndNumber(@PathVariable UUID id, @PathVariable int number)
			throws Exception {
		return documentService.getDocumentFileById(id, number);
	}

	@RequestMapping(value = "/api/files/manager/{id}/{number}/{key}", method = RequestMethod.POST)
	public JsonResult managerPostDocumentFile(@PathVariable UUID id, @PathVariable int number, @PathVariable UUID key,
			@RequestBody String postPayload) {
		JsonResult result = null;
		ExpiringDocumentErrorCode tempDoc = ExpiringDocumentErrorCode.NO_ERROR;
		try {
			tempDoc = expDocService.checkExistence(id, number, key);
			switch (tempDoc) {
			case NO_ERROR:
				IJsonParser parser = new FilePostRequestParserImpl();
				FilePostRequest req = (FilePostRequest) parser.parseJson(postPayload);
				result = documentService.saveDocumentFile(id, number, key, req);
				break;
			case DATASTORE_NOT_AVAILABLE:
				result = new JsonResult(false, "Datastore not available");
				break;
			case FILE_LOCKED:
				result = new JsonResult(false, "The file is locked: another document is pending of insert");
				break;
			case FILE_NOT_FOUND:
				result = new JsonResult(false, "The file wasn't found");
				break;
			case INVALID_KEY:
				result = new JsonResult(false, "The key provided is invalid for the given file and number");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new JsonResult(false, "There's an error in parameters");
		}
		return result;
	}

}