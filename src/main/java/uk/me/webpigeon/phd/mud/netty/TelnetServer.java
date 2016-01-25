package uk.me.webpigeon.phd.mud.netty;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;

public class TelnetServer {
	private int port;
	private SslContext ctx;
	private CommandProcessor processor;
	private ChannelService channels;
	
	public TelnetServer(int port, CommandProcessor processor, ChannelService channels, SslContext ctx) {
		this.port = port;
		this.ctx = ctx;
		this.processor = processor;
		this.channels = channels;
		System.out.println("telnet server created");
	}
	
	
	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .handler(new LoggingHandler(LogLevel.INFO))
			 .childHandler(new TelnetServerInitialiser(ctx, processor, channels))
			 .option(ChannelOption.SO_BACKLOG, 128)
			 .childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture f = b.bind(port).sync();
			
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
