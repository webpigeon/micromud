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
import uk.co.unitycoders.pircbotx.security.SecurityMiddleware;
import uk.co.unitycoders.pircbotx.security.SecurityManager;
import uk.me.webpigeon.phd.mud.botlink.DebugInfo;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountManagement;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.world.PlayerMovement;
import uk.me.webpigeon.phd.mud.modules.world.WorldCommands;
import uk.me.webpigeon.phd.mud.modules.world.WorldModel;
import uk.me.webpigeon.phd.mud.netty.TelnetServer;

/**
 * Hello world!
 *
 */
public class App {
	private static final Boolean SSL_ENABLED = false;

	public static void main(String[] args) throws Exception {
		System.out.println("Starting MUD server...");	
		
		SecurityManager security = new SecurityManager();
		CommandProcessor processor = buildProcessor(security);
		
		//account related
		AccountModel accounts = new AccountModel();
		processor.register("account", new AccountManagement(security, accounts));
		
		//world related
		WorldModel world = DebugUtils.buildWorld();
		processor.register("go", new PlayerMovement(world));
		processor.register("world", new WorldCommands(world));
		
		TelnetServer telnet = buildTelnetServer(processor);
		telnet.run();

	}

	private static CommandProcessor buildProcessor(SecurityManager security) {
		
		List<BotMiddleware> middleware = new ArrayList<BotMiddleware>();
		middleware.add(new CommandFixerMiddleware());
		middleware.add(new SecurityMiddleware(security));
		
		CommandProcessor processor = new CommandProcessor(middleware);
		
		
		//todo register commands here
		processor.register("debug", new DebugInfo());
		
		return processor;
	}
	
	private static TelnetServer buildTelnetServer(CommandProcessor processor) throws CertificateException, SSLException {
		// SSL
		final SslContext sslCtx;
		if (SSL_ENABLED) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
		} else {
			sslCtx = null;
		}

		TelnetServer telnet = new TelnetServer(1337, processor, sslCtx);
		return telnet;
	}
}
