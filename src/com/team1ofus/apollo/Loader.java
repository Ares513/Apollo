package com.team1ofus.apollo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystemException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.JMenu;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JLabel;

public class Loader extends JDialog {
	public LoaderInteractionEventObject events;
	private JTextField widthInput;
	private JTextField heightInput;
	private JTextField nameInput;
	private JTextField displayName;
	/**
	 * Create the dialog.
	 */
	public Loader(ArrayList<Cell> allCells, ArrayList<String> imageNames, ArrayList<BufferedImage> images) {
		events = new LoaderInteractionEventObject();
		setBounds(100, 100, 597, 326);
		getContentPane().setLayout(new BorderLayout());
			JPanel pane = new JPanel();
			pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pane, BorderLayout.SOUTH);
				
				displayName = new JTextField();
				displayName.setColumns(10);
				pane.add(displayName);
				JComboBox mapChooser = new JComboBox();
				mapChooser.setModel(new DefaultComboBoxModel(getNames(allCells)));
				pane.add(mapChooser);
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						events.selectionMade(allCells.get(mapChooser.getSelectedIndex()), allCells);
						
					}
				});
				okButton.setActionCommand("OK");
				pane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				JSplitPane splitPane = new JSplitPane();
				getContentPane().add(splitPane, BorderLayout.CENTER);
				
				Box verticalBox_1 = Box.createVerticalBox();
				splitPane.setLeftComponent(verticalBox_1);
				
				Box verticalBox_2 = Box.createVerticalBox();
				splitPane.setRightComponent(verticalBox_2);
				
				Box horizontalBox_1 = Box.createHorizontalBox();
				verticalBox_2.add(horizontalBox_1);
				
				nameInput = new JTextField();
				horizontalBox_1.add(nameInput);
				nameInput.setColumns(10);
				
				Component horizontalStrut_5 = Box.createHorizontalStrut(20);
				horizontalBox_1.add(horizontalStrut_5);
				
				Component horizontalStrut_4 = Box.createHorizontalStrut(20);
				horizontalBox_1.add(horizontalStrut_4);
				
				Component horizontalStrut_3 = Box.createHorizontalStrut(20);
				horizontalBox_1.add(horizontalStrut_3);
				
				Component horizontalStrut_2 = Box.createHorizontalStrut(20);
				horizontalBox_1.add(horizontalStrut_2);
				
				Component horizontalStrut_1 = Box.createHorizontalStrut(20);
				horizontalBox_1.add(horizontalStrut_1);
				
				Component horizontalStrut = Box.createHorizontalStrut(20);
				horizontalBox_1.add(horizontalStrut);
				
				JButton btnCreateNewMap = new JButton("Create new map");

				horizontalBox_1.add(btnCreateNewMap);
				
				Box horizontalBox = Box.createHorizontalBox();
				getContentPane().add(horizontalBox, BorderLayout.NORTH);
				
				Box verticalBox = Box.createVerticalBox();
				horizontalBox.add(verticalBox);
				
				JLabel lblWidth = new JLabel("Width");
				verticalBox.add(lblWidth);
				
				widthInput = new JTextField();
				widthInput.setText("50");
				verticalBox.add(widthInput);
				widthInput.setColumns(10);
				
				Component verticalGlue = Box.createVerticalGlue();
				verticalBox.add(verticalGlue);
				
				JLabel lblHeight = new JLabel("Height");
				verticalBox.add(lblHeight);
				
				heightInput = new JTextField();
				heightInput.setText("50");
				verticalBox.add(heightInput);
				heightInput.setColumns(10);
				
				JComboBox imageSelectionList = new JComboBox();
				verticalBox.add(imageSelectionList);
				for(String s : imageNames) {
					imageSelectionList.addItem(s);
				} //in list
				
				JButton sizeValues = new JButton("Autosize from image selection");
				sizeValues.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(imageSelectionList.getSelectedIndex() >= 0) {
							//valid selection made
							widthInput.setText(Integer.toString((int)Math.ceil(images.get(imageSelectionList.getSelectedIndex()).getWidth()/BootstrapperConstants.TILE_WIDTH)));
							heightInput.setText(Integer.toString((int)Math.ceil(images.get(imageSelectionList.getSelectedIndex()).getHeight()/BootstrapperConstants.TILE_WIDTH)));
							
						}
					}
				});
				verticalBox.add(sizeValues);
				Component verticalStrut = Box.createVerticalStrut(75);
				verticalBox.add(verticalStrut);
				btnCreateNewMap.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Cell cellToCreate;
						//make a new cell and add it to the cell list.
						try {
							
						int width = Integer.parseInt(widthInput.getText());
						int height = Integer.parseInt(heightInput.getText());
						String mapID = nameInput.getText();
						if(mapID.contains(".map")) {
							throw new FileSystemException("Do not include extensions");
						}
						String[] duplicateCheck = getNames(allCells);
						for(int i=0; i<duplicateCheck.length; i++) {
							if(mapID.equals(duplicateCheck[i])) {
								throw new FileSystemException("Duplicate file not overwritten.");
							}
						}
						cellToCreate = new Cell(width, height, 1.0, TILE_TYPE.WALL, mapID + ".map", displayName.getText());
						allCells.add(cellToCreate);
						mapChooser.removeAllItems();
						String[] names = getNames(allCells);
						for(int i=0; i<names.length; i++) {
							mapChooser.addItem(names[i]);
						}
						
						
						} catch(NumberFormatException | FileSystemException er) {
							//if errors are encountered in parsing, do nothing.
							System.out.println("Error!");
						}
					}
				});

			setVisible(true);
			
	}
	private String[] getNames(ArrayList<Cell> allCells) {
		String[] result = new String[allCells.size()];
		for(int c = 0; c < allCells.size(); c++)
			result[c] = allCells.get(c).getID();
		return result;
	}
	

}
