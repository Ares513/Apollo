package com.team1ofus.apollo;

import java.util.ArrayList;

public class LoaderInteractionEventObject {
	
	public ArrayList<ILoaderInteractionListener> listeners = new ArrayList<ILoaderInteractionListener>();
	public synchronized void addChooseListener(ILoaderInteractionListener input) {
		listeners.add(input);
	}
	public synchronized void removeChooseListener(ILoaderInteractionListener input) {
		listeners.remove(input);
	}
	public void selectionMade(int selection, ArrayList<Cell> allCells) {
		for(ILoaderInteractionListener listener : listeners) {
			listener.selectionMade(selection, allCells);
		}
	}
	
	public LoaderInteractionEventObject() {
	}
}
