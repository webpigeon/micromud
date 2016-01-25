package uk.me.webpigeon.phd.mud.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerAdapter;

public class TelnetHandler extends ChannelHandlerAdapter {
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    	ctx.write(msg);
    	ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
	
}
