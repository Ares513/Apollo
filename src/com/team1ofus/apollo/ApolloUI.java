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
import javax.swing.SwingUtilities;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.Box;
import java.awt.Font;
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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ApolloUI {
	private JFrame frame;
	private JPanel windowUI;
	private Point mousePosition;
	private PaintTool painter;
	private JComboBox tiles;
	private JComboBox brushes;
	private Box verticalBox;
	private JLabel lblOffset;
	DrawPane panel;
	int scrollSpeed = 10;
	private Box verticalBox_1;
	private JButton saveButton;
	public HumanInteractionEventObject events;
	private JComboBox underlyingImageSelection;
	ArrayList<Image> imageSelection = new ArrayList<Image>();
	public ApolloUI() {
		imageSelection = new ArrayList<Image>(); //editor image selection; could use abstraction but we'll deal with that later.
		loadImages();
		events = new HumanInteractionEventObject();
		painter = new PaintTool();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize(Cell cellToEdit) {
		buildControls(cellToEdit);
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});


		
		
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition = new Point(e.getX(), e.getY());
				 
				if(SwingUtilities.isLeftMouseButton(e)) {
					//left is pressed!
					doPaint(panel, e);
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					//left is pressed!
					doPaint(panel, e);
				}
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doPaint(panel, e);
			}
		});
		tiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//selection box changed for tiles
				painter.selectTileType((TILE_TYPE) tiles.getSelectedItem());
				panel.grabFocus();
			}
		});
		brushes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//selection box changed for brushes
				painter.selectBrush(brushes.getSelectedIndex());
				panel.grabFocus();
				
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
				panel.grabFocus();
				events.triggerSave(cellToEdit);
			}
		});
		underlyingImageSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//when a selection is made.
				panel.currentImage = imageSelection.get(underlyingImageSelection.getSelectedIndex());
				makePanelDirty();
				panel.grabFocus;
			}
		});
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		panel.grabFocus();
	}
	public Point getMouseLocation() {
		return mousePosition;
	}
	private void makePanelDirty() {
		panel.repaint(0, 0, panel.getWidth() + 100, panel.getHeight() + 100);
	}
	private void doPaint(DrawPane panel, MouseEvent e) {
		//PICK TILES HERE
		Point picked = panel.render.pickTile(e.getX(), e.getY());
		painter.applyBrush(panel.render, picked.x, picked.y);
		
		//panel.render.editTile(picked.x, picked.y, new DataTile(painter.getTileToPaint()));
		
		makePanelDirty();
	}
	private void loadImages() {
		imageSelection.add(loadImage("stratton_hall-page1.jpg"));
		imageSelection.add(loadImage("stratton_hall-page2.jpg"));
		imageSelection.add(loadImage("stratton_hall-page3.jpg"));
		imageSelection.add(loadImage("stratton_hall-page4.jpg"));
	}
	private BufferedImage loadImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
			return img;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
		
	}
	private void doOffsetCalc(KeyEvent e, JLabel offsetLbl) {
		switch(e.getKeyCode()) {
		//some optimizations to be made here
		case KeyEvent.VK_LEFT:
			panel.render.incrementOffset(-1*scrollSpeed, 0, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_RIGHT:
			panel.render.incrementOffset(scrollSpeed, 0, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_DOWN:
			panel.render.incrementOffset(0, scrollSpeed, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_UP:
			panel.render.incrementOffset(0, -1*scrollSpeed, panel.getWidth(), panel.getHeight());
			break;
		default:
			break;
		}
		
		makePanelDirty();
		lblOffset.setText(panel.render.offset.getX() + "," + panel.render.offset.getY());
	}
	private void buildControls(Cell cellToEdit) {
		frame = new JFrame();
		frame.setTitle(cellToEdit.getID());
		frame.setResizable(false);
		panel  = new DrawPane(cellToEdit);
		//this loads the default image automagically and needs to be changed when we implement this more formally
		panel.setCurrentImage(imageSelection.get(0));
		//
		frame.setBounds(100, 100, 969, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		windowUI = new JPanel();

		windowUI.setBackground(Color.RED);
		windowUI.setForeground(Color.RED);
		frame.getContentPane().add(windowUI, BorderLayout.EAST);
		windowUI.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		verticalBox_1 = Box.createVerticalBox();
		windowUI.add(verticalBox_1);
		
		saveButton = new JButton("Save");

		saveButton.setVerticalAlignment(SwingConstants.TOP);
		saveButton.setToolTipText("Save to a file.");
		verticalBox_1.add(saveButton);
		
		verticalBox = Box.createVerticalBox();
		lblOffset = new JLabel("0,0");
		lblOffset.setFont(new Font("Tahoma", Font.PLAIN, 20));
		verticalBox.add(lblOffset);


		
		brushes = new JComboBox();

		verticalBox.add(brushes);
		brushes.setModel(new DefaultComboBoxModel(new String[] {"Single Tile", "2 x 2 Square", "3 x 3 Square"}));
		brushes.setAlignmentY(Component.TOP_ALIGNMENT);
		windowUI.add(verticalBox);
		
		underlyingImageSelection = new JComboBox();

		underlyingImageSelection.setAlignmentY(5.0f);
		underlyingImageSelection.addItem("stratton_hall-page1.jpg");
		underlyingImageSelection.addItem("stratton_hall-page2.jpg");
		underlyingImageSelection.addItem("stratton_hall-page3.jpg");
		underlyingImageSelection.addItem("stratton_hall-page4.jpg"); //will need to link this later
		
		verticalBox.add(underlyingImageSelection);
		tiles = new JComboBox();
		verticalBox.add(tiles);
		tiles.setAlignmentY(5.0f);
		tiles.setModel(new DefaultComboBoxModel(TILE_TYPE.values()));
	}
}
