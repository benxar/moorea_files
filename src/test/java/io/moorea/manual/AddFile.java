package io.moorea.manual;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Map;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;

public class AddFile {

	public static void main(String[] args) {

		String doc_origen = "/Users/juanmanuelcarrascal/Documents/Expedientes/NotaPedidoPersonal_sello.pdf";
		String doc_destino = "/Users/juanmanuelcarrascal/Documents/Expedientes/NotaPedidoPersonal_sello_attach.pdf";
		String doc_attach = "/Users/juanmanuelcarrascal/Documents/Expedientes/Personal Seleccionado.pdf";

		try {
			PdfReader reader = new PdfReader(doc_origen);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, baos);
			addAttachment(stamper.getWriter(), new File(doc_attach));
			MessageDigest md = MessageDigest.getInstance("MD5");
			try (InputStream is = Files.newInputStream(Paths.get(doc_attach));
			     DigestInputStream dis = new DigestInputStream(is, md)) 
			{
			  /* Read decorated stream (dis) to EOF as normal... */
			}
			byte[] digest = md.digest();
			
			System.out.println(Base64.encodeBytes(digest));
			Map<String, String> info = reader.getInfo();	
			info.put("Title", "Actuación: 024-99-817797454-790");
			info.put("Subject", "Proyecto de Resolución para la firma del Señor Subdirector Ejecutivo de Administración");
			info.put("Keywords", "Asignación AD-REFERENDUM");
			info.put("Creator", "Juan Manuel Carrascal");
			info.put("Author", "ANSES");
			info.put("[1] - " + new File(doc_attach).getName(),Base64.encodeBytes(digest));
			stamper.setMoreInfo(info);
			stamper.close();
			try (OutputStream outputStream = new FileOutputStream(doc_destino)) {
				baos.writeTo(outputStream);
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	public static void addAttachment(PdfWriter writer, File src) throws IOException {
		PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(writer, src.getAbsolutePath(), src.getName(), null);
		writer.addFileAttachment(src.getName().substring(0, src.getName().indexOf('.')), fs);
	}

	
}
