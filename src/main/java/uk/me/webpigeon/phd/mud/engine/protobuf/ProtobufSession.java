package uk.me.webpigeon.phd.mud.engine.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import uk.me.webpigeon.phd.mud.engine.Percept;
import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.engine.impl.CommandParser;
import uk.me.webpigeon.phd.mud.engine.impl.UnknownCommandException;
import uk.me.webpigeon.phd.mud.protocol.MudCore.ServerResponse;

public class ProtobufSession extends Session implements Runnable {
	
	private Socket socket;
	private CommandParser parser;
	private OutputStream os;
	private boolean dead;
	
	public ProtobufSession(Socket socket, CommandParser parser) {
		this.socket = socket;
		this.parser = parser;
		
		try {
			this.os = socket.getOutputStream();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		try (Scanner in = new Scanner(socket.getInputStream())) {
			while (in.hasNextLine()) {
				try {
					String message = in.nextLine();
					parser.process(this, message);
				} catch (UnknownCommandException ex) {
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
		throw new RuntimeException("that's dumb, don't do that.");
	}
	
	@Override
	public void addPercept(ServerResponse message) {
		try {
			System.out.println(message);
			message.writeTo(os);
			os.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			this.dead = true;
		}
	}

	@Override
	public boolean isDead() {
		return dead;
	}

}
