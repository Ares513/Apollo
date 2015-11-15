package com.team1ofus.apollo;

import java.awt.Point;
import java.util.HashMap;

public class PaintTool {

	private int brushSelection; //brush index
	private Brush[] brushes; // available brushes
	private TILE_TYPE tileToPaint; //tiletype selected to paint with
	private HashMap<Integer, TILE_TYPE> tileMap = new HashMap<Integer, TILE_TYPE>();
	
	public PaintTool() {
		tileToPaint = TILE_TYPE.WALL;
		brushSelection = 0;
		initializeTileMap();
		initializeBrushes();
	}
	
	/*
	 * Generates the array of brushes
	 * seems primitive
	 * sizes?
	 */
	private void initializeBrushes() {
		brushes = new Brush[1];
		Point[] brush1points = {new Point(0,0)}; //single tile
		brushes[0] = new Brush(brush1points);
		
	}
	
	/*
	 * Fills out the hashmap with the indices linked to the tiletypes 
	 */
	private void initializeTileMap() {
		int c = 0;
		for(TILE_TYPE t : TILE_TYPE.values()) {
			tileMap.put(c, t);
			c++;
		}
	}
	
	/*
	 * Selects the brush based on index
	 */
	public void selectBrush(int brushIndex) {
		brushSelection = brushIndex;
	}
	
	/*
	 * Selects the tiletype based on index
	 */
	public void selectTileType(int typeIndex) {
		tileToPaint = tileMap.get(typeIndex);
	}
	
	/*
	 * Selects the tiletype based on enum
	 */
	public void selectTileType(Object type) {
		if(type instanceof TILE_TYPE)
			tileToPaint = (TILE_TYPE) type;
	}
	
	/* What is this supposed to do?
	public Cell paintAtTile(Cell cell) {
		
	}*/
}
