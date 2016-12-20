package io.moorea.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
import io.moorea.entity.Signer;
import io.moorea.enums.ExpiringDocumentErrorCode;
import io.moorea.model.JsonResult;
import io.moorea.model.JsonResultList;
import io.moorea.parser.IJsonParser;
import io.moorea.parser.impl.ConvertToPdfRequestParserImpl;
import io.moorea.parser.impl.FilePostRequestParserImpl;
import io.moorea.parser.impl.GetSignersRequestParserImpl;
import io.moorea.parser.impl.NewFileRequestParserImpl;
import io.moorea.parser.impl.NextNumberRequestParserImpl;
import io.moorea.parser.impl.ValidatePdfRequestParserImpl;
import io.moorea.parser.request.ConvertToPdfRequest;
import io.moorea.parser.request.FilePostRequest;
import io.moorea.parser.request.GetSignersRequest;
import io.moorea.parser.request.NewFileRequest;
import io.moorea.parser.request.NextNumberRequest;
import io.moorea.parser.request.ValidatePdfRequest;
import io.moorea.service.BookService;
import io.moorea.service.DocumentRepositoryService;
import io.moorea.service.ExpiringDocumentRepositoryService;
import io.moorea.service.PdfService;

@RestController
public class DocServiceController {

	@Autowired
	private PdfService pdfService;

	@Autowired
	private BookService bookService;

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
			IJsonParser parser = new NextNumberRequestParserImpl();
			NextNumberRequest req = (NextNumberRequest) parser.parseJson(postPayload);
			if(pdfService.validatePdfFormat(req.getB64()).getSuccess()){
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
						JsonResult auxRes = null;
						auxRes = bookService.getNextNumber(req.getB64(), nextDocument.getNumber());
						if (auxRes.getSuccess()) {
							ExpiringDocument auxED = (ExpiringDocument) result.getObject();
							auxED.setB64(auxRes.getObject().toString());
							result.setObject(auxED);
						}
						break;
					default:
						break;
					}
				} else
					result = new JsonResult(false, "General error while retrieving next number");
			}else
				result = new JsonResult(false, "The file must be a pdf");
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
			@RequestBody String postPayload) throws Exception {
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

	@RequestMapping(value = "/api/files/helpers/atach_doc_to_pdf", method = RequestMethod.POST)
	public JsonResult attachToPdf(@RequestBody String postPayload) throws Exception {
		return null;
	}

	@RequestMapping(value = "/api/files/helpers/convert_to_pdf", method = RequestMethod.POST)
	public JsonResult convertToPdf(@RequestBody String postPayload) throws Exception {
		IJsonParser parser = new ConvertToPdfRequestParserImpl();
		ConvertToPdfRequest req = null;
		InputStream is = null;
		try {
			req = (ConvertToPdfRequest) parser.parseJson(postPayload);
			is = new ByteArrayInputStream(req.getHtlm().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (req != null && is != null)
			return pdfService.htmlToPdf(is);
		else
			return new JsonResult(false, "There's an error in parameters");
	}

	@RequestMapping(value = "/api/files/helpers/validations/type_of_file", method = RequestMethod.POST)
	public JsonResult typeOfFile(@RequestBody String postPayload) throws Exception {
		IJsonParser parser = new ValidatePdfRequestParserImpl();
		ValidatePdfRequest req = null;
		try {
			req = (ValidatePdfRequest) parser.parseJson(postPayload);
			if (req != null)
				return new JsonResult(true,"Success", pdfService.validatePdfFormat(req.getB64()));
			else
				return new JsonResult(false, "There's an error in parameters");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "Error while validating file");
		}
	}

	@RequestMapping(value = "/api/files/helpers/validations/signers", method = RequestMethod.POST)
	public JsonResultList getSigners(@RequestBody String postPayload) throws Exception {
		JsonResultList toReturn = null;
		GetSignersRequest req = null;
		List<Signer> signers = null;
		IJsonParser parser = new GetSignersRequestParserImpl();
		try {
			req = (GetSignersRequest) parser.parseJson(postPayload);
			if (req != null)
				signers = pdfService.getSigners(req.getB64());
			else
				toReturn = new JsonResultList(false, "There's an error in parameters", null);
			if (signers != null)
				toReturn = new JsonResultList(true, "Success", new ArrayList<Object>(signers), (long) signers.size());
			else
				toReturn = new JsonResultList(false, "There was an error while fetching the signers of the document",
						null);
		} catch (Exception e) {
			e.printStackTrace();
			toReturn = new JsonResultList(false, "Unexpected error while fetching signers", null);
		}
		return toReturn;
	}
}