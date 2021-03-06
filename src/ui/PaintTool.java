package ui;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.org.apache.bcel.internal.generic.LASTORE;
import com.team1ofus.apollo.DataTile;
import com.team1ofus.apollo.TILE_TYPE;

import core.DebugManagement;
import jdk.management.resource.internal.inst.InitInstrumentation;

public class PaintTool {

	private int brushSelection; //brush index
	private IBrush[] brushes; // available brushes
	private TILE_TYPE tileToPaint; //tiletype selected to paint with
	private HashMap<Integer, TILE_TYPE> tileMap = new HashMap<Integer, TILE_TYPE>();
	private UndoData undoData = new UndoData();
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
		brushes = new IBrush[9];
		Point[] brush1points = {new Point(0,0)}; //single tile
		brushes[0] = new ExplicitBrush(brush1points, "Single tile");
		Point[] brush2points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1)}; //2 x 2 square
		brushes[1] = new ExplicitBrush(brush2points, "2x2 square");
		Point[] brush3points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(-1,0), new Point(0,-1), new Point(1,-1), new Point(-1,1), new Point(-1,-1), new Point(1,1)}; //3 x 3 square
		brushes[2] = new ExplicitBrush(brush3points, "3x3 square");
		Point[] brush4points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(-1,0), new Point(0,-1), new Point(1,-1), new Point(-1,1), new Point(-1,-1), new Point(1,1),
				new Point(2,0), new Point(2,1), new Point(0,2), new Point(1,2), new Point(2,2), new Point(2,-1), new Point(2,-2), new Point(-2,0), new Point(0,-2),
				new Point(1,-2), new Point(-2,-2), new Point(-2,1), new Point(-2,2), new Point(-2,-1), new Point(-1,-2), new Point(-1,2)}; //5 x 5 square
		brushes[3] = new ExplicitBrush(brush4points, "4x4 square");
		Point[] brush5points = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(-1,0), new Point(0,-1), new Point(1,-1), new Point(-1,1), new Point(-1,-1), new Point(1,1),
				new Point(2,0), new Point(2,1), new Point(0,2), new Point(1,2), new Point(2,2), new Point(2,-1), new Point(2,-2), new Point(-2,0), new Point(0,-2),
				new Point(1,-2), new Point(-2,-2), new Point(-2,1), new Point(-2,2), new Point(-2,-1), new Point(-1,-2), new Point(-1,2),
				new Point(-3,3), new Point(-2,3), new Point(-1,3), new Point(0,3), new Point(1,3), new Point(2,3),
				new Point(3,3), new Point(3,2), new Point(3,1), new Point(3,0), new Point(3,-1), new Point(3,-2),
				new Point(3,-3), new Point(2,-3), new Point(1,-3), new Point(0,-3), new Point(-1,-3), new Point(-2,-3),
				new Point(-3,-3), new Point(-3,-2), new Point(-3,-1), new Point(-3,0), new Point(-3,1), new Point(-3,2)}; //7 x 7 square
		brushes[4] = new ExplicitBrush(brush5points, "5x5 Square");
		ArrayList<Point> brush7points = new ArrayList<Point>(); 
		for(int i=0; i< 7; i++) {
			for(int j=0; j < 7; j++) {
				brush7points.add(new Point(3-i, 3-j));
			}
		}
		brushes[5] = new ExplicitBrush(brush7points.toArray(new Point[brush7points.size()]), "7x7 Square");
		ArrayList<Point> circlePts = new ArrayList<Point>();
		int i = 0;
		int j = 0;
		for(i=0; i< 3; i++) {
			circlePts.add(new Point(i, j));
			for(j=0; j< 3; j++) {
				circlePts.add(new Point(i, j));
				
				
			}
			
		}
		for(j=0; j<3; j++) {
			circlePts.add(new Point(i, j));
			for(i=0; i< 3; i++) {
				circlePts.add(new Point(i, j));
				
				
			}
		}
		
		brushes[6] = new ExplicitBrush(circlePts.toArray(new Point[circlePts.size()]), "Box outline");
		brushes[7] = new LineBrush("Line");
		brushes[8] = new ImageBrush();
		
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
	public String[] getBrushNames() {
		String[] result = new String[brushes.length];
		for(int i=0; i<brushes.length; i++) {
			result[i] = brushes[i].getName();
		}
		return result;
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
	public void applyBrush(CellRenderer render, int x, int y, BrushArgs eArgs) {
		Point[] result = brushes[getBrushSelection()].getReferencePoints(eArgs);
		TILE_TYPE[] data = new TILE_TYPE[result.length];
		for(int i=0; i<result.length; i++) {
			data[i] = render.editCell.getTile(new Point(x + result[i].x, y + result[i].y));
		}
		

		for(Point p : result ) {
			render.editTile(x + p.x, y + p.y, new DataTile(getTileToPaint()));
		}
		TILE_TYPE[] after = new TILE_TYPE[result.length];
		for(int i=0; i<result.length; i++) {
			after[i] = render.editCell.getTile(new Point(x + result[i].x, y + result[i].y));
		}
		boolean differs = false;
		for(int i=0; i < result.length; i++) {
			
			if(data[i] != after[i]) {
				differs = true;
				break;
			}
		}
		if(!differs) {
			DebugManagement.writeNotificationToLog("Before and after are identical. Not storing to Undo history.");
		} else {
			undoData.push(result, data, new Point(x, y));
			
		}
		
	}
	public void undo(CellRenderer render) {
		undoData.undo(render);
	}
	public int getUndoStack() {
		return undoData.size();
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

	public IBrush[] getBrushes() {
		return brushes;
	}

	public TILE_TYPE getTileToPaint() {
		return tileToPaint;
	}
}
