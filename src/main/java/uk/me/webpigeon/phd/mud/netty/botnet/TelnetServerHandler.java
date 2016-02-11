package uk.me.webpigeon.phd.mud.netty.botnet;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandNotFoundException;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.me.webpigeon.phd.mud.botlink.HumanMudMessage;
import uk.me.webpigeon.phd.mud.netty.ChannelService;

import java.util.List;

@Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {
	private final String PROMPT = "\r\n%sPROMPT: \r\n%s";

	static final ChannelGroup allUsers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	private final CommandProcessor processor;
	private final ChannelService channels;

	public TelnetServerHandler(CommandProcessor processor, ChannelService channels) {
		this.processor = processor;
		this.channels = channels;
	}

	public void broadcast(String message) {
		channels.sendToAll(message);
	}

	public void sendChannel(String message, String channelName) {
		channels.sendToUser(channelName, message);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write("IGGIMUD: Botnet port\r\n");
		ctx.flush();

		allUsers.add(ctx.channel());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, String request) {

		String response;
		boolean close = false;
		if (request.isEmpty()) {
			response = "Please type something.";
		} else if ("bye".equals(request.toLowerCase())) {
			response = "Have a good day!";
			close = true;
		} else {
			String sessionKey = ctx.channel().remoteAddress().toString();
			try {
				List<String> args = processor.processMessage(request);
				Message message = new HumanMudMessage(null, ctx, args, sessionKey);

				processor.invoke(message);
				response = ANSI.ANSI_WHITE + "\r\nCOMMAND: " + ANSI.ANSI_RESET;
			} catch (CommandNotFoundException ex) {
				response = "huh?!";
			} catch (Exception ex) {
				ex.printStackTrace();
				response = ANSI.ANSI_RED + "Something blew up, let webpigeon know." + ANSI.ANSI_RESET;
			}
		}

		ChannelFuture future = ctx.write(response + "\r\n");
		ctx.flush();
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
