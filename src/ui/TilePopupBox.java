package ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import core.DebugManagement;
import core.SEVERITY_LEVEL;
import jdk.internal.dynalink.linker.LinkerServices;

/*
 * Creates a popup window whenever told to at a specific point
 * intended to be used in the TextPane class.
 * Not currently in use.
 */
public class TilePopupBox {
	ArrayList<String> lines;
	String title;
	
 //draw a popup with constraints
	public TilePopupBox(ArrayList<String> lines, String title) {
		if(lines == null) {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.CORRUPTED, "Line data isn't being sent to a popupbox!");
		} else if(lines.size() == 0) {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.CORRUPTED, "A popupbox was created without lines!");
			
		}
		this.lines = lines;
		this.title = title;
	}
	public void render(Graphics g, Point topCenter) {
		int longest = 0;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		FontMetrics metric = g.getFontMetrics();
		for(int i=0; i<lines.size(); i++) {
			int current = (int) metric.getStringBounds(lines.get(i), g).getWidth();
			if(current > longest) {
				longest = current;
			}
		}
		//figure out how big the rectangle has to be to draw all the strings properly
		Point target = new Point(topCenter.x - longest/2, topCenter.y);
		//target is the x-y location the popupbox will appear at
		double height = lines.size() * metric.getStringBounds(lines.get(0), g).getHeight();
		g.fillRect(target.x, target.y, longest+5, (int)height);
		g.drawRect(target.x, target.y, longest+5, (int)height);
		g.drawString(title, target.x, target.y);
	    for(int i=0; i<lines.size(); i++) {
	    	g.drawString(lines.get(i), target.x, target.y + 20 + 12*i);
	    }
	}
}
