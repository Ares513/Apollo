package com.team1ofus.apollo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

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
	}

	public void addLocation(TextLocation input) {
		locations.add(input);
	}
}
