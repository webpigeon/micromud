package uk.me.webpigeon.phd.mud.modules.test;

import uk.me.webpigeon.phd.mud.persist.PersistantStorage;

public interface AvatarService extends PersistantStorage<Avatar> {
	
	public Avatar createNew(String name);
	public Avatar get(String name);

}
