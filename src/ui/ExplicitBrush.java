package ui;

import java.awt.Point;

public class ExplicitBrush implements IBrush {
	private Point[] referencePoints; //points in relation to the point/tile selected to paint
	String name;
	public ExplicitBrush(Point[] pts, String name) {
		referencePoints = pts;
		this.name = name;
	}
	


	@Override
	public Point[] getReferencePoints(BrushArgs bArgs) {
		return referencePoints;
	}



	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
}
