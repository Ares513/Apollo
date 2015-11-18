package com.team1ofus.apollo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class CellRenderer {
	int tileWidth;
	int tileHeight;
	Cell editCell;
	public CellRenderer(Cell inCell) {
		editCell = inCell;
		tileWidth = 32;
		tileHeight = 32;
	}
	public void renderTiles(Graphics g) {
		
		for(int i=0; i<editCell.tiles[0].length; i++) {
			for(int j=0; j<editCell.tiles[1].length; j++) {
				switch(editCell.tiles[i][j].getType()) {
				case WALL:
					g.setColor(Color.gray);
					
					break;
				case PEDESTRIAN_WALKWAY:
					g.setColor(Color.WHITE);
					break;
				default:
					break;
				}
				g.fillRect(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
				g.setColor(Color.black);
				g.drawRect(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
			}
		}
	}
	public Point pickTile(int mouseX, int mouseY) {
		int x = (int) (Math.floor((mouseX)/tileWidth));
		int y = (int) (Math.floor((mouseY)/tileHeight));
		return new Point(x,y);
	}
	public void editTile(int x, int y, DataTile swapped) {
		//edits the contents of a tile
		if(x >= 0 && x < editCell.tiles[0].length - 1 && y >= 0 && y < editCell.tiles[1].length - 1) {
			editCell.tiles[x][y] = swapped;
		}
		
	}
}
