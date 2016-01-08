package uk.me.webpigeon.phd.mud.engine.impl;

import java.util.List;

public class UnknownCommandException extends Exception {
	private String message;
	private List<String> tokens;
	
	public UnknownCommandException(String message, List<String> tokens){
		this.message = message;
		this.tokens = tokens;
	}
	
	public String toString() {
		return String.format("%s: %s", message, tokens);
	}

}
