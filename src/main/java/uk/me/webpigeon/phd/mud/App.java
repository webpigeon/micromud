package uk.me.webpigeon.phd.mud;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandFixerMiddleware;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.middleware.BotMiddleware;
import uk.co.unitycoders.pircbotx.security.SecurityManager;
import uk.co.unitycoders.pircbotx.security.SecurityMiddleware;
import uk.me.webpigeon.phd.mud.botlink.DebugInfo;
import uk.me.webpigeon.phd.mud.dataModel.DataController;
import uk.me.webpigeon.phd.mud.dataModel.debug.BasicGraphWorld;
import uk.me.webpigeon.phd.mud.dataModel.orm.OrmController;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountManagement;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.items.InventoryCommands;
import uk.me.webpigeon.phd.mud.modules.items.InventoryModel;
import uk.me.webpigeon.phd.mud.modules.social.SocialsCommand;
import uk.me.webpigeon.phd.mud.modules.world.PlayerMovement;
import uk.me.webpigeon.phd.mud.modules.world.WorldCommands;
import uk.me.webpigeon.phd.mud.modules.world.WorldModel;
import uk.me.webpigeon.phd.mud.netty.ChannelService;
import uk.me.webpigeon.phd.mud.netty.botnet.BotnetServer;
import uk.me.webpigeon.phd.mud.netty.telnet.TelnetServer;

/**
 * Hello world!
 *
 */
public class App {
	private static final Boolean SSL_ENABLED = false;

	public static void main(String[] args) throws Exception {
		String databaseUrl = "jdbc:postgresql://localhost/mud";
		DataController db = new OrmController(databaseUrl);

		if (args.length > 0) {
			if ("createdb".equals(args[0])) {
				db.init();
				DebugUtils.populateWorld(db.getWorldModel());
				DebugUtils.buildInventories(db.getInventoryModel(), db.getWorldModel());
				return;
			}
			
			if ("populatedb".equals(args[0])) {
				DebugUtils.populateWorld(db.getWorldModel());
				DebugUtils.buildInventories(db.getInventoryModel(), db.getWorldModel());
				return;
			}
		}

		System.out.println("Starting MUD server...");

		ChannelService channels = new ChannelService();

		SecurityManager security = new SecurityManager();
		CommandProcessor processor = buildProcessor(security);

		// account related
		AccountModel accounts = db.getAccountModel();
		processor.register("account", new AccountManagement(security, channels, accounts));

		// world related
		WorldModel world = db.getWorldModel();
		processor.register("go", new PlayerMovement(world, channels, accounts));
		processor.register("room", new WorldCommands(world, accounts));

		// inventory related
		InventoryModel items = db.getInventoryModel();
		processor.register("items", new InventoryCommands(items, channels, accounts));

		// socials related
		processor.register("socials", new SocialsCommand(accounts, channels));

		// start the heart beat system
		Heartbeat hb = new Heartbeat();
		Thread hbt = new Thread(hb);
		hbt.start();

		TelnetServer telnet = buildTelnetServer(processor, channels);
		new Thread(telnet).start();

		BotnetServer botnet = buildBotnetServer(processor, channels);
		new Thread(botnet).start();


	}

	private static CommandProcessor buildProcessor(SecurityManager security) {

		List<BotMiddleware> middleware = new ArrayList<BotMiddleware>();
		middleware.add(new CommandFixerMiddleware());
		middleware.add(new SecurityMiddleware(security));

		CommandProcessor processor = new CommandProcessor(middleware);

		// todo register commands here
		processor.register("debug", new DebugInfo());

		return processor;
	}

	private static TelnetServer buildTelnetServer(CommandProcessor processor, ChannelService channels)
			throws CertificateException, SSLException {
		// SSL
		final SslContext sslCtx;
		if (SSL_ENABLED) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
		} else {
			sslCtx = null;
		}

		TelnetServer telnet = new TelnetServer(1337, processor, channels, sslCtx);
		return telnet;
	}

	private static BotnetServer buildBotnetServer(CommandProcessor processor, ChannelService channels)
			throws CertificateException, SSLException {
		// SSL
		final SslContext sslCtx;
		if (SSL_ENABLED) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
		} else {
			sslCtx = null;
		}

		BotnetServer telnet = new BotnetServer(4242, processor, channels, sslCtx);
		return telnet;
	}

}
