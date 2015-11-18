package com.team1ofus.apollo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class CellRenderer {
	int tileWidth;
	int tileHeight;
	Point offset;
	Cell editCell;
	public CellRenderer(Cell inCell) {
		editCell = inCell;
		tileWidth = (int) Math.round(16*inCell.scaling);
		tileHeight =(int) Math.round(16*inCell.scaling);
		offset = new Point(0,0);
	}
	public void renderTiles(Graphics g) {
		
		for(int i=0; i<editCell.tiles[0].length; i++) {
			for(int j=0; j<editCell.tiles[1].length; j++) {
				switch(editCell.tiles[i][j].getType()) {
				case WALL:
					g.setColor(Color.gray);
					
					break;
				case PEDESTRIAN_WALKWAY:
					g.setColor(Color.GREEN);
					break;
				default:
					break;
				}
				g.fillRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
				g.setColor(Color.black);
				g.drawRect(i*tileWidth - offset.x, j*tileHeight - offset.y, tileWidth, tileHeight);
			}
		}
	}
	public Point pickTile(int mouseX, int mouseY) {
		int x = (int) (Math.floor((mouseX + offset.x)/tileWidth));
		int y = (int) (Math.floor((mouseY + offset.y)/tileHeight));
		return new Point(x,y);
	}
	public void editTile(int x, int y, DataTile swapped) {
		//edits the contents of a tile
		if(x >= 0 && x < editCell.tiles[0].length - 1 && y >= 0 && y < editCell.tiles[1].length - 1) {
			editCell.tiles[x][y] = swapped;
		}
		
	}
	public void incrementOffset(int dx, int dy, int windowWidth, int windowHeight) {
		//some optimizations to be made here
		offset.translate(dx, dy);
		if(offset.x < 0) {
			offset.x = 0;
		} else if(offset.x > editCell.tiles[0].length * tileWidth - windowWidth) {
			offset.x = editCell.tiles[0].length * tileWidth - windowWidth;
		}
		if(offset.y < 0) {
			offset.y = 0;
		} else if(offset.y > editCell.tiles[1].length * tileHeight - windowHeight) {
			offset.y = editCell.tiles[1].length * tileHeight - windowHeight; 
		
		}
		
	}
}
