package events;

import com.team1ofus.apollo.HashCell;

/*
 * Handles all the ApolloUI events. UIManagement implements this class.
 */
/**
 * @author Evan King
 *
 */
public interface IHumanInteractionListener {
	void onSaveTriggered(HashCell cellToSave);
	
}
