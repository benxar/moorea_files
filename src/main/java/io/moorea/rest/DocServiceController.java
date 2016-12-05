package io.moorea.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.moorea.model.JsonResult;
import io.moorea.service.BookService;
import io.moorea.service.PdfService;



@RestController
public class DocServiceController {

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

}
