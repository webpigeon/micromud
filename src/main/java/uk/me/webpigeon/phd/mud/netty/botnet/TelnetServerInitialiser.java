package uk.me.webpigeon.phd.mud.netty.botnet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.me.webpigeon.phd.mud.netty.ChannelService;

public class TelnetServerInitialiser extends ChannelInitializer<SocketChannel> {
	private static final StringDecoder DECODER = new StringDecoder();
	private static final StringEncoder ENCODER = new StringEncoder();

	private final SslContext sslCtx;
	private final TelnetServerHandler telnetProcessor;

	public TelnetServerInitialiser(SslContext sslCtx, CommandProcessor processor, ChannelService channels) {
		this.sslCtx = sslCtx;
		this.telnetProcessor = new TelnetServerHandler(processor, channels);
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		if (sslCtx != null) {
			pipeline.addLast(sslCtx.newHandler(ch.alloc()));
		}

		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);

		pipeline.addLast(telnetProcessor);
	}

}
