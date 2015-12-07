package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.team1ofus.apollo.EntryPoint;
import com.team1ofus.apollo.HashCell;
import com.team1ofus.apollo.LocationInfo;

import core.BootstrapperConstants;
import core.DebugManagement;
import core.SEVERITY_LEVEL;
import events.LoaderInteractionEventObject;

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
import javax.swing.BoxLayout;

public class Loader extends JDialog {
	public LoaderInteractionEventObject events;
	private JTextField widthInput;
	private JTextField heightInput;
	private JTextField nameInput;
	private JTextField displayName;
	private JComboBox mapChooser;
	private JButton okButton;
	private JComboBox imageSelectionList;
	private JButton sizeValues;
	
	/**
	 * Create the dialog.
	 */
	public Loader(ArrayList<HashCell> allCells, ArrayList<String> imageNames, ArrayList<BufferedImage> images) {
		events = new LoaderInteractionEventObject();
		setBounds(100, 100, 597, 200);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		getContentPane().add(horizontalBox);
		
		Box verticalBox = Box.createVerticalBox();
		
		Box dimensionHBox = Box.createHorizontalBox();
		Box imageHBox = Box.createHorizontalBox();
		Box mapHBox = Box.createHorizontalBox();
		Box okHBox = Box.createHorizontalBox();
		
		JLabel lblWidth = new JLabel("Width");
		dimensionHBox.add(lblWidth);
		
		widthInput = new JTextField();
		widthInput.setText("50");
		widthInput.setColumns(10);
		dimensionHBox.add(widthInput);
		
		JLabel lblHeight = new JLabel("Height");
		dimensionHBox.add(lblHeight);
		
		heightInput = new JTextField();
		heightInput.setText("50");
		heightInput.setColumns(10);
		dimensionHBox.add(heightInput);
		
		JLabel lblImage = new JLabel("Image selection");
		imageHBox.add(lblImage);
		
		imageSelectionList = new JComboBox();
		imageSelectionList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(imageSelectionList.getItemCount() > 0)
					widthInput.setText(Integer.toString((int)Math.ceil(images.get(imageSelectionList.getSelectedIndex()).getWidth()/BootstrapperConstants.TILE_WIDTH)));
					heightInput.setText(Integer.toString((int)Math.ceil(images.get(imageSelectionList.getSelectedIndex()).getHeight()/BootstrapperConstants.TILE_WIDTH)));
			}
		});
		for(String s : imageNames) {
			imageSelectionList.addItem(s);
		} //in list
		imageHBox.add(imageSelectionList);
		
		JLabel lblCellName = new JLabel("Cell name");
		mapHBox.add(lblCellName);
		
		nameInput = new JTextField();
		nameInput.setColumns(10);
		mapHBox.add(nameInput);
		
		JButton btnCreateNewMap = new JButton("Create new map");
		mapHBox.add(btnCreateNewMap);
		
		JLabel lblDisplayName = new JLabel("Display name");
		okHBox.add(lblDisplayName);
		
		displayName = new JTextField();
		displayName.setColumns(10);
		okHBox.add(displayName);
		
		JComboBox mapChooser = new JComboBox();
		mapChooser.setModel(new DefaultComboBoxModel(getNames(allCells)));
		okHBox.add(mapChooser);
		
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okHBox.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		verticalBox.add(Box.createVerticalStrut(2));
		verticalBox.add(dimensionHBox);
		verticalBox.add(Box.createVerticalStrut(2));
		verticalBox.add(imageHBox);
		verticalBox.add(Box.createVerticalStrut(2));
		verticalBox.add(mapHBox);
		verticalBox.add(Box.createVerticalStrut(2));
		verticalBox.add(okHBox);
		verticalBox.add(Box.createVerticalStrut(2));
		
		horizontalBox.add(Box.createHorizontalStrut(2));
		horizontalBox.add(verticalBox);
		horizontalBox.add(Box.createHorizontalStrut(2));

		btnCreateNewMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashCell cellToCreate;
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
				cellToCreate = new HashCell(width, height, mapID + ".cell", displayName.getText(), new ArrayList<LocationInfo>(), new ArrayList<EntryPoint>());
				allCells.add(cellToCreate);
				mapChooser.removeAllItems();
				String[] names = getNames(allCells);
				for(int i=0; i<names.length; i++) {
					mapChooser.addItem(names[i]);
				}
				
				
				} catch(NumberFormatException | FileSystemException er) {
					//if errors are encountered in parsing, do nothing.
					DebugManagement.writeLineToLog(SEVERITY_LEVEL.ERROR, "Problem adding Map in Loader!");
				}
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				events.selectionMade(allCells.get(mapChooser.getSelectedIndex()), allCells);
				
			}
		});
		
		
		setSize((int) horizontalBox.getPreferredSize().getWidth() + 10, 150);
		setLocationRelativeTo(null);
		setVisible(true);
			
	}
	private String[] getNames(ArrayList<HashCell> allCells) {
		String[] result = new String[allCells.size()];
		for(int c = 0; c < allCells.size(); c++)
			result[c] = allCells.get(c).getID();
		return result;
	}
	

}
