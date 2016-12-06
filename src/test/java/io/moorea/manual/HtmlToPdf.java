package io.moorea.manual;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HtmlToPdf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream is = new ByteArrayInputStream("<h1> Hello Word </h1>".getBytes());

		htmlToPdf(is, "/tmp/test.pdf");
	}

	public static void htmlToPdf(InputStream html, String file) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, html);
			document.close();
			String result = Base64.encodeBytes(baos.toByteArray());
			System.out.println(result);

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
