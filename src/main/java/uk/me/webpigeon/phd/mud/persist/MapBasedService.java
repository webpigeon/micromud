package uk.me.webpigeon.phd.mud.persist;

import java.util.Map;
import java.util.TreeMap;

public class MapBasedService<T extends GameData> {
	private Map<String, T> data;
	
	public MapBasedService() {
		this.data = new TreeMap<String,T>();
	}
	
	public void save(T object){
		data.put(object.getPK(), object);
	}
	
	public T get(String pk) {
		return data.get(pk);
	}

}
