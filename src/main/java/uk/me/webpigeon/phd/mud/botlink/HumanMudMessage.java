package uk.me.webpigeon.phd.mud.botlink;

import java.util.List;

import uk.co.unitycoders.pircbotx.commandprocessor.AbstractMessage;

public class HumanMudMessage extends AbstractMessage {
	
	public HumanMudMessage(List<String> args, String sessionKey) {
		super(args, sessionKey);
	}

	@Override
	public String getRawMessage() {
		return null;
	}

	@Override
	public String getTargetName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void respond(String message) {
		throw new RuntimeException("use netty properly");
	}

	@Override
	public void respondSuccess() {
		throw new RuntimeException("use netty properly");	
	}

	@Override
	public void sendAction(String action) {
		throw new RuntimeException("use netty properly");	
	}

}
