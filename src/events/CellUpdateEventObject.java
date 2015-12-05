package events;

import java.util.ArrayList;

import com.team1ofus.apollo.HashCell;

public class CellUpdateEventObject {
	public ArrayList<ICellUpdateListener> listeners = new ArrayList<ICellUpdateListener>();
	public void addListener(ICellUpdateListener input) {
		listeners.add(input);
	}
	public void removeListener(ICellUpdateListener input) {
		listeners.remove(input);
	}
	public void triggerSave(ArrayList<HashCell> cellsToSave) {
		for(ICellUpdateListener listener : listeners) {
			//listener.onSaveTriggered(cellsToSave);
		}
	}
}
