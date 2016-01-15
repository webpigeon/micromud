package uk.me.webpigeon.phd.mud.modules.accounts;

public class Account {
	public static final String NAME_PROP = "mud.accounts.name";
	public static final String ROOM_PROP = "mud.accounts.room";
	
	private final String username;
	private String password;
	
	public Account(String username) {
		this.username = username;
	}
	
	//TODO hash passwords
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isPassword(String challenge) {
		return password.equals(challenge);
	}
	
	@Override
	public String toString() {
		return username;
	}

}
