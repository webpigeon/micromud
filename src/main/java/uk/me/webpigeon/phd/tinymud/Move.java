package uk.me.webpigeon.phd.tinymud;

import uk.me.webpigeon.phd.tinymud.data.UpdatableState;

public interface Move {
	
	public void execute(String who, UpdatableState state);
	public String getReadableDescription();

}
