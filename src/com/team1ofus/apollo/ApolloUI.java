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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLayeredPane;

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
	int mode = 0;
	ArrayList<Image> imageSelection = new ArrayList<Image>();
	ArrayList<String> imageNames = new ArrayList<String>();

	private Box horizontalBox;
	public ApolloUI() {
		imageSelection = new ArrayList<Image>(); // editor image selection;
													// could use abstraction but
													// we'll deal with that
													// later.
		loadImages();
		events = new HumanInteractionEventObject();
		painter = new PaintTool();

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void initialize(Cell cellToEdit) {
		buildControls(cellToEdit);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});

		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});

		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition = new Point(e.getX(), e.getY());

				if (SwingUtilities.isLeftMouseButton(e)) {
					// left is pressed!
					doPaint(panel, e);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					// left is pressed!
					doPaint(panel, e);
				} else if (SwingUtilities.isRightMouseButton(e)) {

					// safety check

					if (mousePosition != null) {
						int x = (int) (-0.5 * (e.getX() - mousePosition.getX()));
						int y = (int) (-0.5 * (e.getY() - mousePosition.getY()));
						// DebugManagement.writeNotificationToLog("Dragging
						// occurred, dx dy " + x + " , " + y);
						panel.render.incrementOffset(x, y, panel.getWidth(), panel.getHeight());
						// panel.setOffset(panel.render.offset);
						repaintPanel();
						mousePosition = e.getPoint();
					} else {
						mousePosition = new Point(e.getX(), e.getY());
					}

				}
			}

		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mode == 0)
					doPaint(panel, e);
				else if (mode == 1) {
					JOptionPane namedialog = new JOptionPane();
					String s = (String) namedialog.showInputDialog(new JFrame(), "Enter location name:", "Location",
							JOptionPane.PLAIN_MESSAGE, null, null, "");
					System.out.println(s);
					// NEEDS TO BE SENT SOMEWHERE
					// TODO: send this information somewhere:
					System.out.println(s);
				}
			}
		});

		// this loads the default image automagically and needs to be changed
		// when we implement this more formally

		tiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// selection box changed for tiles
				painter.selectTileType((TILE_TYPE) tiles.getSelectedItem());
				panel.grabFocus();
			}
		});
		brushes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// selection box changed for brushes
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
				// when a selection is made.
				panel.currentImage = imageSelection.get(underlyingImageSelection.getSelectedIndex());
				makePanelDirty();
				panel.grabFocus();
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

	private void loadImages() {
		File dir = new File(".");
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".jpg"))) {
				imageNames.add(file.getName());
				imageSelection.add(loadImage(file.getName()));
			}
		}
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
		switch (e.getKeyCode()) {
		// some optimizations to be made here
		case KeyEvent.VK_LEFT:
			panel.render.incrementOffset(-1 * scrollSpeed, 0, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_RIGHT:
			panel.render.incrementOffset(scrollSpeed, 0, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_DOWN:
			panel.render.incrementOffset(0, scrollSpeed, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_UP:
			panel.render.incrementOffset(0, -1 * scrollSpeed, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_A:
			panel.render.incrementUnderlyingOffset(-1 * scrollSpeed, 0, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_D:
			panel.render.incrementUnderlyingOffset(scrollSpeed, 0, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_S:
			panel.render.incrementUnderlyingOffset(0, scrollSpeed, panel.getWidth(), panel.getHeight());
			break;
		case KeyEvent.VK_W:
			panel.render.incrementUnderlyingOffset(0, -1 * scrollSpeed, panel.getWidth(), panel.getHeight());
			break;

		default:
			break;
		}

		makePanelDirty();
		lblOffset.setText(panel.render.offset.getX() + "," + panel.render.offset.getY());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		layeredPane.paintComponents(g);
	}

	private void buildControls(Cell cellToEdit) {

		frame = new JFrame("Apollo");
		frame.setTitle(cellToEdit.getID());
		frame.setResizable(false);
		frame.getContentPane().setBounds(100, 100, 969, 596);

		frame.setBounds(100, 100, 969, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new DrawPane(cellToEdit);
		frame.setVisible(true);
		panel.setCurrentImage(imageSelection.get(0));

		horizontalBox = Box.createHorizontalBox();
		frame.getContentPane().add(horizontalBox, BorderLayout.CENTER);
		layeredPane = new JLayeredPane();
		horizontalBox.add(layeredPane);
		layeredPane.setBackground(Color.CYAN);

		panel = new DrawPane(cellToEdit);
		panel.setBounds(0, 0, 691, 560);
		textPanel = new TextPane();
		layeredPane.add(textPanel);
		textPanel.setBounds(0, 0, 691, 560);
		layeredPane.add(panel);
		layeredPane.setBounds(0, 0, 691, 560);
		
		layeredPane.setComponentZOrder(textPanel, 0);
		layeredPane.setComponentZOrder(panel, 1);
		layeredPane.setOpaque(true);
		windowUI = new JPanel();
		frame.getContentPane().add(windowUI, BorderLayout.WEST);
		frame.getContentPane().add(windowUI, BorderLayout.EAST);
		windowUI.setBackground(Color.RED);
		windowUI.setForeground(Color.RED);
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
		brushes.setModel(new DefaultComboBoxModel(new String[] { "Single Tile", "2 x 2 Square", "3 x 3 Square" }));
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
		paintMode.setModel(new DefaultComboBoxModel(new String[] { "Tile painting", "Location Painting" }));
		verticalBox.add(paintMode);
		tiles.setAlignmentY(5.0f);
		windowUI.setBounds(691, 0, 150, 560);

	}

	private void repaintPanel() {
		layeredPane.repaint();

	}

	private void setMode(int modeIndex) {
		mode = modeIndex;
		if (modeIndex == 0) {
			underlyingImageSelection.setEnabled(true);
			brushes.setEnabled(true);
			tiles.setEnabled(true);
		} else if (modeIndex == 1) {
			underlyingImageSelection.setEnabled(false);
			brushes.setEnabled(false);
			tiles.setEnabled(false);
		}
	}
}
