package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;
import com.team1ofus.apollo.DataTile;
import com.team1ofus.apollo.HashCell;
import com.team1ofus.apollo.TILE_TYPE;

import core.BootstrapperConstants;
import core.DebugManagement;
import core.SEVERITY_LEVEL;

public class CellRenderer {
	int tileWidth;
	int tileHeight;
	Point offset;
	Point underlyingOffset;
	HashCell editCell;
	private boolean campusMapFlag = false;
	Color blue = new Color(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue(), 125);
	Color black = new Color(Color.black.getRed(), Color.black.getGreen(), Color.blue.getRed(), 125);
	Color gray = new Color(Color.gray.getRed(), Color.gray.getGreen(), Color.blue.getRed(), 125);
	Color yellow = new Color(Color.yellow.getRed(), Color.yellow.getGreen(), Color.blue.getRed(), 125);
	Color green = new Color(Color.green.getRed(), Color.green.getGreen(), Color.blue.getRed(), 125);
	Color red = new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 125);
	Color lightpurple = new Color(234, 150, 212, 125);

	public CellRenderer(HashCell inCell) {
		editCell = inCell;
		tileWidth = (int) Math.round(BootstrapperConstants.TILE_WIDTH);
		tileHeight = (int) Math.round(BootstrapperConstants.TILE_HEIGHT);
		offset = new Point(0, 0);
		underlyingOffset = new Point(0, 0);
	}

	public void renderTiles(Graphics g, BufferedImage underlyingImage, int windowWidth, int windowHeight) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, tileWidth * editCell.getWidth(), tileHeight * editCell.getHeight());

		if (campusMapFlag) { // if the campus map is selected, stretch it by 4
								// times
			// BufferedImage img = underlyingImage.getSubimage(offset.x,
			// offset.y, windowWidth, windowHeight);
			// offset.x*-1 - underlyingOffset.x, offset.y*-1 -
			// underlyingOffset.y
			g.drawImage(underlyingImage, offset.x * -1 - underlyingOffset.x, offset.y * -1 - underlyingOffset.y,
					underlyingImage.getWidth(null) * 4, underlyingImage.getHeight(null) * 4, Color.white, null);
		} else {
			g.drawImage(underlyingImage, offset.x * -1 - underlyingOffset.x, offset.y * -1 - underlyingOffset.y,
					Color.white, null);
		}
		int cellWidth = editCell.getWidth();
		int cellHeight = editCell.getHeight();

		// edge bounds to render for map
		int lowerX = (int) (offset.x / BootstrapperConstants.TILE_WIDTH);
		int lowerY = (int) (offset.y / BootstrapperConstants.TILE_HEIGHT);
		int higherX = (int) (offset.x + windowWidth) / BootstrapperConstants.TILE_WIDTH;
		int higherY = (int) (offset.y + windowHeight) / BootstrapperConstants.TILE_HEIGHT;
		if (windowWidth > BootstrapperConstants.TILE_WIDTH * cellWidth) {
			higherX = cellWidth;
		}
		if (windowHeight > BootstrapperConstants.TILE_HEIGHT * cellHeight) {
			higherY = cellHeight;
		}

		for (Point p : editCell.getTiles().keySet()) {
			TILE_TYPE type = editCell.getTile(p);
			if(type == null) {
				continue;
			}
			switch (type) {
			case PEDESTRIAN_WALKWAY:

				g.setColor(gray);
				g.fillRect(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				break;
			case DOOR:
				g.setColor(yellow);
				g.fillRect(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				break;
			case GRASS:
				g.setColor(green);
				g.fillRect(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				break;
			case CONGESTED:
				g.fillRect(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				g.setColor(Color.magenta);
				break;
			case VERTICAL_UP_STAIRS:
				g.setColor(red);
				// g.fillRect(p.x*tileWidth - offset.x, p.y*tileHeight -
				// offset.y, tileWidth, tileHeight);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y + tileHeight);
				// base line for arrow
				g.drawLine(p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight / 2);
				// arrow lines

				break;
			case VERTICAL_DOWN_STAIRS:
				g.setColor(blue);
				// g.fillRect(p.x*tileWidth - offset.x, p.y*tileHeight -
				// offset.y, tileWidth, tileHeight);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y + tileHeight);
				// base line for arrow
				g.drawLine(p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y + tileHeight,
						p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y + tileHeight,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight / 2);
				// arrow lines

				break;
			case HORIZONTAL_LEFT_STAIRS:
				g.setColor(red);

				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight / 2);

				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2,
						p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y);
				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2, 
						p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y + tileHeight);
				break;
			case HORIZONTAL_RIGHT_STAIRS:
				g.setColor(blue);

				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight / 2);

				g.drawLine(p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight / 2,
						p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight / 2,
						p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y + tileHeight);
				break;

			case TREE:
				g.setColor(green);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight);
				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight);

				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight / 2);
				// horizontal line

				break;
			case BUSH:
				g.setColor(green);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight);
				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight);
				g.fillOval(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight / 2);
				// horizontal line

				break;
			case LINOLEUM:
				g.setColor(gray);
				g.drawRect(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth / 2, tileHeight / 2);
				g.drawRect(p.x * tileWidth - offset.x + tileWidth / 2, p.y * tileHeight - offset.y + tileHeight / 2,
						tileWidth / 2, tileHeight / 2);

				break;
			case ELEVATOR:
				g.setColor(red);
				g.drawRect(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth / 2, tileHeight / 2);
				break;
			case IMPASSABLE:
				g.setColor(black);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight);
				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight);

				break;
			case MALE_BATHROOM:

				g.setColor(blue);
				g.fillOval(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				break;
			case FEMALE_BATHROOM:
				g.setColor(red);
				g.fillOval(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				break;
			case UNISEX_BATHROOM:
				g.setColor(red);
				g.fillArc(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight, 0, 180);
				g.setColor(blue);

				g.fillArc(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight, 0, -180);

				break;
			case BENCH:
				g.setColor(black);

				g.fillArc(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight, 0, -180);

				break;
			case UNPLOWED:
				g.setColor(black);
				g.fillRect(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				//g.drawArc(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight / 2, tileWidth,
				//		tileHeight / 2, 0, 180);

				break;
			case CLASSROOM:
				g.setColor(lightpurple);
				g.fillRect(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				break;
			case EXTRA_TILE_TYPE_1:
				// ROAD TILE
				g.setColor(black);
				g.fillOval(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y, tileWidth, tileHeight);
				g.drawLine(p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x, p.y * tileHeight - offset.y + tileHeight);
				g.drawLine(p.x * tileWidth - offset.x, p.y * tileHeight - offset.y,
						p.x * tileWidth - offset.x + tileWidth, p.y * tileHeight - offset.y + tileHeight);

				break;
			default:
				break;
			}

			g.setColor(black);
			
			
		}
		drawGridLines(g, windowWidth, windowHeight);
	}

	private void drawGridLines(Graphics g, int windowWidth, int windowHeight) {
		int cellWidth = editCell.getWidth();
		int cellHeight = editCell.getHeight();
		int lowerX = (int) Math.ceil(offset.x / BootstrapperConstants.TILE_WIDTH);
		int lowerY = (int) Math.ceil(offset.y / BootstrapperConstants.TILE_HEIGHT);
		int higherX = (int) Math.ceil((offset.x + windowWidth) / BootstrapperConstants.TILE_WIDTH);
		int higherY = (int) Math.ceil((offset.y + windowHeight) / BootstrapperConstants.TILE_HEIGHT);
		lowerX = 0;
		lowerY = 0;
		higherX = editCell.getWidth();
		higherY = editCell.getHeight();
		//override, most maps are plenty small
		if (windowWidth > BootstrapperConstants.TILE_WIDTH * cellWidth) {
			higherX = cellWidth;
		}
		if (windowHeight > BootstrapperConstants.TILE_HEIGHT * cellHeight) {
			higherY = cellHeight;
		}

		int right = Math.min(windowWidth, editCell.getWidth()*BootstrapperConstants.TILE_WIDTH);
		int bottom = Math.min(windowWidth, editCell.getHeight()*BootstrapperConstants.TILE_HEIGHT);
		//DebugManagement.writeNotificationToLog("LowerX " + lowerX + " LowerY " + lowerY  + " higherX" + higherX + " higherY " + higherY);
		g.setColor(black);
		for (int i = lowerX; i < higherX; i++) {
			g.drawLine(0, i*BootstrapperConstants.TILE_HEIGHT - offset.y, right, i*BootstrapperConstants.TILE_HEIGHT - offset.y);
			

		
		}
		for (int j = lowerY; j < higherY; j++) {
			
			g.drawLine(j*BootstrapperConstants.TILE_WIDTH - offset.x, 0, j*BootstrapperConstants.TILE_WIDTH - offset.x, bottom);
			
		}
/*		for (int j = lowerY; j < higherY; j++) {
			
			g.drawRect(i*BootstrapperConstants.TILE_WIDTH - offset.x, j*BootstrapperConstants.TILE_HEIGHT - offset.y, BootstrapperConstants.TILE_WIDTH, BootstrapperConstants.TILE_HEIGHT);
		}*/
	}

	public Point pickTile(int mouseX, int mouseY) {
		int x = (int) (Math.round((mouseX + offset.x) / tileWidth));
		int y = (int) (Math.round((mouseY + offset.y) / tileHeight));
		return new Point(x, y);
	}

	public void editTile(int x, int y, DataTile swapped) {
			editCell.setTile(x, y, swapped.getType());

	}

	public void incrementOffset(int dx, int dy, int windowWidth, int windowHeight) {
		// some optimizations to be made here
		offset.translate(dx, dy);

		if (offset.x < 0) {
			offset.x = 0;
		} else if (offset.x > editCell.getWidth() * tileWidth - windowWidth) {
			offset.x = editCell.getWidth() * tileWidth - windowWidth;
		}
		if (offset.y < 0) {
			offset.y = 0;
		} else if (offset.y > editCell.getHeight() * tileHeight - windowHeight) {
			offset.y = editCell.getHeight() * tileHeight - windowHeight;

		}
		if (offset.x < 0) {
			offset.x = 0;
		}
		if (offset.y < 0) {
			offset.y = 0;
		}
		DebugManagement.writeNotificationToLog("Current offest " + offset);
		DebugManagement.writeNotificationToLog("Window width " + windowWidth + " Window height " + windowHeight);
	}

	public void incrementUnderlyingOffset(int dx, int dy, int windowWidth, int windowHeight) {
		// some optimizations to be made here
		underlyingOffset.translate(dx, dy);

	}

	public void setCampusMapFlag(boolean flag) {
		campusMapFlag = flag;
	}
}
