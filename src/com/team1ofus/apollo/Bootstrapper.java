package com.team1ofus.apollo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.team1ofus.apollo.SplashScreen;

public class Bootstrapper {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SplashScreen splash = new SplashScreen(1000); //X000 -> x seconds 
		splash.showSplashAndExit();	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataManagement data;
					data = new DataManagement();
					
					UIManagement UI;
					UI = new UIManagement(data.getCells());
					UI.events.addListener(data);
					UI.begin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
}
