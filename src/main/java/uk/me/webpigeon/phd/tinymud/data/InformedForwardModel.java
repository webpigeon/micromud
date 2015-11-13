package uk.me.webpigeon.phd.tinymud.data;

public interface InformedForwardModel extends ForwardModel, GameState {
	
	/**
	 * Create an exact copy of this model
	 * 
	 * @return the copy of this model
	 */
	@Override
	public InformedForwardModel copy();
}
