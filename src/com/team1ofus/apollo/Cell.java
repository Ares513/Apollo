package com.team1ofus.apollo;

import java.awt.Point;
import java.io.Serializable;
import java.util.UUID;

public class Cell implements Serializable {
	/**
	 * 
	 */
	private String id;
	private static final long serialVersionUID = 3L;
	public DataTile[][] tiles;
	//minimum required information
	double scaling  = 1;
	public Cell(int width, int height, double scaling, TILE_TYPE defaultTile, String name) {
		id = name;
		//identifier
		tiles = new DataTile[width][height];
		fillTiles(TILE_TYPE.WALL, width, height);
		
	}
	private void fillTiles(TILE_TYPE fillTile, int width, int height) {
		for(int i=0; i< width; i++) {
			for(int j=0; j< height; j++) {
				tiles[i][j] = new DataTile(fillTile);
				
			}
		}
	}
	public String getID() {
		return id;
	}
	public void setID(String inID) {
		id = inID;
	}
	public int getWidth() {
		return tiles[0].length;
	}
	public int getHeight() {
		return tiles[1].length;
	}
	public DataTile getTile(int x, int y) {
			
			int xActual = x;
			int yActual = y;
			if(x > getWidth() - 1) {
				xActual = getWidth() - 1;
			}
			if(y > getHeight() - 1) {
				yActual = getHeight() - 1;
			}
			return tiles[xActual][yActual];
	}
}
