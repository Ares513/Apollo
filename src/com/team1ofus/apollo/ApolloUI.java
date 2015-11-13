package com.team1ofus.apollo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseMotionAdapter;

public class ApolloUI {
	private JFrame frame;
	private Point mousePosition;
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
		
		DrawPane panel = new DrawPane();
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition = new Point(e.getX(), e.getY());
				lblMousePosition.setText(mousePosition.toString()); 
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
	public Point getMouseLocation() {
		return mousePosition;
	}
}
