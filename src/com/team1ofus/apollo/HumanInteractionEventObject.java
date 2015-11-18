package com.team1ofus.apollo;

import java.util.ArrayList;
import java.util.EventObject;

public class HumanInteractionEventObject {
	
	public ArrayList<IHumanInteractionListener> listeners = new ArrayList<IHumanInteractionListener>();
	public synchronized void addSaveListener(IHumanInteractionListener input) {
		listeners.add(input);
	}
	public synchronized void removeSaveListener(IHumanInteractionListener input) {
		listeners.remove(input);
	}
	public void triggerSave(Cell cellToSave) {
		for(IHumanInteractionListener listener : listeners) {
			listener.onSaveTriggered(cellToSave);
		}
	}
	
	public HumanInteractionEventObject() {
	}
}

