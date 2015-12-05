package events;

import java.util.ArrayList;

import com.team1ofus.apollo.HashCell;

public class DataUpdateEventObject {
	public ArrayList<IDataUpdateListener> listeners = new ArrayList<IDataUpdateListener>();
	public void addListener(IDataUpdateListener input) {
		listeners.add(input);
	}
	public void removeListener(IDataUpdateListener input) {
		listeners.remove(input);
	}
	public void triggerSave(ArrayList<HashCell> cellsToSave) {
		for(IDataUpdateListener listener : listeners) {
			//listener.onSaveTriggered(cellsToSave);
		}
	}
}
