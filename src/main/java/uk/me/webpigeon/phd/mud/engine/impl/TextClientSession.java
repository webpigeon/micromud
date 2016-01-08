package uk.me.webpigeon.phd.mud.engine.impl;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import uk.me.webpigeon.phd.mud.engine.ClientState;
import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.MessageType;
import uk.me.webpigeon.phd.mud.engine.Percept;
import uk.me.webpigeon.phd.mud.engine.Session;

public class TextClientSession extends Session implements Runnable {
	private Socket socket;
	private PrintStream out;
	private CommandParser parser;
	private boolean dead;
	
	private ClientState state;
	
	public TextClientSession(Socket socket, CommandParser parser) {
		this.socket = socket;
		this.parser = parser;
		this.dead = false;
		this.state = ClientState.LOBBY;
		
		try {
			this.out = new PrintStream(socket.getOutputStream());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void print(MessageType type, String line) {
		if (!dead) {
			switch(type){
				case SYSTEM:
					out.println(ANSI.ANSI_WHITE+"[*] "+line+ANSI.ANSI_RESET);
					break;
				case NARRATIVE:
					out.println(ANSI.ANSI_BLUE+line+ANSI.ANSI_RESET);
					break;
				case ERROR:
				default:
					out.println(ANSI.ANSI_RED+"[DEBUG] "+line+ANSI.ANSI_RESET);
			}
		}
	}

	@Override
	public void run() {
		try (Scanner in = new Scanner(socket.getInputStream())) {
			print(MessageType.DEBUG, "Mud debug mode, data is non-persistant, create account with debug command.");
			while (in.hasNextLine()) {
				try {
					String message = in.nextLine();
					parser.process(this, message);
				} catch (UnknownCommandException ex) {
					print(MessageType.ERROR, "huh??");
					ex.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dead = true;
	}

	@Override
	public void addPercept(Percept percept) {
		print(percept.getType(), percept.toString());
	}

	@Override
	public boolean isDead() {
		return dead;
	}

}
