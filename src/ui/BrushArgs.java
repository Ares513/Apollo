package ui;
//

import java.awt.Point;

public class BrushArgs {

	Point lastMouseLocation;
	Point currentMouseLoc;
	int size;
	public BrushArgs(Point currentMouseLoc, Point lastMouseLocation, int size) {
		super();
		this.lastMouseLocation = lastMouseLocation;
		this.currentMouseLoc = currentMouseLoc;
		this.size = size;
	}
	
}
