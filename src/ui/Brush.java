package ui;

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
