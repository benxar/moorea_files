package io.moorea.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.codec.Base64;

import io.moorea.model.JsonResult;
import io.moorea.service.BookService;
import io.moorea.util.FormatUtil;

@Service
public class BookServiceImpl implements BookService {

	
	
	public JsonResult getNextNumber(Object object, String number) {
		//Create Image Number		
		BufferedImage image = FormatUtil.convertTextToGraphic(number, new Font("Arial", Font.BOLD, 26));
		
		Image pdfImage = null;
		try {
			pdfImage = Image.getInstance( image, Color.white);
		} catch (BadElementException e1) {
			e1.printStackTrace();
			return new JsonResult(false, e1.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
			return new JsonResult(false, e1.getMessage());
		}
		//Add image number to PDF
		PdfReader pdfReader =  (PdfReader) object;
		try {	
			ByteArrayOutputStream baos = FormatUtil.addImageToPDF(pdfReader,pdfImage); 
			//tmp
			FileOutputStream fos = new FileOutputStream("/tmp/test.pdf");
			fos.write(baos.toByteArray());
			fos.close();
			
			return new JsonResult(true,"Ok", Base64.encodeBytes(baos.toByteArray()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JsonResult(false, "Error");
	}

	
	
}
