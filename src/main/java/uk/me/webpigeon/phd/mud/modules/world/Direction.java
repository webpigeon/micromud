package uk.me.webpigeon.phd.mud.modules.world;

public enum Direction {
	N("North"),
	NE("North East"),
	E("East"),
	SE("South East"),
	S("South"),
	SW("South West"),
	W("WEST"),
	NW("North West"),
	U("Up"),
	D("Down");
	
	public final String name;
	
	Direction(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
