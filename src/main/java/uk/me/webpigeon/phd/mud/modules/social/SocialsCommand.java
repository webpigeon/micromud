package uk.me.webpigeon.phd.mud.modules.social;

import java.util.Collection;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;
import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.items.Item;
import uk.me.webpigeon.phd.mud.modules.items.ItemUtils;

public class SocialsCommand extends AnnotationModule {

	private AccountModel accounts;
	
	public SocialsCommand(AccountModel accounts) {
		super("socials");
		this.accounts = accounts;
	}
	
	private Account getAccount(Session session) {
		String accountName = session.getProp(Account.NAME_PROP, null);
		if (accountName == null) {
			throw new RuntimeException("You are not playing");
		}
		
		Account account = accounts.getAccount(accountName);
		if (account == null) {
			throw new RuntimeException("You are not playing");
		}
		
		return account;
	}
	
	@Command({"say"})
	@Secured
	public void onSay(Message message) {
		Account account = getAccount(message.getSession());
		
		String words = message.getArgument(2, null);
		if (words == null) {
			System.out.println("usage: say \"[message]\"");
		}
		
		message.respond("You say: "+words);
		
		message.broadcast(String.format("%s: %s", account.getShortName(), words));
	}
	
	@Command({"shout"})
	@Secured
	public void onShout(Message message) {
		Account account = getAccount(message.getSession());
		
		String words = message.getArgument(2, null);
		if (words == null) {
			System.out.println("usage: shout \"[message]\"");
		}
		
		message.respond("You shout: "+words);
		message.broadcast(String.format("%s: %s", account.getShortName(), words));
	}
	
	@Command({"tell", "whisper"})
	@Secured
	public void onTell(Message message) {
		Account account = getAccount(message.getSession());
		
		String words = message.getArgument(2, null);
		String whom = message.getArgument(3, null);
		if (words == null) {
			System.out.println("usage: shout \"[message]\"");
		}
		
		//todo make this hidden to everyone but the recipient
		message.respond(String.format("You whisper %s to %s",words, whom));
		message.broadcast(String.format("[debug] %s whispers %s to %s", account.getShortName(), words, whom));
	}
	
	@Command({"do", "action"})
	@Secured
	public void onSocial(Message message) {
		Account account = getAccount(message.getSession());
		
		String what = message.getArgument(2, null);
		String whom = message.getArgument(3, null);
		if (what == null || whom == null) {
			System.out.println("What do you want to say?");
		}
		
		message.broadcast(String.format("* %s %s %s", account.getShortName(), what, whom));
	}

}
