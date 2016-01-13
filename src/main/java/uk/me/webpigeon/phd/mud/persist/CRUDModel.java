package uk.me.webpigeon.phd.mud.persist;

import java.util.List;

public interface CRUDModel<T extends GameData> {
	
	public List<T> getAll();
	public T get(String id);
	public T create(T item);
	public T update(String id, T item);
	public T delete(String id);
	
}
