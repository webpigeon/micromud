package uk.me.webpigeon.phd.mud.modules.accounts;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;

import uk.co.unitycoders.pircbotx.security.SecurityManager;
import uk.me.webpigeon.phd.mud.netty.ChannelService;

/**
 * This is mostly useful for my debugging.
 */
public class AccountManagement extends AnnotationModule {
	
	private SecurityManager security;
	private AccountModel model;
	private ChannelService channels;

	public AccountManagement(SecurityManager security, ChannelService channels, AccountModel model) {
		super("account");
		this.security = security;
		this.model = model;
		this.channels = channels;
	}

	@Command("login")
	public void login(Message message) {
		String username = message.getArgument(2, null);
		String password = message.getArgument(3, null);
		
		if (username == null || password == null) {
			message.respond("usage: login [username] [password]");
			return;
		}
		
		// check the user's details
		Account account = model.getAccount(username);
		if (account == null) {
			message.respond("incorrect username");
			return;
		}
		
		//check passwords match
		if (!account.isPassword(password)) {
			message.respond("incorrect password");
			return;
		}
		
		//create the user a session
		String sessionKey = message.getSessionKey();
		MudSession session = new MudSession(sessionKey);
		session.setAccount(account);
		security.startSession(session);
		
		channels.register(username, message);
		
		session.setProp(Account.NAME_PROP, username);
		message.respond("You are now logged in");
	}
	
	@Command("register")
	public void register(Message message) {
		String username = message.getArgument(2, null);
		String password = message.getArgument(3, null);
		
		if (username == null || password == null) {
			message.respond("usage: register [username] [password]");
			return;
		}
		
		// check the user's details
		Account account = model.getAccount(username);
		if (account != null) {
			message.respond("that account exists already");
			return;
		}
		
		boolean result = model.createAccount(username, password);
		if (result) {
			message.respond("You account exists and you can now log in");
		} else {
			message.respond("Your account could not be created");
		}
	}
	
}
