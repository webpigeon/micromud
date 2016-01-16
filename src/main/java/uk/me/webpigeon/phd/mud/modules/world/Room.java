package uk.me.webpigeon.phd.mud.modules.world;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import uk.me.webpigeon.phd.mud.modules.MudObject;

public class Room extends MudObject {
	
	private final String name;
	private String description;
	
	private Map<String, String> extraDesc;
	
	public Room(String name) {
		this.name = name;
		this.extraDesc = new HashMap<String,String>();
		this.flags = new HashSet<String>();
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getExtraDesc(String id) {
		return extraDesc.get(id);
	}
	
	public void setExtraDesc(String id, String desc) {
		extraDesc.put(id, desc);
	}

}
