package model;

import model.agents.AStarAgent;
import model.agents.Agents;
import model.agents.AggressiveAgent;
import model.agents.GreedyAgent;
import model.agents.HumanAgent;
import model.agents.MinmaxAgent;
import model.agents.PacifistAgent;
import model.agents.PassiveAgent;
import model.agents.RTAStarAgent;

public class AgentFactory {

	public Agents get_agent(String typeOfAgent) {
		Agents agent = null;
		switch(typeOfAgent) {
		case "Human agent":
			agent = new HumanAgent();
			break;
		case "Passive agent":
			agent = new PassiveAgent();
			break;
		case "Aggressive agent":
			agent = new AggressiveAgent();
			break;
		case "Pacifist agent":
			agent = new PacifistAgent();
			break;
		case "Greedy agent":
			agent = new GreedyAgent();
			break;
		case "A* with heuristic":
			agent = new AStarAgent();
			break;
		case "Real time A*":
			agent = new RTAStarAgent();
			break;
		case "Minimax agent":
			agent = new MinmaxAgent();
			break;
		default:
			agent = new PassiveAgent();
			break;	
		}
		return agent;
	}
}
