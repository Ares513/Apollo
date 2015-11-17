package com.team1ofus.apollo;

import java.util.ArrayList;

public class CellUpdateEventObject {
	public ArrayList<ICellUpdateListener> listeners = new ArrayList<ICellUpdateListener>();
	public void addListener(ICellUpdateListener input) {
		listeners.add(input);
	}
	public void removeListener(ICellUpdateListener input) {
		listeners.remove(input);
	}
	public void triggerSave(ArrayList<Cell> cellsToSave) {
		for(ICellUpdateListener listener : listeners) {
			//listener.onSaveTriggered(cellsToSave);
		}
	}
}
