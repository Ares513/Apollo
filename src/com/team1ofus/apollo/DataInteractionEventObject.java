package com.team1ofus.apollo;

import java.util.ArrayList;

public class DataInteractionEventObject {
	public ArrayList<IDataInteractionListener> listeners = new ArrayList<IDataInteractionListener>();
	public void addListener(IDataInteractionListener input) {
		listeners.add(input);
	}
	public void removeListener(IDataInteractionListener input) {
		listeners.remove(input);
	}
	public void triggerSave() {
		for(IDataInteractionListener listener : listeners) {
			listener.onDataInteraction();
		}
	}
}


