package com.team1ofus.apollo;
/*
Description:    Is used to paint the tiles into the map editor

Attributes
brushSelection              int                     Represents which brush type has been selected.
brushes                         Brush[]             List of the possible brush types.
tileToPaint                    TILE_TYPE     The type of tile that will be painted.

Methods
selectBrush                                           This function selects which brush type has been selected.
paintAtTile                                            Paints the selected brush at the specified location                  
 
Relationships
Brush               Composition                  Holds the brush objects
*/


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
		brushes = new Brush[3];
		Point[] brush1points = {new Point(0,0)}; //single tile
		brushes[0] = new Brush(brush1points);
		Point[] brush2points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1)}; //2 x 2 square
		brushes[1] = new Brush(brush2points);
		Point[] brush3points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(-1,0), new Point(0,-1), new Point(1,-1), new Point(-1,1), new Point(-1,-1), new Point(1,1)}; //3 x 3 square
		brushes[2] = new Brush(brush3points);
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
	public void applyBrush(CellRenderer render, int x, int y) {
		for(Point p : brushes[getBrushSelection()].getReferencePoints()) {
			render.editTile(x + p.x, y + p.y, new DataTile(getTileToPaint()));
		}
		
	}
	/*
	 * Selects the tiletype based on enum
	 */
	public void selectTileType(TILE_TYPE type) {
			tileToPaint = type;
	}
	//Need to be able to query the currently selected brush; set with methods you created.
	public int getBrushSelection() {
		return brushSelection;
	}

	public Brush[] getBrushes() {
		return brushes;
	}

	public TILE_TYPE getTileToPaint() {
		return tileToPaint;
	}
}
