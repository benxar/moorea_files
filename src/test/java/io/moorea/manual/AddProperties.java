package io.moorea.manual;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class AddProperties {

	public static void main(String[] args) {
		String doc_origen = "/Users/juanmanuelcarrascal/Documents/Expedientes/NotaPedidoPersonal_sello_attach_signed.pdf";
		String doc_destino = "/Users/juanmanuelcarrascal/Documents/Expedientes/NotaPedidoPersonal_sello_attach_signed_adulte.pdf";

		PdfReader reader;
		try {
			reader = new PdfReader(doc_origen);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(doc_destino));
			Map<String, String> info = reader.getInfo();
			info.put("Title", "Actuación: 024-99-817797455-790");
			info.put("Subject", "Proyecto de Resolución para la firma del Señor Subdirector Ejecutivo de Administración");
			info.put("Keywords", "Asignación AD-REFERENDUM");
			info.put("Creator", "Juan Manuel Carrascal");
			info.put("Author", "ANSES");

			stamper.setMoreInfo(info);
			stamper.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
