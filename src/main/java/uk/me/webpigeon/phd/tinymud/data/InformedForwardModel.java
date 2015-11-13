package uk.me.webpigeon.phd.tinymud.data;

import java.util.List;

import uk.me.webpigeon.phd.tinymud.AgentController;
import uk.me.webpigeon.phd.tinymud.world.Item;
import uk.me.webpigeon.phd.tinymud.world.Room;

public interface InformedForwardModel extends ForwardModel, GameState {
	
	/**
	 * Create an exact copy of this model
	 * 
	 * @return the copy of this model
	 */
	@Override
	public InformedForwardModel copy();
}
