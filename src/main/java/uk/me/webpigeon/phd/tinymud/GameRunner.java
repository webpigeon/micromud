package uk.me.webpigeon.phd.tinymud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.me.webpigeon.phd.tinymud.agents.local.RandomAgent;
import uk.me.webpigeon.phd.tinymud.world.GraphWorld;
import uk.me.webpigeon.phd.tinymud.world.NodeRoom;
import uk.me.webpigeon.phd.tinymud.world.Room;

public class GameRunner {
	public static final Integer MAX_TICKS = 1000;
	
	public static void main(String[] args) {
		GraphWorld world = buildWorld();
		
		GameRuntime runner = new GameRuntime(world);
		
		Map<AgentController, String> agentIDs = new HashMap<>();
		for (AgentController agent : buildAgents()) {
			System.out.println("Agent added: "+agent);
			String agentID = runner.addAgent(agent);
			agent.setup("LIMBO", agentID);
			agentIDs.put(agent, agentID);
		}
		
		for (int ticks = 0; ticks < MAX_TICKS; ticks++) {
			System.out.println("tick started: "+ticks);
			for (Map.Entry<AgentController, String> agentEntry : agentIDs.entrySet()) {
				AgentController agent = agentEntry.getKey();
				String agentID = agentEntry.getValue();
				
				List<Precept> precepts = runner.getPreceptsFor(agentID);
				runner.clearPrecepts(agentID);
				System.out.println(agent+" : "+precepts);
				
				Move move = agent.getMove(null, precepts);
				runner.execute(agentID, move);
				
				System.out.println("["+agentID+"] "+move);
			}
			System.out.println("tick ended: "+ticks);
		}
	}
	
	public static List<AgentController> buildAgents() {
		List<AgentController> agents = new ArrayList<AgentController>();
		agents.add(new RandomAgent());
		
		return agents;
	}

	public static GraphWorld buildWorld() {
		GraphWorld world = new GraphWorld();
		
		Room street = new NodeRoom("Main Street");
		world.addRoom(street);
		
		Room bakery = new NodeRoom("Bakery");
		world.addRoom(bakery);
		
		Room well = new NodeRoom("Well");
		world.addRoom(well);
		
		world.addDuelLink(street, bakery);
		world.addLink(street, well); //trap state
		
		return world;
	}
	
}
