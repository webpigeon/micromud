package uk.me.webpigeon.phd.mud.modules.social;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;
import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.netty.ChannelService;

public class SocialsCommand extends AnnotationModule {

	private AccountModel accounts;
	private ChannelService channels;

	public SocialsCommand(AccountModel accounts, ChannelService channels) {
		super("socials");
		this.accounts = accounts;
		this.channels = channels;
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

	@Command({ "say" })
	@Secured
	public void onSay(Message message) {
		Account account = getAccount(message.getSession());

		String words = message.getArgument(2, null);
		Room room = account.getLocation();
		if (words == null) {
			System.out.println("usage: say \"[message]\"");
		}

		message.respond("You say: " + words);
		channels.sendToGroup("room-" + room.getID(), String.format("%s: %s", account.getShortName(), words));
	}

	@Command({ "shout" })
	@Secured
	public void onShout(Message message) {
		Account account = getAccount(message.getSession());

		String words = message.getArgument(2, null);
		if (words == null) {
			System.out.println("usage: shout \"[message]\"");
		}

		message.respond("You shout: " + words);
		channels.sendToAll(String.format("%s shouts %s", account.getShortName(), words));
	}

	@Command({ "tell", "whisper" })
	@Secured
	public void onTell(Message message) {
		Account account = getAccount(message.getSession());

		String words = message.getArgument(3, null);
		String whom = message.getArgument(2, null);
		if (words == null) {
			System.out.println("usage: shout \"[message]\"");
		}

		// todo make this hidden to everyone but the recipient
		message.respond(String.format("You whisper %s to %s", words, whom));
		channels.sendToUser(whom, String.format("%s whispers %s to you", account.getShortName(), words));
	}

	@Command({ "do", "action" })
	@Secured
	public void onSocial(Message message) {
		Account account = getAccount(message.getSession());

		String whom = message.getArgument(2, null);
		String what = message.getArgument(3, null);
		if (what == null || whom == null) {
			System.out.println("What do you want to say?");
		}

		doAction(account, what, whom);
	}

	// socials to include
	// -> Abstain
	// -> accuse
	// -> agree
	// -> ahem
	// -> applaud

	private void doAction(Account account, String action, String toWho) {
		Room room = account.getLocation();

		channels.sendToGroup("room-" + room.getID(),
				String.format("[*] %s %ss %s", account.getShortName(), action, toWho));
	}

}
