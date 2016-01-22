package uk.me.webpigeon.phd.mud.botlink;

import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;

public class MudSession extends Session {
	private Account account;
	
	public MudSession(String sessionKey) {
		super(sessionKey);
	}

	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount(){
		return account;
	}
}
