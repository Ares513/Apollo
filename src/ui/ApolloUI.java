package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JSpinner;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import com.team1ofus.apollo.HashCell;
import com.team1ofus.apollo.TILE_TYPE;

import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import ui.*;
import core.*;
import events.*;
import data.*;

import core.DebugManagement;
import events.HumanInteractionEventObject;

import java.awt.Toolkit;

public class ApolloUI extends JPanel {
	private JFrame frame;
	private JPanel windowUI;
	private Point mousePosition;
	private PaintTool painter;
	private JComboBox tiles;
	private JComboBox brushes;
	private Box verticalBox;
	private JLabel lblOffset;
	DrawPane panel;
	TextPane textPanel;
	int scrollSpeed = 10;
	private Box verticalBox_1;
	private JButton saveButton;
	public HumanInteractionEventObject events;
	private JComboBox underlyingImageSelection;
	private JComboBox paintMode;
	private JLayeredPane layeredPane;
	private NameChecker nameChecker;
	int mode = 0;
	ArrayList<BufferedImage> imageSelection = new ArrayList<BufferedImage>();
	ArrayList<String> imageNames = new ArrayList<String>();

	private Box horizontalBox;
	public ApolloUI(ArrayList<String> imageNames, ArrayList<BufferedImage> imageSelection) {
		this.imageSelection = imageSelection;											// could use abstraction but
		this.imageNames = imageNames; 				// we'll deal with that
													// later.
		events = new HumanInteractionEventObject();
		painter = new PaintTool();

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void initialize(HashCell HashCellToEdit) {
		buildControls(HashCellToEdit);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				System.out.println("test");
				doOffsetCalc(e, lblOffset);
			}
		});
		textPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				textPanel.grabFocus();
				DebugManagement.writeNotificationToLog("Offset is " + panel.render.offset);
				doOffsetCalc(e, lblOffset);
			}
		});
		layeredPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});

		layeredPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition = new Point(e.getX(), e.getY());
				if (SwingUtilities.isLeftMouseButton(e)) {
					// left is pressed!
					if(mode == 0) {
						doPaint(panel, e);
						//no support for mouse held tile painting
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && mode == 0) {
					// left is pressed!
					doPaint(panel, e);
				} else if (SwingUtilities.isRightMouseButton(e)) {

					// safety check

					if (mousePosition != null) {
						int x = (int) (-0.5 * (e.getX() - mousePosition.getX()));
						int y = (int) (-0.5 * (e.getY() - mousePosition.getY()));
						// DebugManagement.writeNotificationToLog("Dragging
						// occurred, dx dy " + x + " , " + y);
						panel.render.incrementOffset(x, y, layeredPane.getWidth(), layeredPane.getHeight());
						textPanel.setOffset(panel.render.offset);
						repaintPanel();
						mousePosition = e.getPoint();
					} else {
						mousePosition = new Point(e.getX(), e.getY());
					}

				}
			}

		});
		layeredPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point picked = panel.render.pickTile(e.getX(), e.getY());
				if (mode == 0)
					doPaint(panel, e);
				else if (mode == 1) {					
					//overwrite mode. Existing points will be deleted and replaced.
					JOptionPane namedialog = new JOptionPane();
					String s = (String) namedialog.showInputDialog(new JFrame(), "Enter location name:", "Location",
							JOptionPane.PLAIN_MESSAGE, null, null, "");
					if(s == null || s.trim().length() == 0) {
						//invalid.
						DebugManagement.writeLineToLog(SEVERITY_LEVEL.SEVERE, "Rejected empty input.");
						return;
					}
							
					DebugManagement.writeNotificationToLog("Text point added.");
					textPanel.addLocation(new TextLocation(s, picked, Color.BLACK));
					HashCellToEdit.addLocation(new LocationInfo(s, picked));												
					repaintPanel();
				
					
				} else if(mode == 2) {
					JOptionPane namedialog = new JOptionPane();
					String s = (String) namedialog.showInputDialog(new JFrame(), "Amend location name", "Location",
							JOptionPane.PLAIN_MESSAGE, null, null, "");
					DebugManagement.writeNotificationToLog("Text point amended.");
					textPanel.updateLocation(s, panel.render.pickTile(e.getX(), e.getY()));
					HashCellToEdit.appendLocation(picked, s);
					repaintPanel();
				} else if(mode == 3) {
					//creating HashCell points
					JOptionPane namedialog = new JOptionPane();/*
					String s = (String) namedialog.showInputDialog(new JFrame(), "Enter location name:", "Location",
							JOptionPane.PLAIN_MESSAGE, null, null, "");
					if(s == null || s.trim().length() == 0) {
						//invalid.
						DebugManagement.writeLineToLog(SEVERITY_LEVEL.SEVERE, "Rejected empty input.");
						return;
					}
					DebugManagement.writeNotificationToLog("HashCell point added.");*/
					
					String reference = null;
					boolean valid = false;
					//The while loop below will run infinitely until the user puts in a valid input for HashCell name
					while(valid == false){
						 reference = (String) namedialog.showInputDialog(new JFrame(), "Enter HashCell Name", "HashCell ID",JOptionPane.PLAIN_MESSAGE, null, null, "");
						//Checks HashCell names
						 nameChecker = new NameChecker(reference,false); 	
						if(reference == null || reference.trim().length() == 0) {
							//invalid.
							DebugManagement.writeLineToLog(SEVERITY_LEVEL.SEVERE, "Rejected empty input.");
							return;
							
						}						
						else if(nameChecker.isNameValid() == false){
							DebugManagement.writeLineToLog(SEVERITY_LEVEL.FATAL, "HashCell Name is invalid");
							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(new JFrame(), "HashCell Name is invalid");
							
						}
						else{
							valid = true;
						}
					}
					
					String entryReference = null;
					valid = false;
					
					while(valid == false){
						 entryReference = (String) namedialog.showInputDialog(new JFrame(), "Enter entry name:", "Location",
								JOptionPane.PLAIN_MESSAGE, null, null, "");
						 nameChecker = new NameChecker(entryReference,true);
						if(entryReference == null || entryReference.trim().length() == 0) {
							//invalid.
							DebugManagement.writeLineToLog(SEVERITY_LEVEL.SEVERE, "Rejected empty input.");
							return;
						
						}
						else if(nameChecker.isNameValid() == false){
							DebugManagement.writeLineToLog(SEVERITY_LEVEL.FATAL, "Entry Point Name is invalid");
							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(new JFrame(), "Entry Point is invalid");
						}
						else{
							valid = true;
						}
					}
					
					//name of the entrance it is at on the other HashCell.
					HashCellToEdit.addLocation(new LocationInfo(entryReference, picked, reference, entryReference));
					textPanel.addLocation(new TextLocation(entryReference, panel.render.pickTile(e.getX(), e.getY()), Color.BLUE));
					repaintPanel();
				} else if(mode == 4) {
					//creating entry points for other HashCell points
					JOptionPane namedialog = new JOptionPane();
					
					String s = null;
					boolean valid = false;
					
					while(valid == false){
						 s = (String) namedialog.showInputDialog(new JFrame(), "Enter entry name:", "Location",
								JOptionPane.PLAIN_MESSAGE, null, null, "");
						 nameChecker = new NameChecker(s,true);
						if(s == null || s.trim().length() == 0) {
							//invalid.
							DebugManagement.writeLineToLog(SEVERITY_LEVEL.SEVERE, "Rejected empty input.");
							return;
						
						}
						else if(nameChecker.isNameValid() == false){
							DebugManagement.writeLineToLog(SEVERITY_LEVEL.FATAL, "Entry Point Name is invalid");
							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(new JFrame(), "Entry Point is invalid");
						}
						else{
							valid = true;
						}
					}
					
					DebugManagement.writeNotificationToLog("Text point added.");
					//name of the entryReference
					HashCellToEdit.addEntryPoint(new EntryPoint(s, picked));
					//this would indicate that at point picked, there is an entry label named s.
					//Other HashCells could reference it, saying that they are accessing HashCell (this HashCell), entry label s.
					
					textPanel.removeLocation(panel.render.pickTile(e.getX(), e.getY()));
					textPanel.addLocation(new TextLocation(s, panel.render.pickTile(e.getX(), e.getY()), Color.GREEN));
					repaintPanel();
				} else if(mode == 5) {
					//remove mode
					HashCellToEdit.removePointLocation(picked);
					HashCellToEdit.removeEntry(picked);
					textPanel.removeLocation(picked);
					DebugManagement.writeNotificationToLog("Removed at " + picked);
					repaintPanel();
				}

			}
		});

		tiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// selection box changed for tiles
				painter.selectTileType((TILE_TYPE) tiles.getSelectedItem());
				textPanel.grabFocus();
			}
		});
		brushes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// selection box changed for brushes
				painter.selectBrush(brushes.getSelectedIndex());
				textPanel.grabFocus();

			}
		});
		windowUI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});
		verticalBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);

			}
		});
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPanel.grabFocus();
				events.triggerSave(HashCellToEdit);
			}
		});
		underlyingImageSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// when a selection is made.
				System.out.println(underlyingImageSelection.getSelectedItem());
				if(underlyingImageSelection.getSelectedItem().equals("WPI_campus_map_resized_quarter.jpg")) //special handling for campus map
					panel.setCampusMapFlag(true);
				else
					panel.setCampusMapFlag(false);
				panel.setCurrentImage(imageSelection.get(underlyingImageSelection.getSelectedIndex()));
				makePanelDirty();
				textPanel.grabFocus();
			}
		});
		paintMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(paintMode.getSelectedIndex());
			}
		});

	}

	public Point getMouseLocation() {
		return mousePosition;
	}

	private void makePanelDirty() {
		// panel.repaint(0, 0, panel.getWidth() + 100, panel.getHeight() + 100);
		repaintPanel();
	}

	private void doPaint(DrawPane panel, MouseEvent e) {
		// PICK TILES HERE
		Point picked = panel.render.pickTile(e.getX(), e.getY());
		painter.applyBrush(panel.render, picked.x, picked.y);

		// panel.render.editTile(picked.x, picked.y, new
		// DataTile(painter.getTileToPaint()));

		makePanelDirty();
	}


	private void doOffsetCalc(KeyEvent e, JLabel offsetLbl) {
		switch (e.getKeyCode()) {
		// some optimizations to be made here
		case KeyEvent.VK_LEFT:
			panel.render.incrementOffset(-1 * scrollSpeed, 0, layeredPane.getWidth(), layeredPane.getHeight());
			break;
		case KeyEvent.VK_RIGHT:
			panel.render.incrementOffset(scrollSpeed, 0, layeredPane.getWidth(), layeredPane.getHeight());
			break;
		case KeyEvent.VK_DOWN:
			panel.render.incrementOffset(0, scrollSpeed, layeredPane.getWidth(), layeredPane.getHeight());
			break;
		case KeyEvent.VK_UP:
			panel.render.incrementOffset(0, -1 * scrollSpeed, layeredPane.getWidth(), layeredPane.getHeight());
			break;
		case KeyEvent.VK_A:
			panel.render.incrementUnderlyingOffset(-1 * scrollSpeed, 0, layeredPane.getWidth(), layeredPane.getHeight());
			break;
		case KeyEvent.VK_D:
			panel.render.incrementUnderlyingOffset(scrollSpeed, 0, layeredPane.getWidth(), layeredPane.getHeight());
			break;
		case KeyEvent.VK_S:
			panel.render.incrementUnderlyingOffset(0, scrollSpeed, layeredPane.getWidth(), layeredPane.getHeight());
			break;
		case KeyEvent.VK_W:
			panel.render.incrementUnderlyingOffset(0, -1 * scrollSpeed, layeredPane.getWidth(), layeredPane.getHeight());
			break;

		default:
			break;
		}
		textPanel.setOffset(panel.render.offset);
		makePanelDirty();
		lblOffset.setText(panel.render.offset.getX() + "," + panel.render.offset.getY());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		layeredPane.paintComponents(g);
	}

	private void buildControls(HashCell HashCellToEdit) {

		frame = new JFrame("Apollo");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ApolloUI.class.getResource("/com/team1ofus/apollo/hammer-geology-512.png")));
		frame.setBackground(UIManager.getColor("Button.focus"));
		frame.setTitle(HashCellToEdit.getID());
		frame.setResizable(false);
		frame.getContentPane().setBounds(100, 100, 969, 596);

		frame.setBounds(100, 100, 1439, 1023);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new DrawPane(HashCellToEdit);
		frame.setVisible(true);
		panel.setCurrentImage(imageSelection.get(0));

		horizontalBox = Box.createHorizontalBox();
		frame.getContentPane().add(horizontalBox, BorderLayout.CENTER);
		layeredPane = new JLayeredPane();
		horizontalBox.add(layeredPane);
		layeredPane.setBackground(Color.CYAN);
		windowUI = new JPanel();
		panel = new DrawPane(HashCellToEdit);
		textPanel = new TextPane(HashCellToEdit);
		layeredPane.add(textPanel);
		
		layeredPane.add(panel);
		windowUI.setBounds(691, 0, 150, 560);
		layeredPane.setBounds(0, 0, frame.getBounds().width - windowUI.getWidth(), frame.getBounds().height);
		textPanel.setBounds(layeredPane.getBounds());
		panel.setBounds(layeredPane.getBounds());
		
		layeredPane.setComponentZOrder(textPanel, 0);
		layeredPane.setComponentZOrder(panel, 1);
		layeredPane.setOpaque(true);
		
		frame.getContentPane().add(windowUI, BorderLayout.WEST);
		frame.getContentPane().add(windowUI, BorderLayout.EAST);
		windowUI.setBackground(UIManager.getColor("CheckBox.light"));
		windowUI.setForeground(Color.RED);
		windowUI.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
				verticalBox_1 = Box.createVerticalBox();
				windowUI.add(verticalBox_1);

		verticalBox = Box.createVerticalBox();
		lblOffset = new JLabel("0,0");
		lblOffset.setFont(new Font("Tahoma", Font.PLAIN, 20));
		verticalBox.add(lblOffset);

		brushes = new JComboBox();

		verticalBox.add(brushes);
		brushes.setModel(new DefaultComboBoxModel(new String[] { "Single Tile", "2 x 2 Square", "3 x 3 Square", "5 x 5 Square","7 x 7 Square"}));
		brushes.setAlignmentY(Component.TOP_ALIGNMENT);
		windowUI.add(verticalBox);

		underlyingImageSelection = new JComboBox();

		underlyingImageSelection.setAlignmentY(5.0f);
		for(String s : imageNames) {
			underlyingImageSelection.addItem(s);
		}
		verticalBox.add(underlyingImageSelection);
		tiles = new JComboBox();
		verticalBox.add(tiles);
		tiles.setAlignmentY(5.0f);
		tiles.setModel(new DefaultComboBoxModel(TILE_TYPE.values()));

		paintMode = new JComboBox();
		paintMode.setModel(new DefaultComboBoxModel(new String[] {"Tile painting", "Location Painting", "Append Location Painting", "HashCell Reference Painting", "Entry Point Painting", "Delete Locations, References And Points"}));
		verticalBox.add(paintMode);
		tiles.setAlignmentY(5.0f);
		
				saveButton = new JButton("Save");
				verticalBox.add(saveButton);
				
						saveButton.setVerticalAlignment(SwingConstants.TOP);
						saveButton.setToolTipText("Save to a file.");
		
		textPanel.grabFocus();
	}

	private void repaintPanel() {
		layeredPane.repaint(layeredPane.getBounds());

	}

	private void setMode(int modeIndex) {
		mode = modeIndex;
		if (modeIndex == 0) {
			underlyingImageSelection.setEnabled(true);
			brushes.setEnabled(true);
			tiles.setEnabled(true);
		} else if (modeIndex >= 1) {
			underlyingImageSelection.setEnabled(false);
			brushes.setEnabled(false);
			tiles.setEnabled(false);
		}
		textPanel.grabFocus();
	}
}
