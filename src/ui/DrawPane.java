package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.team1ofus.apollo.HashCell;

public class DrawPane extends JPanel {
	CellRenderer render;
	BufferedImage currentImage;
	public DrawPane(HashCell inCell) {
		
		
		render = new CellRenderer(inCell);
	}
	public void paintComponent(Graphics g) {
		render.renderTiles(g, currentImage, this.getWidth(), this.getHeight());
	}
	public Point pickTile(int mouseX, int mouseY) {
		return render.pickTile(mouseX, mouseY);
	}
	public Image getCurrentImage() {
		return currentImage;
	}
	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}
	public void setCampusMapFlag(boolean flag) {
		render.setCampusMapFlag(flag);
	}
}
