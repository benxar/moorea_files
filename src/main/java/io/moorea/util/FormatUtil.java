package io.moorea.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class FormatUtil {
	
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
