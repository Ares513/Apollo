package core;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.alee.laf.WebLookAndFeel;

import core.SplashScreen;
import data.DataManagement;
import ui.UIManagement;

public class Bootstrapper {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		WebLookAndFeel.install();
		SplashScreen splash = new SplashScreen(1000); //X000 -> x seconds 
		splash.showSplashAndExit();	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataManagement data;
					data = new DataManagement();
					
					UIManagement UI;
					UI = new UIManagement(data, splash.imageNames, splash.imageSelection);
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
