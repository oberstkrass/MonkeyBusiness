package com.ftg.qa.poc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFBox {
	
	public static void main(String[] args) {
		System.out.println("Create Simple PDF file with blank Page");
		
		String fileName = "HelloWorld.pdf";
		
		try {
			PDDocument doc = new PDDocument();
			PDPage helloWorld = new PDPage();
			doc.addPage(helloWorld);
			
			PDPageContentStream content = new PDPageContentStream(doc, helloWorld);
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 18);
			content.newLineAtOffset(100, 700);
			content.showText("Hello World");
			content.endText();
			content.addRect(0, 0, 100, 100);
			content.fill();
			
			
			PDImageXObject image = PDImageXObject.createFromFile("C:\\Users\\Oliver Sorowka\\Documents\\Sample.jpg", doc);
			content.drawImage(image, 0, 800-image.getHeight()/2, image.getWidth()/2, image.getHeight()/2);
			//content.drawImage(image, 612 - image.getWidth(), 800 - image.getHeight());
			//image.setHeight(image.getHeight()/2); image.setWidth(image.getWidth()/2);
			content.drawImage(image, 0, 0);//850-image.getHeight());
			
			content.close();
			
			PDPage second = new PDPage();
			doc.addPage(second);
			content = new PDPageContentStream(doc, second);
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 24);
			content.newLineAtOffset(0, 150);
			content.showText("This is just a small test");
			content.newLineAtOffset(0, -100);			
			content.showText("Lets continue");
			content.endText();
			showTextInMultipleLines("This is a test. Lets see if the multiple row thing works. I just need to put a whole fucking lot of text here. Dunno, i think this should do!", content, second);
			content.close();
			
			PDPage third = new PDPage();
			doc.addPage(third);
			content = new PDPageContentStream(doc, third);
			
			drawTable(third, content, 500, 10, new String[][] {{ "a", "b", "1" },
																{ "c", "d", "2" },
																{ "e", "f", "3" },
																{ "g", "h", "4" }, 
												                { "i", "j", "5" }});
			content.close();
			
			doc.save(fileName);
			
			doc.close();
			System.out.println("Your file created in: " + System.getProperty("user.dir"));
		} catch(IOException e) {
			e.printStackTrace();	
		}
		
		
	}
	
	
	/**
	 * 
	 * @param text
	 * @param stream
	 * @param page
	 * @throws IOException
	 */
	public static void showTextInMultipleLines(String text, PDPageContentStream stream, PDPage page) throws IOException {
		PDFont pdfFont = PDType1Font.HELVETICA;
	    float fontSize = 25;
	    float leading = 1.5f * fontSize;

	    PDRectangle mediabox = page.getMediaBox();
	    float margin = 72;
	    float width = mediabox.getWidth() - 2*margin;
	    float startX = mediabox.getLowerLeftX() + margin;
	    float startY = mediabox.getUpperRightY() - margin;
	    
	    List<String> lines = new ArrayList<String>();
	    int lastSpace = -1;
	    while (text.length() > 0)
	    {
	        int spaceIndex = text.indexOf(' ', lastSpace + 1);
	        if (spaceIndex < 0)
	            spaceIndex = text.length();
	        String subString = text.substring(0, spaceIndex);
	        float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
	        System.out.printf("'%s' - %f of %f\n", subString, size, width);
	        if (size > width)
	        {
	            if (lastSpace < 0)
	                lastSpace = spaceIndex;
	            subString = text.substring(0, lastSpace);
	            lines.add(subString);
	            text = text.substring(lastSpace).trim();
	            System.out.printf("'%s' is line\n", subString);
	            lastSpace = -1;
	        }
	        else if (spaceIndex == text.length())
	        {
	            lines.add(text);
	            System.out.printf("'%s' is line\n", text);
	            text = "";
	        }
	        else
	        {
	            lastSpace = spaceIndex;
	        }
	    }

	    stream.beginText();
	    stream.setFont(pdfFont, fontSize);
	    stream.newLineAtOffset(startX, startY);
	    for (String line: lines)
	    {
	        stream.showText(line);
	        stream.newLineAtOffset(0, -leading);
	    }
	    stream.endText(); 
	    stream.close();
	}
	
	/**
	 * @param page
	 * @param contentStream
	 * @param y the y-coordinate of the first row
	 * @param margin the padding on left and right of table
	 * @param content a 2d array containing the table data
	 * @throws IOException
	 */
	public static void drawTable(PDPage page, PDPageContentStream contentStream, 
	                            float y, float margin, 
	                            String[][] content) throws IOException {
	    final int rows = content.length;
	    final int cols = content[0].length;
	    final float rowHeight = 20f;
	    final float tableWidth = page.getMediaBox().getWidth() - margin - margin;
	    final float tableHeight = rowHeight * rows;
	    final float colWidth = tableWidth/(float)cols;
	    final float cellMargin=5f;

	    //draw the rows
	    float nexty = y ;
	    for (int i = 0; i <= rows; i++) {
	        drawLine(contentStream, margin, nexty, margin+tableWidth, nexty);
	        nexty-= rowHeight;
	    }

	    //draw the columns
	    float nextx = margin;
	    for (int i = 0; i <= cols; i++) {
	        drawLine(contentStream, nextx, y, nextx, y-tableHeight);
	        nextx += colWidth;
	    }

	    //now add the text        
	    contentStream.setFont( PDType1Font.HELVETICA_BOLD , 12 );        

	    float textx = margin+cellMargin;
	    float texty = y-15;        
	    for(int i = 0; i < content.length; i++){
	        for(int j = 0 ; j < content[i].length; j++){
	            String text = content[i][j];
	            contentStream.beginText();
	            contentStream.newLineAtOffset(textx,texty);
	            contentStream.showText(text);
	            contentStream.endText();
	            textx += colWidth;
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	}
	
	/**
	 * PDPageContentStream.drawLine is deprecated and can be replaced by calling PDPCS.moveTo(float, float) -> PDPCS.lineTo(float, float) -> PDPCS.stroke()
	 * This method reduces that amount of calls down to one Method: drawLine(PDPageContentStream, float, float, float, float)
	 * @param stream As drawLine can�t be called on a Stream Object it has to be parametized
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 */
	public static void drawLine(PDPageContentStream stream, float xStart, float yStart, float xEnd, float yEnd) {
		try {
			stream.moveTo(xStart, yStart);
			stream.lineTo(xEnd, yEnd);
			stream.stroke();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
