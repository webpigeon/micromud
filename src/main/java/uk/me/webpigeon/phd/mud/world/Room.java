package uk.me.webpigeon.phd.mud.world;

public class Room {
	private final String name;
	private String description;
	
	public Room(String name) {
		this.name = name;
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

}
