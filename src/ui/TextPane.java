package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.function.Predicate;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.team1ofus.apollo.EntryPoint;
import com.team1ofus.apollo.HashCell;
import com.team1ofus.apollo.LocationInfo;

import core.BootstrapperConstants;
import core.DebugConsole;
import core.DebugManagement;
import core.SEVERITY_LEVEL;

/*
 * Intended to be efficient at drawing text in a standardized fashion over the screen.
 * Add support for offset needed as a future task.
 */
public class TextPane extends JPanel {
	ArrayList<TextLocation> locations = new ArrayList<TextLocation>();
	public Point currentTileLocation; //automatically updated.
	private Point offset = new Point(0,0);
	public TextPane(HashCell cellToEdit) {
		setOpaque(true);
		loadFromCell(cellToEdit);
	}
	private void loadFromCell(HashCell cellToEdit) {
		for(LocationInfo l : cellToEdit.getListedLocations()) {
			if(l.getCellReference() == null) {
				//generic location, it's black
				//relates to TFS bug #242.
				//TODO: fix magic number associations
				locations.add(new TextLocation(l.getAliases(), l.getLocation(), Color.BLACK));
				
			} else {
				locations.add(new TextLocation(l.getAliases(), l.getLocation(), Color.BLUE));
				
			}    
			DebugManagement.writeNotificationToLog("Loaded a new LocationInfo from the cell at " + l.getLocation().toString());
		}
		for(EntryPoint e : cellToEdit.getEntryPoints()) {
			locations.add(new TextLocation(e.getName(), e.getLocation(), Color.GREEN));
		}
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		this.paintComponents(g);
		for(TextLocation l : locations) {
			g.setColor(l.drawnColor);
			//we now need to center the text.
			//double yShift = g.getFont().getSize()/2;
			for(int i=0; i<l.lines.size(); i++) {
				
				//double xShift = 0;
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				int stringLength;
				if(metrics == null) {
					DebugManagement.writeLineToLog(SEVERITY_LEVEL.ERROR, "FontMetrics was unable to be loaded in TextPane.");
					stringLength = 0;
				} else {
					stringLength = (int) metrics.getStringBounds(l.lines.get(i), g).getWidth();
					
				}
				
				int start = stringLength/2;
				g.drawString(l.lines.get(i), l.location.x*BootstrapperConstants.TILE_WIDTH-(int)start+BootstrapperConstants.TILE_WIDTH/2-offset.x, l.location.y*BootstrapperConstants.TILE_HEIGHT-offset.y+g.getFont().getSize()*i);
				
			}
		}
		showConsole(g);
	}
	public void doPopupBoxes(Graphics g) {
		
	}
	public void addLocation(TextLocation input) {
		removeLocation(input.location);
		DebugManagement.writeNotificationToLog("Created new location at " + input.location.toString());
		locations.add(input);
	}
	//remove locations if they already exist.
	public void removeLocation(Point input) {
		locations.removeIf(isEqual(input));
	}
	//appends line to the input if it exists
	public void updateLocation(String input, Point location) {
	
		for(int i=0; i < locations.size(); i++) {
			if(locations.get(i).location.equals(location)) {
				//matching location! append the text.
				DebugManagement.writeNotificationToLog("Appended the text " + input + " at location " + location.toString());
				locations.get(i).addString(input);
				return;
			}
		}
		DebugManagement.writeLineToLog(SEVERITY_LEVEL.WARNING, "Attempted to append when there was nothing to append.");
		//can't have reached here if the point already existed.
		//nothing happens
		//locations.add(new TextLocation(input, (new Point(location.x, location.y)), Color.BLACK));
	}
	public static Predicate<TextLocation> isEqual(Point filter) {
	    return p -> p.location.equals(filter);
	}
	private void showConsole(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 8));
		if(DebugConsole.getEntries().size() > 1000) {
			DebugConsole.getEntries().clear();
		}
		if(DebugConsole.getEntries().size() <= 0) {
			return;
		}
		int count = 0; //number of lines drawn.
		if(DebugConsole.getEntries().size() < BootstrapperConstants.LINES_TO_SCREEN) {
			//There are fewer lines on the list, so just start at the end.
			
			for(int i=DebugConsole.getEntries().size()-1; i>0;i--) {
				g.drawString(DebugConsole.getEntries().get(i), 0, g.getFont().getSize()*count);
				count++;
			}
		} else {
			//more, start at the end up to LINES_TO_SCREEN lower
			for(int i=DebugConsole.getEntries().size()-1; i>DebugConsole.getEntries().size()-1-BootstrapperConstants.LINES_TO_SCREEN;i--) {
				g.drawString(DebugConsole.getEntries().get(i), 0, g.getFont().getSize()*count);
				count++;
			}
		}
	
		//draw the number of lines onscreen that are specified, but don't remove them
	}
	
	public void setOffset(Point offset) {
		this.offset = offset;
		
	}
}
