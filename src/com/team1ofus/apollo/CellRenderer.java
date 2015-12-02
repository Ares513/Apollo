package com.team1ofus.apollo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class CellRenderer {
	int tileWidth;
	int tileHeight;
	Point offset;
	Point underlyingOffset;
	Cell editCell;
	private boolean campusMapFlag = false;
	
	public CellRenderer(Cell inCell) {
		editCell = inCell;
		tileWidth = (int) Math.round(16*inCell.scaling);
		tileHeight =(int) Math.round(16*inCell.scaling);
		offset = new Point(0,0);
		underlyingOffset = new Point(0,0);
	}
	public void renderTiles(Graphics g, BufferedImage underlyingImage, int windowWidth, int windowHeight) {
		g.setColor(Color.WHITE);
	    g.fillRect(0, 0, tileWidth * editCell.getWidth(), tileHeight * editCell.getHeight());
	    
	    if(campusMapFlag) { //if the campus map is selected, stretch it by 4 times
	    	//BufferedImage img = underlyingImage.getSubimage(offset.x, offset.y, windowWidth, windowHeight);
	    	//offset.x*-1 - underlyingOffset.x, offset.y*-1 - underlyingOffset.y
	    	g.drawImage(underlyingImage, offset.x*-1 - underlyingOffset.x, offset.y*-1 - underlyingOffset.y, underlyingImage.getWidth(null)*4, underlyingImage.getHeight(null)*4, Color.white, null);
	    } else {
	    	g.drawImage(underlyingImage, offset.x*-1 - underlyingOffset.x, offset.y*-1 - underlyingOffset.y, Color.white, null);
	    }
		int cellWidth = editCell.getWidth();
		int cellHeight = editCell.getHeight();
		Color blue = new Color(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue(), 125);
		Color black = new Color(Color.black.getRed(), Color.black.getGreen(), Color.blue.getRed(), 125);
		Color gray = new Color(Color.gray.getRed(), Color.gray.getGreen(), Color.blue.getRed(), 125);
		Color yellow = new Color(Color.yellow.getRed(), Color.yellow.getGreen(), Color.blue.getRed(), 125);
		Color green = new Color(Color.green.getRed(), Color.green.getGreen(), Color.blue.getRed(), 125);
		Color red = new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 125);
		//edge bounds to render for map
		int lowerX = (int)(offset.x / BootstrapperConstants.TILE_WIDTH);
		int lowerY = (int)(offset.y / BootstrapperConstants.TILE_HEIGHT);
		int higherX = (int)(offset.x + windowWidth)/BootstrapperConstants.TILE_WIDTH;
		int higherY = (int)(offset.y + windowHeight)/BootstrapperConstants.TILE_HEIGHT;
		if(windowWidth > BootstrapperConstants.TILE_WIDTH * cellWidth) {
			higherX = cellWidth;
		}
		if(windowHeight > BootstrapperConstants.TILE_HEIGHT * cellHeight) {
			higherY = cellHeight;
		}
		for(int i=lowerX; i<higherX; i++) {
			for(int j=lowerY; j<higherY; j++) {
				try {
					editCell.getTile(i, j);
								
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("helloworld");
				}
				
				switch(editCell.getTile(i,j).getType()) {
				case WALL:
					//don't draw walls
					
					g.setColor(black);
					break;
				case PEDESTRIAN_WALKWAY:
					
					g.setColor(gray);
					g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					break;
				case DOOR:
					g.setColor(yellow);
					g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					break;
				case GRASS:
					g.setColor(green);
					g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					break;
				case CONGESTED:
					g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					
					g.setColor(Color.magenta);
				case VERTICAL_UP_STAIRS:
					g.setColor(red);
					//g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					g.drawLine(i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y, i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y + tileHeight);
					//base line for arrow
					g.drawLine(i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y, i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight/4);
					g.drawLine(i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y, i*tileWidth - offset.x + tileWidth, j*tileHeight - offset.y + tileHeight/4);
					//arrow lines
					
					break;
				case VERTICAL_DOWN_STAIRS:
					g.setColor(blue);
					//g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					g.drawLine(i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y, i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y + tileHeight);
					//base line for arrow
					g.drawLine(i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y + tileHeight, i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight*(2/4));
					g.drawLine(i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y + tileHeight, i*tileWidth - offset.x + tileWidth, j*tileHeight - offset.y + tileHeight*(2/4));
					//arrow lines
					
					break;
				case HORIZONTAL_LEFT_STAIRS:
					g.setColor(red);

					g.drawLine(i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight/2, i*tileWidth - offset.x+tileWidth, j*tileHeight - offset.y + tileHeight/2);
					//horizontal line
					//base line for arrow
					//arrow lines
					Point p1 = new Point(i*tileWidth - offset.x + tileWidth, j*tileHeight - offset.y + tileHeight/2);
					Point p2 = new Point(i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y);
					
					g.drawLine(p1.x, p2.y, p1.x, p2.y);
				
					
					break;
				case HORIZONTAL_RIGHT_STAIRS:
					g.setColor(blue);

					g.drawLine(i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight/2, i*tileWidth - offset.x+tileWidth, j*tileHeight - offset.y + tileHeight/2);
					//horizontal line
					//base line for arrow
					//arrow lines
					//Point p1 = new Point(i*tileWidth - offset.x + tileWidth, j*tileHeight - offset.y + tileHeight/2);
					//Point p2 = new Point(i*tileWidth - offset.x + tileWidth/2, j*tileHeight - offset.y);
					
					//g.drawLine(p1.x, p2.y, p1.x, p2.y);
				
					
					break;

				case TREE:
					g.setColor(green);
					g.drawLine(i*tileWidth - offset.x  + tileWidth, j*tileHeight - offset.y, i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight);
					g.drawLine(i*tileWidth - offset.x, j*tileHeight - offset.y, i*tileWidth - offset.x  + tileWidth, j*tileHeight - offset.y + tileHeight);
					
					g.drawLine(i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight/2, i*tileWidth - offset.x+tileWidth, j*tileHeight - offset.y + tileHeight/2);
					//horizontal line
				
					break;
				case BUSH:
					g.setColor(green);
					g.drawLine(i*tileWidth - offset.x  + tileWidth, j*tileHeight - offset.y, i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight);
					g.drawLine(i*tileWidth - offset.x, j*tileHeight - offset.y, i*tileWidth - offset.x  + tileWidth, j*tileHeight - offset.y + tileHeight);
					g.fillOval(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					g.drawLine(i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight/2, i*tileWidth - offset.x+tileWidth, j*tileHeight - offset.y + tileHeight/2);
					//horizontal line
				
					break;
				case LINOLEUM:
					g.setColor(gray);
					g.drawRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth/2, tileHeight/2);
					g.drawRect(i*tileWidth - offset.x +tileWidth/2, j*tileHeight - offset.y+tileHeight/2, tileWidth/2, tileHeight/2);
					
					break;
				case ELEVATOR:
					g.setColor(red);
					g.drawRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth/2, tileHeight/2);
					break;
				case IMPASSABLE:
					g.setColor(black);
					g.drawLine(i*tileWidth - offset.x  + tileWidth, j*tileHeight - offset.y, i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight);
					g.drawLine(i*tileWidth - offset.x, j*tileHeight - offset.y, i*tileWidth - offset.x  + tileWidth, j*tileHeight - offset.y + tileHeight);
				
					break;
				case MALE_BATHROOM:
					
					g.setColor(blue);
					g.fillOval(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					break;
				case FEMALE_BATHROOM:
					g.setColor(red);
					g.fillOval(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					break;
				case UNISEX_BATHROOM:
					g.setColor(red);
					g.fillArc(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight, 0, 180);
					g.setColor(blue);
					
					g.fillArc(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight, 0, -180);
					
					break;
				case BENCH:
					
					//g.fillArc(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight, 0, 180);
					g.setColor(black);
					
					g.fillArc(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight, 0, -180);
					
					break;
				case UNPLOWED:
					
					//g.fillArc(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight, 0, 180);
					g.setColor(black);
					
					g.drawArc(i*tileWidth - offset.x, j*tileHeight - offset.y+tileHeight/2, tileWidth, tileHeight/2, 0, 180);
					
					break;
				case CLASSROOM:
					
					//g.fillArc(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight, 0, 180);
					g.setColor(black);
					g.setFont(new Font("TimesRoman", Font.BOLD, tileHeight));
					g.drawString("C", i*tileWidth - offset.x + tileWidth/8, j*tileHeight - offset.y + tileHeight);
					break;
				case EXTRA_TILE_TYPE_1:
					//ROAD TILE
					//g.fillArc(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight, 0, 180);
					g.setColor(black);
					g.setFont(new Font("TimesRoman", Font.BOLD, tileHeight));
					g.fillOval(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					g.drawLine(i*tileWidth - offset.x  + tileWidth, j*tileHeight - offset.y, i*tileWidth - offset.x, j*tileHeight - offset.y + tileHeight);
					g.drawLine(i*tileWidth - offset.x, j*tileHeight - offset.y, i*tileWidth - offset.x  + tileWidth, j*tileHeight - offset.y + tileHeight);
				
					break;
				default:
					break;
				}
				
				g.setColor(black);
				//drawGridLines(g);
				g.drawRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
//				g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
//				g.setColor(Color.black);
//				g.drawRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
			}
		}
	}
	private void drawGridLines(Graphics g) {
		int width = editCell.getWidth();
		int height = editCell.getHeight();
		int right = width * tileWidth;
		int bottom = height * tileHeight;
		for(int i=0; i< width; i++) {
			g.drawLine(tileWidth*i - offset.x, 0, tileWidth*i - offset.y, bottom);
		}
		for(int i=0; i < height; i++) {
			
			g.drawLine(0, tileHeight*i - offset.x, right, tileHeight*i - offset.y);
		}
	}
	public Point pickTile(int mouseX, int mouseY) {
		int x = (int) (Math.floor((mouseX + offset.x)/tileWidth));
		int y = (int) (Math.floor((mouseY + offset.y)/tileHeight));
		return new Point(x,y);
	}
	public void editTile(int x, int y, DataTile swapped) {
		//edits the contents of a tile
		if(x >= 0 && x < editCell.getWidth() && y >= 0 && y < editCell.getHeight()) {
			editCell.setTile(x, y, swapped.getType());
		}
		
	}
	public void incrementOffset(int dx, int dy, int windowWidth, int windowHeight) {
		//some optimizations to be made here
		offset.translate(dx, dy);

		if(offset.x < 0) {
			offset.x = 0;
		} else if(offset.x > editCell.getWidth() * tileWidth - windowWidth) {
			offset.x = editCell.getWidth() * tileWidth - windowWidth;
		}
		if(offset.y < 0) {
			offset.y = 0;
		} else if(offset.y > editCell.getHeight() * tileHeight - windowHeight) {
			offset.y = editCell.getHeight() * tileHeight - windowHeight; 
		
		}
		if(offset.x < 0) {
			offset.x = 0;
		}
		if(offset.y < 0) {
			offset.y = 0;
		}
		DebugManagement.writeNotificationToLog("Current offest " + offset);
		DebugManagement.writeNotificationToLog("Window width " + windowWidth + " Window height " + windowHeight);
	}
	public void incrementUnderlyingOffset(int dx, int dy, int windowWidth, int windowHeight) {
		//some optimizations to be made here
		underlyingOffset.translate(dx, dy);
		if(underlyingOffset.x < 0) {
			underlyingOffset.x = 0;
		}
		if(underlyingOffset.y < 0) {
			underlyingOffset.y = 0;
		}
		
	}
	public void setCampusMapFlag(boolean flag) {
		campusMapFlag = flag;
	}
}
