package uk.me.webpigeon.phd.mud;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLException;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandFixerMiddleware;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.middleware.BotMiddleware;
import uk.me.webpigeon.phd.mud.netty.TelnetServer;

/**
 * Hello world!
 *
 */
public class App {
	private static final Boolean SSL_ENABLED = false;

	public static void main(String[] args) throws Exception {
		System.out.println("Starting MUD server...");
		
		CommandProcessor processor = buildProcessor();
		
		TelnetServer telnet = buildTelnetServer(processor);
		telnet.run();

	}

	private static CommandProcessor buildProcessor() {
		List<BotMiddleware> middleware = new ArrayList<BotMiddleware>();
		middleware.add(new CommandFixerMiddleware());
		
		CommandProcessor processor = new CommandProcessor(middleware);
		
		//todo register commands here
		
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
