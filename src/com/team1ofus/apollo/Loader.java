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
	/**
	 * Create the dialog.
	 */
	public Loader(ArrayList<Cell> allCells) {
		events = new LoaderInteractionEventObject();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
			JPanel pane = new JPanel();
			pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pane, BorderLayout.SOUTH);
				JComboBox mapChooser = new JComboBox();
				mapChooser.setModel(new DefaultComboBoxModel(getNames(allCells)));
				pane.add(mapChooser);
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						events.selectionMade(mapChooser.getSelectedIndex(), allCells);
						
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
				nameInput.setText("default");
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
				
				Component verticalStrut = Box.createVerticalStrut(110);
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
							throw new Exception("Do not include extensions");
						}
						cellToCreate = new Cell(width, height, 1.0, TILE_TYPE.WALL, mapID + ".map");
						allCells.add(cellToCreate);
						mapChooser.setModel(new DefaultComboBoxModel(getNames(allCells)));
						} catch(Exception ex) {
							//if errors are encountered in parsing, do nothing.
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
