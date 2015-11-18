package com.team1ofus.apollo;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class DrawPane extends JPanel {
	CellRenderer render;
	public DrawPane(Cell inCell) {
		
		
		render = new CellRenderer(inCell);
	}
	public void paintComponent(Graphics g) {
		render.renderTiles(g);
	}
	public Point pickTile(int mouseX, int mouseY) {
		return render.pickTile(mouseX, mouseY);
	}
	
}
