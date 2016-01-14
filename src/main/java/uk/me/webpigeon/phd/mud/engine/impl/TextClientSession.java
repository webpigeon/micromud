package uk.me.webpigeon.phd.mud.engine.impl;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.google.protobuf.Message;

import uk.me.webpigeon.phd.mud.engine.ClientState;
import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.MessageType;
import uk.me.webpigeon.phd.mud.engine.Percept;
import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.protocol.MudCore.ServerResponse;
import uk.me.webpigeon.phd.mud.protocol.MudWorld;
import uk.me.webpigeon.phd.mud.protocol.MudWorld.MovementPercept;
import uk.me.webpigeon.phd.mud.protocol.MudWorld.RoomPercept;

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
	@Deprecated
	public void addPercept(Percept percept) {
		print(percept.getType(), percept.toString());
	}
	
	@Override
	public void addPercept(ServerResponse message) {
		processResponse(message);
	}
	
	private void processResponse(ServerResponse message) {
		int type = message.getType();
		if (type == MudWorld.EXITS_EXT_FIELD_NUMBER) {
			MudWorld.ExitsPercept ep = message.getExtension(MudWorld.exitsExt);
			
			//process the list of exits
			String exitStr = "exits: ";
			Iterator<MudWorld.RoomExit> exits = ep.getExitList().iterator();
			while(exits.hasNext()) {
				MudWorld.RoomExit exit = exits.next();
				exitStr += exit.getDirection();
				if (exits.hasNext()) {
					exitStr += " ";
				}
			}
			
			print(MessageType.NARRATIVE, exitStr);
		} else if (type == MudWorld.MOVE_EXT_FIELD_NUMBER) {
			
			MovementPercept ep = message.getExtension(MudWorld.moveExt);
			print(MessageType.NARRATIVE, ep.getWho()+" moves into "+ep.getNewRoom()+" from "+ep.getOldRoom());
			
		} else if (type == MudWorld.ROOM_EXT_FIELD_NUMBER) {
			RoomPercept ep = message.getExtension(MudWorld.roomExt);
			print(MessageType.NARRATIVE, ":: "+ep.getName()+" ::");
			print(MessageType.NARRATIVE, ep.getDescription());
		} else {
			print(MessageType.DEBUG, "unknown percept: "+message.getType()+" "+message);
		}
	}

	@Override
	public boolean isDead() {
		return dead;
	}

}
