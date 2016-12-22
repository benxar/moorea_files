package io.moorea.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfBorderArray;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class FormatUtil {

	public static ByteArrayOutputStream addFolioToImage(InputStream imageInpuStream, Integer folio) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(imageInpuStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics g = image.getGraphics();
		Font font = new Font("Arial", Font.BOLD, 22);
		g.setFont(font);
		g.setColor(Color.GRAY);
		g.drawString(String.valueOf(folio), 70, 95);
		g.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos;
	}

	public static ByteArrayOutputStream addImageToPDF(PdfReader reader, Image img, Map<String,String> info)
			throws IOException, DocumentException {
		//int n = reader.getNumberOfPages();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PdfStamper stamper = new PdfStamper(reader, baos);
		if (info != null) {
			Map<String,String> auxInfo = reader.getInfo();
			auxInfo.putAll(info);
			stamper.setMoreInfo(auxInfo);
		}
		float x = 10;
		float y = 650;
		//float w = img.getWidth();
		//float h = img.getHeight();
		img.setAbsolutePosition(reader.getPageSize(1).getWidth() - 200, reader.getPageSize(1).getHeight() - 30);
		stamper.getOverContent(1).addImage(img);
		Rectangle linkLocation = new Rectangle(x, y);
		PdfDestination destination = new PdfDestination(PdfDestination.FIT);
		PdfAnnotation link = PdfAnnotation.createLink(stamper.getWriter(), linkLocation, PdfAnnotation.HIGHLIGHT_NONE,
				reader.getNumberOfPages(), destination);
		link.setBorder(new PdfBorderArray(0, 0, 0));
		stamper.addAnnotation(link, 1);
		stamper.close();
		
		//tmp
		FileOutputStream fos = new FileOutputStream("/tmp/test.pdf");
		fos.write(baos.toByteArray());
		fos.close();
		
		return baos;
	}

	public static BufferedImage convertTextToGraphic(String text, Font font) {

		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();

		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		g2d.dispose();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		g2d = img.createGraphics();
	
		g2d.setFont(font);
		g2d.setColor(Color.GRAY);
		g2d.drawString(text, 0, fm.getAscent());
		g2d.dispose();
		return img;
	}
}
