package com.team1ofus.apollo;

import java.awt.Color;
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
	public CellRenderer(Cell inCell) {
		editCell = inCell;
		tileWidth = (int) Math.round(16*inCell.scaling);
		tileHeight =(int) Math.round(16*inCell.scaling);
		offset = new Point(0,0);
		underlyingOffset = new Point(0,0);
	}
	public void renderTiles(Graphics g, Image underlyingImage) {
		g.setColor(Color.WHITE);
	    g.fillRect(0, 0, tileWidth * editCell.getWidth(), tileHeight * editCell.getHeight());
		g.drawImage(underlyingImage, offset.x*-1 - underlyingOffset.x, offset.y*-1 - underlyingOffset.y, Color.white, null);
		int cellWidth = editCell.getWidth();
		int cellHeight = editCell.getHeight();
		for(int i=0; i<cellWidth; i++) {
			for(int j=0; j<cellHeight; j++) {
				try {
					editCell.getTile(i, j);
								
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("helloworld");
				}
				
				switch(editCell.getTile(i,j).getType()) {
				case WALL:
					//Color grey = new Color(Color.gray.getRed(), Color.gray.getGreen(), Color.gray.getBlue(), 125);
					
					//g.setColor(grey);
					
					break;
				case PEDESTRIAN_WALKWAY:
					Color green = new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), 125);
					g.setColor(green);
					g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
					break;
				default:
					break;
				}
				g.setColor(Color.black);
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
}
