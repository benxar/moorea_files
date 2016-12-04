package io.moorea.manual;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class AddTextToImage {

	public static void main(String[] args) throws Exception {
	    final BufferedImage image = ImageIO.read(new File ("/Users/juanmanuelcarrascal/Documents/Expedientes/sello.png"));

	    Graphics g = image.getGraphics();
	    Font font = new Font("Arial", Font.BOLD, 22);
	    g.setFont(font);
	    g.setColor(Color.GRAY);
	    g.drawString("3", 70, 95);
	    g.dispose();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(image, "png", baos);
	    
	}
	
	public static ByteArrayOutputStream addFolioToImage(InputStream imageInpuStream, Integer folio){
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
	
	
	
}
