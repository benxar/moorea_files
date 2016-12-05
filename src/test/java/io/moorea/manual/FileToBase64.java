package io.moorea.manual;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.codec.binary.Base64;

import com.itextpdf.text.pdf.PdfReader;

public class FileToBase64 {

	public static void main(String[] args) {
		
		Path path = Paths.get("/Users/juanmanuelcarrascal/Documents/resolucion.pdf");
		byte[] data;
		try {
			data = Files.readAllBytes(path);
			System.out.println(Base64.encodeBase64String(data));
			
			
			    
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	

	}

}
