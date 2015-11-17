package com.team1ofus.apollo;

import java.util.ArrayList;
import java.util.EventObject;

public class HumanInteractionEventObject extends EventObject {
	
	public ArrayList<HumanInteractionEventObject> listeners = new ArrayList<HumanInteractionEventObject>();
	public synchronized void addMouseClickListener(HumanInteractionEventObject input) {
		listeners.add(input);
	}
	public synchronized void removeMouseClickListener(HumanInteractionEventObject input) {
		listeners.remove(input);
	}
	public void triggerSave(ArrayList<Cell> cellsToSave) {
		for(HumanInteractionEventObject listener : listeners) {
			//listener.onSaveTriggered();
		}
	}
	
	public HumanInteractionEventObject(Object arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}

