package com.team1ofus.apollo;

import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPane extends JPanel {
	public void paintComponent(Graphics g) {
		for(int i=0; i<100; i++) {
			for(int j=0; j<100; j++) {
				g.drawRect(i*20, j*20, 20, 20);
			}
		}
	}
	
}
