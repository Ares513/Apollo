package ui;
//

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.sun.prism.Image;
import com.team1ofus.apollo.HashCell;

public class BrushArgs {

	Point lastMouseLocation;
	Point currentMouseLoc;
	int size;
	CellRenderer render;
	BufferedImage underlyingImage;
	
	public BrushArgs(Point currentMouseLoc, Point lastMouseLocation, int size, CellRenderer render, BufferedImage underlyingImage) {
		super();
		this.lastMouseLocation = lastMouseLocation;
		this.currentMouseLoc = currentMouseLoc;
		this.size = size;
		this.render = render;
		this.underlyingImage = underlyingImage;
	}
	
}
