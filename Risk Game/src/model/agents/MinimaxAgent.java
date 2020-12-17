package model.agents;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.City;
import model.Helper;
import model.Node_Minimax;
import model.Player;

public class MinimaxAgent implements Agents{

	Helper help = new Helper();
	static City[] child_city;
	
	@Override
	public void placing_armies(City[] all_cities, Player p, int bonus_armies) {
		// TODO Auto-generated method stub
		child_city = new City[all_cities.length];
		Node_Minimax node = new Node_Minimax();
		Map<City[], City[]> my_children = help.get_children(all_cities, p, bonus_armies);
		for (Map.Entry<City[], City[]> entry : my_children.entrySet()) {
			node = minmax(entry.getValue(), p, bonus_armies, -999999, 999999, true,1,  entry);
			}
		child_city = node.get_child().getKey();
		System.out.println("mmmmmm");
	}

	@Override
	public boolean attack(City[] all_cities, Player p1, Player p2) {
		// TODO Auto-generated method stub
		if(Arrays.equals(child_city, all_cities)) {
			help.update_players(all_cities, p1, p2);
			return false;
		}
		all_cities = child_city;
		help.update_players(all_cities, p1, p2);
		return true;
	}

	
	
	private Node_Minimax minmax(City[] all_cities, Player p, int bonus_armies,int alpha,int beta, boolean maxTurn, int depth,Map.Entry<City[], City[]> toReturn) {
		Node_Minimax node = new Node_Minimax();
		if (depth == 0 || help.test_goal(all_cities, p.get_color())){
			int heuristic = help.calculate_heuristsic(all_cities, p);
			node.set_child(toReturn);
			node.set_heueistic(heuristic);
			return node;
		}
		
		if(maxTurn) {
			node.set_heueistic(-999999);
			Map<City[], City[]> next_states = help.get_children(all_cities, p, bonus_armies);
			
			for (Map.Entry<City[], City[]> next_state : next_states.entrySet()) {
				int new_armies = help.calculate_bonus_minimax(p, next_state.getValue());
				Node_Minimax node_temp = minmax(next_state.getValue(), p, new_armies, alpha, beta, false,depth-1, next_state);
				
				if(node_temp.get_heuristic() > node.get_heuristic()) {
					node.set_heueistic(node_temp.get_heuristic());
				}
				
				if(node_temp.get_heuristic() > alpha) {
					alpha = node_temp.get_heuristic();
				}
				
				if(alpha >= beta) {
					break;
				}
				
			}

		}
		
		else {
			node.set_heueistic(999999);
			Map<City[], City[]> next_states = help.get_children(all_cities, p, bonus_armies);
			
			for (Map.Entry<City[], City[]> next_state : next_states.entrySet()) {
				
				int new_armies = help.calculate_bonus_minimax(p, next_state.getValue());
				Node_Minimax node_temp = minmax(next_state.getValue(), p, new_armies, alpha, beta, true,depth-1, next_state);
				
				if(node_temp.get_heuristic() < node.get_heuristic()) {
					node.set_heueistic(node_temp.get_heuristic());
				}
				
				if(node_temp.get_heuristic() < beta) {
					beta = node_temp.get_heuristic();
				}
				
				if(alpha >= beta) {
					break;
				}
				
			}
		}
		node.set_child(toReturn);
		return node;
		
	}

}
