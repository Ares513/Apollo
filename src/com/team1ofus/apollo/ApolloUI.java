package com.team1ofus.apollo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class ApolloUI {
	private JFrame frame;
	private Point mousePosition;
	private Point tilePosition;
	public ApolloUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		frame.setBounds(100, 100, 969, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel windowUI = new JPanel();
		windowUI.setBackground(Color.RED);
		windowUI.setForeground(Color.RED);
		frame.getContentPane().add(windowUI, BorderLayout.EAST);
		
		Box verticalBox = Box.createVerticalBox();
		windowUI.add(verticalBox);
		
		JButton btnThisIsA = new JButton("this is a test");
		verticalBox.add(btnThisIsA);
		
		JLabel lblMousePosition = new JLabel("#mouse#");
		lblMousePosition.setFont(new Font("Tahoma", Font.PLAIN, 20));
		verticalBox.add(lblMousePosition);
		
		JLabel lblTilePosition = new JLabel("#tile#");
		lblTilePosition.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		verticalBox.add(lblTilePosition);
		
		/*
		 * This sets up the CellRenderer inside of DrawPane with the data it needs.
		 */
		DrawPane panel = new DrawPane();
		
		
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				tilePosition = panel.pickTile(e.getX(), e.getY());
				lblTilePosition.setText(tilePosition.x + " , " + tilePosition.y); 
				mousePosition = new Point(e.getX(), e.getY());
				lblMousePosition.setText(mousePosition.x + " , " + mousePosition.y);
				
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	public Point getMouseLocation() { //UIManagement can get the mouse position at any time.
		return mousePosition;
	}
}
