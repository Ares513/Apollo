package com.team1ofus.apollo;

import java.util.ArrayList;

public class DataUpdateEventObject {
	public ArrayList<IDataUpdateListener> listeners = new ArrayList<IDataUpdateListener>();
	public void addListener(IDataUpdateListener input) {
		listeners.add(input);
	}
	public void removeListener(IDataUpdateListener input) {
		listeners.remove(input);
	}
	public void triggerSave(ArrayList<Cell> cellsToSave) {
		for(IDataUpdateListener listener : listeners) {
			//listener.onSaveTriggered(cellsToSave);
		}
	}
}
