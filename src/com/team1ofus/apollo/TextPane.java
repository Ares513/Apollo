package com.team1ofus.apollo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.function.Predicate;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Intended to be efficient at drawing text in a standardized fashion over the screen.
 * Add support for offset needed as a future task.
 */
public class TextPane extends JPanel {
	ArrayList<TextLocation> locations = new ArrayList<TextLocation>();
	public TextPane() {
		setOpaque(true);

	}
	public void paintComponent(Graphics g) {
		this.paintComponents(g);
		for(TextLocation l : locations) {
			g.drawString(l.input, l.location.x, l.location.y);
		}
		showConsole(g);
	}

	public void addLocation(TextLocation input) {
		locations.add(input);
	}
	
	public void removeLocation(Point input) {
		locations.removeIf(isEqual(input));
	}
	public static Predicate<TextLocation> isEqual(Point filter) {
	    return p -> p.location == filter;
	}
	private void showConsole(Graphics g) {
		g.setColor(Color.BLACK);
		if(DebugConsole.getEntries().size() > 1000) {
			DebugConsole.getEntries().clear();
		}
		if(DebugConsole.getEntries().size() <= 0) {
			return;
		}
		int count = 0; //number of lines drawn.
		if(DebugConsole.getEntries().size() < BootstrapperConstants.LINES_TO_SCREEN) {
			//There are fewer lines on the list, so just start at the end.
			
			for(int i=DebugConsole.getEntries().size()-1; i>0;i--) {
				g.drawString(DebugConsole.getEntries().get(i), 0, g.getFont().getSize()*count);
				count++;
			}
		} else {
			//more, start at the end up to LINES_TO_SCREEN lower
			for(int i=DebugConsole.getEntries().size()-1; i>DebugConsole.getEntries().size()-1-BootstrapperConstants.LINES_TO_SCREEN;i--) {
				g.drawString(DebugConsole.getEntries().get(i), 0, g.getFont().getSize()*count);
				count++;
			}
		}
	
		//draw the number of lines onscreen that are specified, but don't remove them
	}
}
