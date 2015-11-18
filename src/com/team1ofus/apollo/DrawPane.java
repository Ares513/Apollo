package com.team1ofus.apollo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;

public class DrawPane extends JPanel {
	CellRenderer render;
	Image currentImage;
	public DrawPane(Cell inCell) {
		
		
		render = new CellRenderer(inCell);
	}
	public void paintComponent(Graphics g) {
		render.renderTiles(g, currentImage);
	}
	public Point pickTile(int mouseX, int mouseY) {
		return render.pickTile(mouseX, mouseY);
	}
	public Image getCurrentImage() {
		return currentImage;
	}
	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}
	
}
