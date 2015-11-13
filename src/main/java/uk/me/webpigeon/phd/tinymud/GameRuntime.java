package uk.me.webpigeon.phd.tinymud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import uk.me.webpigeon.phd.tinymud.data.UpdatableState;
import uk.me.webpigeon.phd.tinymud.world.GraphWorld;

/**
 * The server side game model.
 *
 */
public class GameRuntime {
	private final static String SPAWN_ROOM = "Main Street";
	
	private final static Logger LOG = Logger.getLogger(GameRuntime.class.getName());
	private Map<String, List<Precept>> precepts;
	private Map<String, AgentController> agents;
	
	private UpdatableState state;
	
	private int nextAgentID;
	
	public GameRuntime(GraphWorld world) {
		this.precepts = new HashMap<String, List<Precept>>();
		this.agents = new HashMap<String, AgentController>();
		this.nextAgentID = 0;
		this.state = new ServerState(this, world);
	}
	
	public String addAgent(AgentController agent) {
		LOG.info("agent added: "+agent);
		String agentID = "AGENT-"+nextAgentID;
		nextAgentID++;
		
		agents.put(agentID, agent);
		precepts.put(agentID, new ArrayList<Precept>());
		
		//let all existing players know there is a new player
		spawnAgent(agentID);
		
		return agentID;
	}
	
	private void spawnAgent(String agentID) {
		System.out.println(state.getRoomIDs());
		
		state.setLocation(agentID, SPAWN_ROOM);
		
		addPrecept(agentID, new LinkInfomation(SPAWN_ROOM, state.getLinks(SPAWN_ROOM)));
		
		for (String agent : agents.keySet()){
			addPrecept(agent, new AgentCreated(agentID));
			addPrecept(agent, new LocationChanged(agentID, SPAWN_ROOM));
		}
		
	}
	
	public List<Precept> getPreceptsFor(String agentID) {
		return new ArrayList<Precept>(precepts.get(agentID));
	}
	
	public void addPrecept(String agentID, Precept p) {
		List<Precept> perceptList = precepts.get(agentID);
		if (perceptList == null) {
			perceptList = new ArrayList<Precept>();
			precepts.put(agentID, perceptList);
		}
		
		perceptList.add(p);
	}

	public void execute(String agentID, Move move) {
		if (move == null) {
			LOG.info("<"+agentID+"> did not provide a move");
			return;
		}
		
		LOG.info("<"+agentID+"> ACTION: "+move);
		move.execute(agentID, state);
	}

	public void clearPrecepts(String agentID) {
		List<Precept> perceptList = precepts.get(agentID);
		if (perceptList != null) {
			perceptList.clear();
		}
	}

}
