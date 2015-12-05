package events;

import java.util.ArrayList;

import com.team1ofus.apollo.HashCell;

public class LoaderInteractionEventObject {
	
	public ArrayList<ILoaderInteractionListener> listeners = new ArrayList<ILoaderInteractionListener>();
	public synchronized void addChooseListener(ILoaderInteractionListener input) {
		listeners.add(input);
	}
	public synchronized void removeChooseListener(ILoaderInteractionListener input) {
		listeners.remove(input);
	}
	public void selectionMade(HashCell selection, ArrayList<HashCell> allCells) {
		for(ILoaderInteractionListener listener : listeners) {
			listener.selectionMade(selection, allCells);
		}
	}
	
	public LoaderInteractionEventObject() {
	}
}
