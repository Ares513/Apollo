package events;

import java.util.ArrayList;

import com.team1ofus.apollo.HashCell;

public interface ILoaderInteractionListener {
	void selectionMade(HashCell selection, ArrayList<HashCell> allCells);
}
