package com.team1ofus.apollo;

import java.awt.Point;
import java.util.ArrayList;

public class TextLocation {
	ArrayList<String> lines = new ArrayList<String>();
	Point location;
	public TextLocation(String input, Point location) {
		lines.add(input);
		this.location = location;
	}
	public void addString(String input) {
		lines.add(input);
	}
	public ArrayList<String> getLines() {
		return lines;
	}
}
