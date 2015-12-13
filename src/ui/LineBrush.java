package ui;

import java.awt.Point;
import java.util.ArrayList;

import com.sun.xml.internal.ws.util.InjectionPlan;

public class LineBrush implements IBrush {
	String name;
	public LineBrush(String name) {
		this.name = name;
	}
	public Point[] getReferencePoints(BrushArgs bArgs) {
		// TODO Auto-generated method stub
		if(bArgs.lastMouseLocation == null) {
			Point[] result = new Point[1];
			result[0] = new Point(0,0);
			return result;
			
		} else {
			ArrayList<Point> result = getLine(0 ,0 , bArgs.lastMouseLocation.x- bArgs.currentMouseLoc.x, bArgs.lastMouseLocation.y - bArgs.currentMouseLoc.y);
			
			return result.toArray(new Point[result.size()]);
		}
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public ArrayList<Point> getLine(int x,int y,int x2, int y2) {
		ArrayList<Point> result = new ArrayList<Point>(); 
	    int w = x2 - x ;
	    int h = y2 - y ;
	    int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
	    if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
	    if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
	    if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
	    int longest = Math.abs(w) ;
	    int shortest = Math.abs(h) ;
	    if (!(longest>shortest)) {
	        longest = Math.abs(h);
	        shortest = Math.abs(w);
	        if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
	        dx2 = 0 ;            
	    }
	    int numerator = longest >> 1 ;
	    for (int i=0;i<=longest;i++) {
	        result.add(new Point(x,y));
	        numerator += shortest ;
	        if (!(numerator<longest)) {
	            numerator -= longest ;
	            x += dx1 ;
	            y += dy1 ;
	        } else {
	            x += dx2 ;
	            y += dy2 ;
	        }
	    }
	    return result;
	}
}	
