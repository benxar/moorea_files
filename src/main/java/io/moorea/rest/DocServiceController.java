package io.moorea.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.moorea.model.JsonResult;
import io.moorea.service.PdfService;



@RestController
public class DocServiceController {

    @Autowired
    private PdfService pdfService;
   
    @RequestMapping(value = "/alive")
    public JsonResult getName() throws Exception {        
        return new JsonResult(true, "is Alive!");    	
    }
    
    
    @RequestMapping(value = "/htmlToPdf", method=RequestMethod.POST)
    public JsonResult htmlToPdf(@RequestBody String postPayload) throws Exception {        
    	InputStream is = new ByteArrayInputStream(postPayload.getBytes());    	
    	return pdfService.htmlToPdf(is);    	
    }

    

}
