package com.team1ofus.apollo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class CellRenderer {
	DataTile[][] tiles;
	int tileWidth;
	int tileHeight;
	public CellRenderer(DataTile[][] tiles) {
		this.tiles = tiles;
		tileWidth = 32;
		tileHeight = 32;
	}
	public void renderTiles(Graphics g) {
		for(int i=0; i<tiles[0].length; i++) {
			for(int j=0; j<tiles[1].length; j++) {
				switch(tiles[i][j].getType()) {
				case WALL:
					g.setColor(Color.gray);
					
					break;
				case HALLWAY:
					g.setColor(Color.WHITE);
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
}
