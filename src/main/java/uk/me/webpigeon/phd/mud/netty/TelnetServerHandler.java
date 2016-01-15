package uk.me.webpigeon.phd.mud.netty;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandNotFoundException;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.me.webpigeon.phd.mud.botlink.HumanMudMessage;

public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {
	
	private final CommandProcessor processor;
	
	public TelnetServerHandler(CommandProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
		ctx.write("It is " + new Date() + " now.\r\n");
		ctx.flush();
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, String request) {
		String response;
		boolean close = false;
		if (request.isEmpty()) {
			response = "Please type something.";
		} else if ("bye".equals(request.toLowerCase())){
			response = "Have a good day!";
			close = true;
		} else {
			String sessionKey = ctx.channel().remoteAddress().toString();
			try {
				List<String> args = processor.processMessage(request);
				Message message = new HumanMudMessage(ctx, args, sessionKey);
			
				processor.invoke(message);
				response = "COMMAND: ";
			} catch (CommandNotFoundException ex) {
				response = "huh?!";
			} catch (Exception ex) {
				ex.printStackTrace();
				response = "Something blew up, let webpigeon know.";
			}
		}
		
		ChannelFuture future = ctx.write(response+"\r\n");
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
