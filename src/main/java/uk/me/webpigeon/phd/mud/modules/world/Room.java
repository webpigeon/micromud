package uk.me.webpigeon.phd.mud.modules.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;

import uk.me.webpigeon.phd.mud.modules.test.Avatar;
import uk.me.webpigeon.phd.mud.persist.GameData;

@PersistenceCapable
public class Room extends GameData {
	
	private String name;
	private String description;
	private Set<Avatar> avatars = new HashSet<Avatar>();
	
	public Room(String id) {
		super(id);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return name+" "+description;
	}

	public String getID() {
		return this.getPK();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void addAvatar(Avatar dummyAvatar) {
		avatars.add(dummyAvatar);
		dummyAvatar.setCurrentRoom(getPK());
	}
	
	public void removeAvatar(Avatar avatar){
		avatars.remove(avatar);
		avatar.setCurrentRoom(null);
	}
	
	public boolean containsAvatar(Avatar avatar){
		return avatars.contains(avatar);
	}
}
