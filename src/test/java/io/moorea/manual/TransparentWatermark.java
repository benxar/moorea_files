package io.moorea.manual;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfBorderArray;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public class TransparentWatermark {
 
    public static final String SRC = "/Users/juanmanuelcarrascal/Documents/resolucion.pdf";
    public static final String DEST = "/Users/juanmanuelcarrascal/Documents/Expedientes/NotaPedidoPersonal_sello.pdf";
    public static final String IMG = "/Users/juanmanuelcarrascal/Documents/Expedientes/sello.png";
    
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TransparentWatermark().manipulatePdf(SRC, DEST);
    }
 
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
    	PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        Image img = Image.getInstance(IMG);
        float x = 10;
        float y = 650;
        float w = img.getScaledWidth();
        float h = img.getScaledHeight();
        System.out.println(reader.getPageSize(1).getWidth() - 100);
        img.setAbsolutePosition(reader.getPageSize(1).getWidth() - 150, reader.getPageSize(1).getHeight() - 150);
        stamper.getOverContent(1).addImage(img);
        Rectangle linkLocation = new Rectangle(x, y, x + w, y + h);
        PdfDestination destination = new PdfDestination(PdfDestination.FIT);
        PdfAnnotation link = PdfAnnotation.createLink(stamper.getWriter(),
                linkLocation, PdfAnnotation.HIGHLIGHT_INVERT,
                reader.getNumberOfPages(), destination);
        link.setBorder(new PdfBorderArray(0, 0, 0));
        stamper.addAnnotation(link, 1);
        stamper.close();
    }
}