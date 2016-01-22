package uk.me.webpigeon.phd.mud.modules.world;

import java.util.List;

import uk.me.webpigeon.phd.mud.modules.MudObject;

public class RoomLink extends MudObject {

	public Room room;
	public Direction direction;
	public String description;
	public String[] keywords;
	
	public Integer keyRequired;
	
	public RoomLink(Room to, Direction direction) {
		this.room = to;
		this.direction = direction;
	}

	@Override
	public String getID() {
		return room+"-"+direction;
	}

	@Override
	public String[] getKeywords() {
		return keywords;
	}

	@Override
	public String getType() {
		return "roomlink";
	}

	@Override
	public String getShortName() {
		return direction.name()+" "+room.getShortName();
	}

	@Override
	public String getDescription() {
		return direction.name()+" "+room.getShortName();
	}

}
