package com.team1ofus.apollo;

import java.util.ArrayList;

/*
 * Different from HumanInteractionEventObject, this class sends information from UIManagement to Bootstrapper.
 * The events that need to be registered and the terms in which they will be used is different from HumanInteractionEventObject.
 * 
 */
public class UIManagementInteractionEventObject {
	public ArrayList<IUIManagementInteractionListener> listeners = new ArrayList<IUIManagementInteractionListener>();
	public void addListener(IUIManagementInteractionListener input) {
		listeners.add(input);
	}
	public void removeListener(IUIManagementInteractionListener input) {
		listeners.remove(input);
	}
	public void triggerSave(ArrayList<Cell> cellsToSave) {
		for(IUIManagementInteractionListener listener : listeners) {
			listener.onSaveTriggered(cellsToSave);
		}
	}
}