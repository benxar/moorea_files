package io.moorea.manual;
/**
 * Example written by Bruno Lowagie.
 * This example will only work with iText 5.5.6 and higher (you also need the xtra package).
 */

 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.codec.Base64;

import io.moorea.util.FormatUtil;

 
public class AddDocumentToFile {
 
	
 
    /**
     * Main method.
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException, DocumentException {
        LoggerFactory.getInstance().setLogger(new SysoLogger());
        
        String doc_origen = "/Users/juanmanuelcarrascal/Documents/Expedientes/ModeloAltaPersona/3 - NotaPedidoPersonal.pdf";
    	String doc_destino = "/Users/juanmanuelcarrascal/Documents/Expedientes/ModeloAltaPersona/3 - NotaPedidoPersonal_folio.pdf";
    	String img = "/Users/juanmanuelcarrascal/Documents/Expedientes/sello.png";
    	
        
    	Path path = Paths.get(doc_origen);
    	byte[] dataInput = Files.readAllBytes(path);
    	
    	Map<String, String> info = new HashMap<String, String>();	
		info.put("Title", "Actuación: 024-99-817797454-790");
		info.put("Subject", "Proyecto de Resolución para la firma del Señor Subdirector Ejecutivo de Administración");
		info.put("Keywords", "Asignación AD-REFERENDUM");
		info.put("Creator", "Juan Manuel Carrascal");
		info.put("Author", "a014128 - ANSES");
        
        
    	byte[] imgBytes = Files.readAllBytes(Paths.get(img));
        
        
        
        
        //tmp
		FileOutputStream fos = new FileOutputStream(doc_destino);
		fos.write(Base64.decode(addDocumentToFile(Base64.encodeBytes(dataInput),info,2,imgBytes)));
		fos.close();
    }
    
    
    public static String addDocumentToFile(String base64Doc, Map<String, String> properties, Integer folio, byte[] waterMark ){
    	PdfReader reader;
		try {			
			// Leer Doc origen
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			reader = new PdfReader(Base64.decode(base64Doc));	
			PdfStamper stamper = new PdfStamper(reader, baos);
			
			// Agregar headers
			stamper.setMoreInfo(properties);
			
			// Agregar Folio
	        Image img = Image.getInstance(FormatUtil.addFolioToImage(new ByteArrayInputStream(waterMark), folio).toByteArray());
	        float w = img.getScaledWidth();
	        float h = img.getScaledHeight();
	        // transparency
	        PdfGState gs1 = new PdfGState();
	        gs1.setFillOpacity(0.5f);
	        // properties
	        PdfContentByte over;
	        //Rectangle pagesize;
	        //float x, y;
	        
	        // Agregar nueva propiedad de folio 
	        properties.put("folio", String.valueOf(folio));
	        
			//pagesize = reader.getPageSizeWithRotation(1);
            //x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            //y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = stamper.getOverContent(1);
            over.saveState();
            over.setGState(gs1);                         
            over.addImage(img, w-55, 0, 0, h-55, 510, 690);
            over.restoreState();			
			stamper.close();
			reader.close();
			
			// Persistir Documento
			
			return Base64.encodeBytes(baos.toByteArray());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    	
    	
    	
    	
    	
    	
    }
}