package io.moorea.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import io.moorea.model.JsonResult;
import io.moorea.service.PdfService;

@Service
public class PdfServiceImpl implements PdfService {

	public JsonResult htmlToPdf(InputStream html) {		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, html);
			document.close();
			
			//tmp
			FileOutputStream fos = new FileOutputStream("/tmp/test.pdf");
			fos.write(baos.toByteArray());
			fos.close();
			
			return new JsonResult(true, "Ok", Base64.encodeBytes(baos.toByteArray()));
		} catch (DocumentException e) {			
			e.printStackTrace();
			return new JsonResult(false, e.getMessage());
		} catch (Exception e) {			
			e.printStackTrace();
			return new JsonResult(false, e.getMessage());
		}

	}
}
