package uk.me.webpigeon.phd.mud.modules.world;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.j256.ormlite.field.DatabaseField;

import uk.me.webpigeon.phd.mud.modules.MudObject;

public class Room extends MudObject {

	@DatabaseField(id = true)
	private final String name;

	@DatabaseField
	private String description;

	private Map<String, String> extraDesc;

	Room() {
		this(null);
	}

	public Room(String name) {
		this.name = name;
		this.extraDesc = new HashMap<String, String>();
		this.flags = new HashSet<String>();
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public String getExtraDesc(String id) {
		return extraDesc.get(id);
	}

	public void setExtraDesc(String id, String desc) {
		extraDesc.put(id, desc);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getID() {
		return name;
	}

	@Override
	public String[] getKeywords() {
		return new String[] { name };
	}

	@Override
	public String getType() {
		return "room";
	}

	@Override
	public String getShortName() {
		return name;
	}

}
