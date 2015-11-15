package com.team1ofus.apollo;

import java.awt.Point;

public class Brush {
	Point[] referencePoints; //points in relation to the one selected to paint

	public Brush(Point[] pts) {
		referencePoints = pts;
	}
}
