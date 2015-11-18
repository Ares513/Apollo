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

public class UIManagement implements IDataUpdateListener, IHumanInteractionListener, ILoaderInteractionListener {
	ApolloUI window;
	Loader loader;

	ArrayList<Cell> cells;
	public UIManagementInteractionEventObject events; //later, add getters and setters to prevent direct access.
	
	public UIManagement(ArrayList<Cell> allCells) {
		events = new UIManagementInteractionEventObject();
		
		cells = allCells; //loaded from DataManagement
		//Changes are made to Cells until a Save operation is made.
	
	}
	/*
	 * Launch the application once event handling and stitching is complete.
	 */
	public void begin() {
		loader = new Loader(cells);
		loader.events.addChooseListener(this);
		//await loader callback.
		
	}
	
	public void onCellUpdate() {
		
	}
	
	public	void onDataUpdate() {
		
	}
	@Override
	public void onSaveTriggered(Cell cellToSave) {
		cells.set(0, cellToSave);
		events.triggerSave(cells);
		
	}
	@Override
	public void selectionMade(int selection, ArrayList<Cell> allCells) {
		// TODO Auto-generated method stub
		window = new ApolloUI();
		window.events.addSaveListener(this);
		cells = allCells;
		events.triggerSave(cells);
		loader.dispose();
		window.initialize(cells.get(selection));
	}

}
