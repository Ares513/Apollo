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
import javax.swing.JSpinner;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;

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
		windowUI.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox = Box.createVerticalBox();
		
		JButton btnThisIsA = new JButton("this is a test");
		verticalBox.add(btnThisIsA);
		
		JLabel button = new JLabel("#mouse#");
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		verticalBox.add(button);
		windowUI.add(verticalBox);
		
		JComboBox tiles = new JComboBox();
		verticalBox.add(tiles);
		tiles.setAlignmentY(5.0f);
		tiles.setModel(new DefaultComboBoxModel(TILE_TYPE.values()));
		
		JComboBox brushes = new JComboBox();
		verticalBox.add(brushes);
		brushes.setModel(new DefaultComboBoxModel(new String[] {"Single Tile"}));
		brushes.setAlignmentY(Component.TOP_ALIGNMENT);
		
		DrawPane panel = new DrawPane();
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition = new Point(e.getX(), e.getY());
				button.setText(mousePosition.toString()); 
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		
		Box verticalBox_1 = Box.createVerticalBox();
		frame.getContentPane().add(verticalBox_1, BorderLayout.SOUTH);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	public Point getMouseLocation() {
		return mousePosition;
	}
}
