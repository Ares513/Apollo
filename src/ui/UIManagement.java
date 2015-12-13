package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.team1ofus.apollo.HashCell;

import core.DebugManagement;
import data.DataManagement;
import events.IDataUpdateListener;
import events.IHumanInteractionListener;
import events.ILoaderInteractionListener;
import events.UIManagementInteractionEventObject;

public class UIManagement implements IDataUpdateListener, IHumanInteractionListener, ILoaderInteractionListener {
	ApolloUI window;
	Loader loader;

	HashCell editCell;
	ArrayList<String> imageNames;
	ArrayList<BufferedImage> images;
	public UIManagementInteractionEventObject events; //later, add getters and setters to prevent direct access.
	DataManagement data;
	public UIManagement(DataManagement data, ArrayList<String> imageNames, ArrayList<BufferedImage> images) {
		events = new UIManagementInteractionEventObject();
		this.imageNames = imageNames;
		this.images = images;
		//Changes are made to Cells until a Save operation is made.
        this.data = data;
	}
	/*
	 * Launch the application once event handling and stitching is complete.
	 */
	public void begin() {
		loader = new Loader(data, imageNames, images);
		loader.events.addChooseListener(this);
		//await loader callback.
		
	}
	
	public void onCellUpdate() {
		
	}
	
	public	void onDataUpdate() {
		
	}
	@Override
	public void onSaveTriggered(HashCell cellToSave) {
		
		events.triggerSave(cellToSave);
		
	}
	@Override
	public void selectionMade(HashCell selection) {
		editCell = selection;
		DebugManagement.writeNotificationToLog("Loader selection made!");
		window = new ApolloUI(imageNames, images);
		DebugManagement.writeNotificationToLog("Finished creating ApolloUI instance.");
		window.events.addSaveListener(this);
		DebugManagement.writeNotificationToLog("saveListener added to ApolloUI.");
		events.triggerSave(editCell);
		DebugManagement.writeNotificationToLog("Writeback save complete");
		loader.dispose();
		DebugManagement.writeNotificationToLog("Loader disposed.");
		window.initialize(selection);
	}

}
