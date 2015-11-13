package com.team1ofus.apollo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ApolloUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApolloUI window = new ApolloUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApolloUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 766, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DrawPane panel = new DrawPane();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
	}

}
