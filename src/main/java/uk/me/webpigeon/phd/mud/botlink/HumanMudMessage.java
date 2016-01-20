package uk.me.webpigeon.phd.mud.botlink;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import uk.co.unitycoders.pircbotx.commandprocessor.AbstractMessage;
import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.netty.TelnetServerHandler;

public class HumanMudMessage extends AbstractMessage {
	
	private ChannelHandlerContext ctx;
	private TelnetServerHandler handler;
	
	public HumanMudMessage(TelnetServerHandler handler, ChannelHandlerContext ctx, List<String> args, String sessionKey) {
		super(args, sessionKey);
		this.ctx = ctx;
		this.handler = handler;
	}

	@Override
	public String getRawMessage() {
		return getMessage();
	}

	@Override
	public String getTargetName() {
		Session session = getSession();
		if (session != null) {
			return session.getCurrentUser();
		} else {
			return null;
		}
	}
	
	@Override
	public void broadcast(String message) {
		handler.broadcast(message);
	}

	@Override
	public void respond(String message) {
		ctx.write(message+"\r\n");
	}

	@Override
	public void respondSuccess() {
		ctx.write("that worked.\r\n");
	}

	@Override
	public void sendAction(String action) {
		ctx.write("[*] "+action+"\r\n");
	}

}
