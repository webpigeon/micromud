package uk.me.webpigeon.phd.mud.modules.accounts;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.j256.ormlite.field.DatabaseField;

import uk.me.webpigeon.phd.mud.modules.MudObject;
import uk.me.webpigeon.phd.mud.modules.world.Room;

public class Account extends MudObject {
	public static final String NAME_PROP = "mud.accounts.name";
	public static final String ROOM_PROP = "mud.accounts.room";
	
	@DatabaseField(id = true)
	private final String username;
	
	@DatabaseField
	private String password;
	
	@DatabaseField(foreign=true)
	private Room room;
	
	private final Map<String, String> accountProperties;
	private final Set<String> changedProperties;
	
	
	Account() {
		this(null);
	}
	
	public Account(String username) {
		this.username = username;
		this.accountProperties = new TreeMap<String, String>();
		this.changedProperties = new HashSet<String>();
	}
	
	public String getProperty(String key, String defaultValue) {
		String result = accountProperties.get(key);
		if (result == null) {
			return defaultValue;
		}
		return result;
	}
	
	public Room getLocation() {
		return room;
	}
	
	public void setLocation(Room location) {
		this.room = location;
	}
	
	public void setProperty(String key, String newValue) {
		accountProperties.put(key, newValue);
		changedProperties.add(key);
	}
	
	//TODO hash passwords
	public void setPassword(String password) {
		this.password = password.trim();
	}
	
	public boolean isPassword(String challenge) {
		String realPass = password.trim();
		return realPass.equals(challenge);
	}
	
	@Override
	public String toString() {
		return username;
	}

	void setProperties(Map<String, String> properties) {
		changedProperties.clear();
		accountProperties.clear();
		accountProperties.putAll(properties);
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String getID() {
		return username.toLowerCase();
	}

	@Override
	public String[] getKeywords() {
		return new String[0];
	}

	@Override
	public String getType() {
		return "account";
	}

	@Override
	public String getShortName() {
		return username;
	}

	@Override
	public String getDescription() {
		return null;
	}

}
