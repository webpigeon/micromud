package uk.me.webpigeon.phd.mud.modules.test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DebugAvatarService implements AvatarService {
	private Map<String, Avatar> avatars;

	public DebugAvatarService() {
		this.avatars = new HashMap<>();
	}
	
	@Override
	public Avatar createNew(String name) {
		Avatar avatar = new Avatar(name);
		avatars.put(name, avatar);

		return avatar;
	}

	@Override
	public Avatar get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Avatar object) {
		// TODO Auto-generated method stub
	}

}
