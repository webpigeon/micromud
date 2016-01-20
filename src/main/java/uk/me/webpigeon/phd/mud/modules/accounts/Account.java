package uk.me.webpigeon.phd.mud.modules.accounts;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Account {
	public static final String NAME_PROP = "mud.accounts.name";
	public static final String ROOM_PROP = "mud.accounts.room";
	
	private final String username;
	private String password;
	
	private final Map<String, String> accountProperties;
	private final Set<String> changedProperties;
	
	
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
	
	public void setProperty(String key, String newValue) {
		accountProperties.put(key, newValue);
		changedProperties.add(key);
	}
	
	//TODO hash passwords
	public void setPassword(String password) {
		this.password = password.trim();
	}
	
	public boolean isPassword(String challenge) {
		return password.equals(challenge);
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
		// TODO Auto-generated method stub
		return null;
	}

}
