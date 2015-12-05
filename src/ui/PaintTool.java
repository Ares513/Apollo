package ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import com.team1ofus.apollo.DataTile;
import com.team1ofus.apollo.TILE_TYPE;

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
		brushes = new Brush[6];
		Point[] brush1points = {new Point(0,0)}; //single tile
		brushes[0] = new Brush(brush1points);
		Point[] brush2points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1)}; //2 x 2 square
		brushes[1] = new Brush(brush2points);
		Point[] brush3points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(-1,0), new Point(0,-1), new Point(1,-1), new Point(-1,1), new Point(-1,-1), new Point(1,1)}; //3 x 3 square
		brushes[2] = new Brush(brush3points);
		Point[] brush4points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(-1,0), new Point(0,-1), new Point(1,-1), new Point(-1,1), new Point(-1,-1), new Point(1,1),
				new Point(2,0), new Point(2,1), new Point(0,2), new Point(1,2), new Point(2,2), new Point(2,-1), new Point(2,-2), new Point(-2,0), new Point(0,-2),
				new Point(1,-2), new Point(-2,-2), new Point(-2,1), new Point(-2,2), new Point(-2,-1), new Point(-1,-2), new Point(-1,2)}; //5 x 5 square
		brushes[3] = new Brush(brush4points);
		Point[] brush5points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(-1,0), new Point(0,-1), new Point(1,-1), new Point(-1,1), new Point(-1,-1), new Point(1,1),
				new Point(2,0), new Point(2,1), new Point(0,2), new Point(1,2), new Point(2,2), new Point(2,-1), new Point(2,-2), new Point(-2,0), new Point(0,-2),
				new Point(1,-2), new Point(-2,-2), new Point(-2,1), new Point(-2,2), new Point(-2,-1), new Point(-1,-2), new Point(-1,2),
				new Point(-3,3), new Point(-2,3), new Point(-1,3), new Point(0,3), new Point(1,3), new Point(2,3),
				new Point(3,3), new Point(3,2), new Point(3,1), new Point(3,0), new Point(3,-1), new Point(3,-2),
				new Point(3,-3), new Point(2,-3), new Point(1,-3), new Point(0,-3), new Point(-1,-3), new Point(-2,-3),
				new Point(-3,-3), new Point(-3,-2), new Point(-3,-1), new Point(-3,0), new Point(-3,1), new Point(-3,2)}; //7 x 7 square
		brushes[4] = new Brush(brush5points);
		
		ArrayList<Point> circlePts = new ArrayList<Point>();
		for(float i=0; i< Math.PI * 2; i = (float) (i + Math.PI/4)) {
			for(float j=0; j< Math.PI * 2; j = (float) (j + Math.PI/4)) {
				
				circlePts.add(new Point((int)Math.round(Math.cos(i)*3),(int)Math.round(Math.cos(j)*3)));
				
			}
		}
		brushes[5] = new Brush(circlePts.toArray(new Point[circlePts.size()]));
		
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
