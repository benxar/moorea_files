package io.moorea.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.moorea.entity.Category;
import io.moorea.entity.Document;
import io.moorea.entity.Office;
import io.moorea.model.JsonResult;
import io.moorea.parser.IJsonParser;
import io.moorea.parser.impl.NewFileRequestParserImpl;
import io.moorea.parser.request.NewFileRequest;
import io.moorea.service.DocumentRepositoryService;
import io.moorea.service.PdfService;

@RestController
public class DocServiceController {

	final Morphia morphia = new Morphia();

	@Autowired
	private PdfService pdfService;

	// @Autowired
	// private BookService bookService;

	@Autowired
	private DocumentRepositoryService documentService;

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
			int nextNumber = documentService.nextNumber(id);
			if (nextNumber > 0)
				result = new JsonResult(true, "Success", nextNumber);
			else
				result = new JsonResult(false, "Error while retrieving next document number");
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
	public JsonResult managerGetDocumentFileByDocumentIdAndNumber(@PathVariable UUID id, @PathVariable String number)
			throws Exception {
		return documentService.getDocumentFileById(id, number);
	}

}
