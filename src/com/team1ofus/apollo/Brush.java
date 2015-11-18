package com.team1ofus.apollo;
/*
 * referencesPoints Point[]
 * 
 * brushSelection int
 * brushes Brush[]
 * tileToPaint TILE_TYPE
 */


import java.awt.Point;

public class Brush {
	private Point[] referencePoints; //points in relation to the point/tile selected to paint

	public Brush(Point[] pts) {
		referencePoints = pts;
	}
	
	public Point[] getReferencePoints() {
		return referencePoints;
	}
}
