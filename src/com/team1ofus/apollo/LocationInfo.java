package com.team1ofus.apollo;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class LocationInfo implements Serializable {
	String id;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> aliases = new ArrayList<String>();
	Point loc;
	public LocationInfo(String first, Point location, String cellID) {
		loc = location;
		aliases.add(first);
		id = cellID;
	}
	public void addAlias(String name) {
		//TODO: prevent duplicate names.
		aliases.add(name);
	}
	public void setLocation(Point location) {
		loc = location;
		
	}
	public Point getLocation() {
		return loc;
	}
	public String getCellReference() {
		return id;
	}
}
