package ui;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.DebugManagement;

public class ImageBrush implements IBrush {

	@Override
	public Point[] getReferencePoints(BrushArgs bArgs) {
		int result = JOptionPane.showConfirmDialog(new JFrame(),"This brush only works on clean maps. Are you sure you want to continue?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if(result == 0) {
			//yes!
			DebugManagement.writeNotificationToLog("User selected yes on an ImageBrush.");
			return getPaintPoints(bArgs);
		} else if(result == 1) {
			//no!
			DebugManagement.writeNotificationToLog("User selected no on an ImageBrush.");
			return new Point[] {};
		}
		
		return new Point[] {};
	}
	private Point[] getPaintPoints(BrushArgs bArgs) {
		ArrayList<Point> result = new ArrayList<Point>();
		int imageWidth = bArgs.underlyingImage.getWidth();
		int imageHeight = bArgs.underlyingImage.getHeight();
		BufferedImage buffy = bArgs.underlyingImage;
		int baseColor = buffy.getRGB(bArgs.pixelMouse.x-bArgs.render.underlyingOffset.x, bArgs.pixelMouse.y-bArgs.render.underlyingOffset.y);
		int  bRed = (baseColor & 0x00ff0000) >> 16;
		int  bGreen = (baseColor & 0x0000ff00) >> 8;
		int  bBlue = baseColor & 0x000000ff;
				
		for(int i=0; i < imageWidth; i = i + 4) {
			for(int j=0; j < imageHeight; j = j + 4) {
				
				int color = buffy.getRGB(i, j);

				int  red = (color & 0x00ff0000) >> 16;
				int  green = (color & 0x0000ff00) >> 8;
				int  blue = color & 0x000000ff;
				int alpha = (color>>24) & 0xff;
				
				if(within(red, bRed, 30) && within(green, bGreen, 30) && within(blue, bBlue, 30)) {
					Point picked = bArgs.render.pickTile(i-bArgs.render.underlyingOffset.x, j-bArgs.render.underlyingOffset.y);
					picked.translate(-bArgs.currentMouseLoc.x, -bArgs.currentMouseLoc.y);
					if(!result.contains(picked)) {
						result.add(picked);
						
					}
				
				}
				
			}
		}
		return result.toArray(new Point[result.size()]);
		
	}
	//Returns true if value is within (middle-tolerance) <--> (middle+tolerance)
	private boolean within(int value, int middle, int tolerance) {
		return value>=(middle-tolerance) && value <= (middle+tolerance);
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Paint from image";
	}

}
