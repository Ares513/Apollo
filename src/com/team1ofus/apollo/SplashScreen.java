package com.team1ofus.apollo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import java.util.ArrayList;
public class SplashScreen extends JWindow {
	  private int duration;
	  ArrayList<String> imageNames = new ArrayList<String>();
	  ArrayList<BufferedImage> imageSelection = new ArrayList<BufferedImage>();
	  public SplashScreen(int d) {
	    duration = d;
	  }
		private void loadImages() {
			File dir = new File("./maps");
			for (File file : dir.listFiles()) {
				if (file.getName().endsWith((".jpg"))) {
					imageNames.add(file.getName());
					DebugManagement.metrics();
					imageSelection.add(loadImage(file.getName()));
				}
			}
		}

		private BufferedImage loadImage(String path) {
			BufferedImage img = null;
			try {
				DebugManagement.writeNotificationToLog("Loaded " + path);
				img = ImageIO.read(new File("./maps/" + path));
				DebugManagement.writeNotificationToLog("Load successful.");
				return img;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return img;

		}
	  // A simple little method to show a title screen in the center
	  // of the screen for the amount of time given in the constructor
	  public void showSplash() {
		  DebugManagement.metrics();
		  DebugManagement.writeNotificationToLog("Service Apollo launching, please wait...");
	    JPanel content = (JPanel)getContentPane();
	    content.setBackground(Color.white);

	    // Set the window's bounds, centering the window
	    int width = 650;
	    int height =262;
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width-width)/2;
	    int y = (screen.height-height)/2;
	    setBounds(x,y,width,height);

	    // Build the splash screen
	    JLabel label = new JLabel(new ImageIcon("apollo_splash.png"));
	    JLabel copyrt = new JLabel
	      ("Team 1 of us", JLabel.CENTER);
	    copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
	    content.add(label, BorderLayout.CENTER);
	    content.add(copyrt, BorderLayout.SOUTH);
	    Color oraRed = new Color(156, 20, 20,  255);
	    //content.setBorder(BorderFactory.createLineBorder(oraRed, 10));

	    // Display it
	    setVisible(true);

	    // Wait a little while, maybe while loading resources
	    try { loadImages(); } catch (Exception e) {}
	    //images loaded...
	    setVisible(false);
	  }

	  public void showSplashAndExit() {
	    showSplash();
	  }

	}
