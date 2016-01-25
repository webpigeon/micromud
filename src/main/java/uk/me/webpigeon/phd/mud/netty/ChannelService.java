package uk.me.webpigeon.phd.mud.netty;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

public class ChannelService {
	private final ChannelGroup everyone;
	private final Map<String, Channel> channels;
	private final Map<String, ChannelGroup> groups;
	
	public ChannelService() {
		this.everyone = new DefaultChannelGroup("everyone", GlobalEventExecutor.INSTANCE);
		this.channels = new HashMap<String, Channel>();
		this.groups = new HashMap<String, ChannelGroup>();
	}
	
	public void register(String username, Message message) {
		assert channels.containsKey(username);
		
		Channel channel = message.getChannel();
		
		channels.put(username, channel);
		everyone.add(channel);
	}
	
	public void deregister(String username) {
		Channel channel = channels.get(username);
		if (channel == null) {
			return;
		}
		
		channels.remove(username);
		for (ChannelGroup group : groups.values()) {
			group.remove(channel);
		}
	}
	
	public void registerGroup(String username, String groupName) {
		Channel userChannel = channels.get(username);
		if (userChannel == null) {
			System.err.println("error: tried to register "+groupName+" for unknown user "+username);
			return;
		}
		
		ChannelGroup group = groups.get(groupName);
		if (group == null) {
			group = new DefaultChannelGroup(groupName, GlobalEventExecutor.INSTANCE);
			groups.put(groupName, group);
		}
		group.add(userChannel);
	}
	
	public void unregsiterGroup(String username, String groupName) {
		Channel channel = channels.get(username);
		if (channel == null) {
			System.err.println("error: tried to unregister "+groupName+" for unknown user "+username);
			return;
		}
		
		ChannelGroup cg = groups.get(groupName);
		if (cg != null) {
			cg.remove(channel);
		}
		
	}

	public void sendToAll(String message) {
		for (Channel channel : everyone) {
			channel.writeAndFlush(message+"\r\n");
		}
	}
	
	public void sendToGroup(String groupName, String message) {
		ChannelGroup group = groups.get(groupName);
		
		//nope, no one in this group
		if (group == null) {
			return;
		}
		
		//send the message to all users
		for (Channel channel : group) {
			channel.writeAndFlush(message+"\r\n");
		}
	}
	
	public void sendToUser(String groupName, String message) {
		Channel channel =  channels.get(groupName);
		if (channel != null) {
			channel.writeAndFlush(message);
		} else {
			System.err.println("User "+groupName+" does not exist");
		}
	}
	
}
