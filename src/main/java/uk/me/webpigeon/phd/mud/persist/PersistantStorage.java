package uk.me.webpigeon.phd.mud.persist;

public interface PersistantStorage <T extends GameData> {

	public void save(T object);
	public T get(String pk);
	
}
