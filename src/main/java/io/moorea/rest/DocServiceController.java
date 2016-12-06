package io.moorea.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.moorea.entity.Category;
import io.moorea.entity.Document;
import io.moorea.entity.Office;
import io.moorea.model.JsonResult;
import io.moorea.parser.IJsonParser;
import io.moorea.parser.impl.NewFileRequestParserImpl;
import io.moorea.parser.request.NewFileRequest;
import io.moorea.persistence.DocumentRepository;
import io.moorea.service.BookService;
import io.moorea.service.PdfService;



@RestController
public class DocServiceController {

	final Morphia morphia = new Morphia();
	
    @Autowired
    private PdfService pdfService;
   
    @Autowired
    private BookService bookService;

    
    @RequestMapping(value = "/alive")
    public JsonResult getName() throws Exception {        
        return new JsonResult(true, "is Alive!");    	
    }
    

    @RequestMapping(value = "/htmlToPdf", method=RequestMethod.POST)
    public JsonResult htmlToPdf(@RequestBody String postPayload) throws Exception {        
    	InputStream is = new ByteArrayInputStream(postPayload.getBytes());    	
    	return pdfService.htmlToPdf(is);    	
    }
    
    
    @RequestMapping(value = "/api/files/next_number/{id}", method=RequestMethod.POST)        
    public JsonResult next_number(@PathVariable String id, @RequestBody String postPayload) throws Exception {
    	
    	try{
    		JsonParser parser = new JsonParser(); 
    		JsonObject obj = parser.parse(postPayload).getAsJsonObject();    		
    		//Validate pdf base64 file
    		JsonResult jsonResult = pdfService.validatePdfFormat(obj.get("b64").getAsString());
    		if (!jsonResult.getSuccess()){
    			return jsonResult;
    		}
    		//Get Document w temp number
    		jsonResult = bookService.getNextNumber(jsonResult.getObject(), obj.get("number").getAsString());
    		
    		System.out.println(jsonResult.getSuccess());
    		return jsonResult;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return new JsonResult(false, "Existe un error en los parametros");   	
    }
    
    @RequestMapping(value = "/api/files/manager", method=RequestMethod.POST)        
    public JsonResult managerPost(@RequestBody String postPayload) throws Exception {
    	String error = "";
    	Key<Document> generatedKey = null;
    	boolean hayError = false;
    	try{
    		IJsonParser parser = new NewFileRequestParserImpl();
    		NewFileRequest req = (NewFileRequest) parser.parseJson(postPayload);
    		Document toPersist = new Document();
    		toPersist.generateRandomId();
    		toPersist.setCategory(new Category(req.getCategoryId(), req.getCategoryText()));
    		toPersist.setOffice(new Office(req.getOfficeId(), req.getOfficeText()));
    		toPersist.setType(req.getType());
    		toPersist.setYear(req.getYear());
    		try {
    			generatedKey = DocumentRepository.getInstance().save(toPersist);
			} catch (Exception e) {
				e.printStackTrace();
				error = "Error al guardar el documento";
				hayError=true;
			}
    		if(!hayError){
        		JsonResult result = new JsonResult(true, "funciono",generatedKey);
        		return result;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		error = "Existe un error en los parametros";
    	}
    	return new JsonResult(false, error);   	
    }
    
    @RequestMapping(value = "/api/files/manager/{type}/{page}/{limit}/{order_filed}/{order_direction}", method=RequestMethod.GET)
    public JsonResult managerGet(@PathVariable String type,@PathVariable int page,@PathVariable int limit,
    		@PathVariable String order_filed,@PathVariable String order_direction) throws Exception{
    	return null;
    }
}
