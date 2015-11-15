package com.team1ofus.apollo;

public enum TILE_TYPE {
	WALL, //cannot path through a wall
	HALLWAY, //can path through a hallway from any direction to any direction
	DOOR,    //
	PEDESTRIAN_WALKWAY,
	GRASS,
	CONGESTED,
	STAIRS,
	OUTDOOR_STAIRS,
	STEEP, //can path through. Represents hills(inclines in general)
	BATHROOM	
	
 }
