package com.team1ofus.apollo;

import java.awt.Point;
import java.io.Serializable;
import java.util.UUID;

public class Cell implements Serializable {
	/**
	 * 
	 */
	private String id;
	private static final long serialVersionUID = 2L;
	public DataTile[][] tiles;
	//minimum required information
	double scaling;
	public Cell(int width, int height, double scaling, TILE_TYPE defaultTile) {
		id = UUID.randomUUID().toString();
		//identifier
		tiles = new DataTile[width][height];
		fillTiles(TILE_TYPE.WALL);
		
	}
	private void fillTiles(TILE_TYPE fillTile) {
		for(int i=0; i<tiles[0].length; i++) {
			for(int j=0; j<tiles[1].length; j++) {
				tiles[i][j] = new DataTile(fillTile);
			}
		}
	}
	public String getID() {
		return id;
	}
}
