package com.team1ofus.apollo;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class DrawPane extends JPanel {
	CellRenderer render;
	public DrawPane(Cell inCell) {
		Cell cell = new Cell(50, 50, 1.0, TILE_TYPE.WALL);
		
		render = new CellRenderer(cell);
	}
	public void paintComponent(Graphics g) {
		render.renderTiles(g);
	}
	public Point pickTile(int mouseX, int mouseY) {
		return render.pickTile(mouseX, mouseY);
	}
	
}
