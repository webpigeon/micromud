package uk.me.webpigeon.phd.mud.modules.accounts;

import uk.co.unitycoders.pircbotx.security.Session;

public class MudSession extends Session {
	private final String SESSION_FORMAT = "[%s on %s]";

	private Account account;
	
	public MudSession(String sessionKey) {
		super(sessionKey);
	}
	
	public boolean isLoggedIn() {
		return account != null;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
	
	@Override
	public String toString() {
		return String.format(SESSION_FORMAT, account==null?"Not logged in":account, getKey());
	}

}
