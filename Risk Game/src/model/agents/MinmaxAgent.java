package model.agents;

import model.City;
import model.Helper;
import model.Node_Minimax;
import model.Player;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MinmaxAgent implements Agents {

	Helper helper = new Helper();
	Pair<City[], City[]> attacked_state;
	City[] new_state;

	public MinmaxAgent() {
		// TODO Auto-generated constructor stub
	}

	ArrayList<String> check_exist = new ArrayList<String>();

	@Override
	public void placing_armies(City[] all_cities, Player p1, Player p2, int bonus_armies) {
		// TODO Auto-generated method stub

		Pair<City[], City[]> initial_state = new Pair<City[], City[]>(all_cities, null);
		Node_Minimax chosen_state = Maximize(initial_state, p1, p2, bonus_armies, -1 * Integer.MAX_VALUE,
				Integer.MAX_VALUE, 2);
		attacked_state = chosen_state.get_child();
		helper.print_cities(attacked_state.getValue());
		helper.update_allCities(all_cities, attacked_state.getValue());
		helper.update_player(attacked_state.getValue(), p1);
		new_state = attacked_state.getKey();
	}

	@Override
	public boolean attack(City[] all_cities, Player p1, Player p2) {
		// TODO Auto-generated method stub
		System.out.println("attack...");
		helper.print_cities(new_state);
		if (!Arrays.deepEquals(new_state, all_cities)) {
			helper.update_allCities(all_cities, new_state);
			helper.update_players(new_state, p1, p2);
			return true;
		}
		return false;
	}

	ArrayList<String> string_armies_array = new ArrayList<String>();

	private Node_Minimax Minimize(Pair<City[], City[]> all_cities, Player p1, Player p2, int bonus_armies, int alpha,
			int beta, int depth) {
		Node_Minimax chosen_node = new Node_Minimax();
		if (depth == 1) {
			int heuristic = helper.calculate_heuristsic(all_cities.getKey(), p1);
			chosen_node.set_utility(-1 * heuristic);
			chosen_node.set_child(all_cities);
			return chosen_node;
		}
		if (helper.test_goal(all_cities.getKey(), p2.get_color())) {
			chosen_node.set_utility(Integer.MAX_VALUE);
			chosen_node.set_child(all_cities);

			return chosen_node;

		}
		if (helper.test_loss(all_cities.getKey(), p2.get_color())) {
			chosen_node.set_utility(-1 * Integer.MAX_VALUE);
			chosen_node.set_child(all_cities);
			return chosen_node;
		}
		int min_utility = Integer.MAX_VALUE;

		ArrayList<City[]> possible_placing = get_possibilities(all_cities.getKey(), p2, bonus_armies,
				new ArrayList<City[]>());
		ArrayList<Pair<ArrayList<City[]>, City[]>> children = get_attacked_children(possible_placing, p2);

		for (int i = 0; i < children.size(); i++) {
			ArrayList<City[]> after_attack = children.get(i).getKey();
			for (int j = 0; j < after_attack.size(); j++) {

				int bonus = helper.calculate_bonus(after_attack.get(j), p1);
				Pair<City[], City[]> newState = new Pair<City[], City[]>(after_attack.get(j),
						children.get(i).getValue());
				Node_Minimax test_node = Maximize(newState, p1, p2, bonus, alpha, beta, depth - 1);

				if (test_node.get_utility() < min_utility) {
					chosen_node.set_child(test_node.get_child());
					chosen_node.set_utility(test_node.get_utility());
					min_utility = test_node.get_utility();
				}

				if (min_utility <= alpha) {
					break;
				}

				if (min_utility < beta) {
					beta = min_utility;
				}

			}
		}

		return chosen_node;

	}

	private Node_Minimax Maximize(Pair<City[], City[]> all_cities, Player p1, Player p2, int bonus_armies, int alpha,
			int beta, int depth) {
		Node_Minimax chosen_node = new Node_Minimax();
		if (depth == 1) {
			int heuristic = helper.calculate_heuristsic(all_cities.getKey(), p1);
			chosen_node.set_utility(-1 * heuristic);
			chosen_node.set_child(all_cities);
			return chosen_node;
		}
		if (helper.test_goal(all_cities.getKey(), p1.get_color())) {
			chosen_node.set_utility(Integer.MAX_VALUE);
			chosen_node.set_child(all_cities);
			return chosen_node;
		}
		if (helper.test_loss(all_cities.getKey(), p1.get_color())) {
			chosen_node.set_utility(-1 * Integer.MAX_VALUE);
			chosen_node.set_child(all_cities);
			return chosen_node;
		}
		int max_utility = -1 * Integer.MAX_VALUE;
		ArrayList<City[]> possible_placing = get_possibilities(all_cities.getKey(), p1, bonus_armies,
				new ArrayList<City[]>());
		ArrayList<Pair<ArrayList<City[]>, City[]>> children = get_attacked_children(possible_placing, p1);
		for (int i = 0; i < children.size(); i++) {
			ArrayList<City[]> after_attack = children.get(i).getKey();
			for (int j = 0; j < after_attack.size(); j++) {

				int bonus = helper.calculate_bonus(after_attack.get(j), p2);
				Pair<City[], City[]> newState = new Pair<City[], City[]>(after_attack.get(j),
						children.get(i).getValue());
				Node_Minimax test_node = Minimize(newState, p1, p2, bonus, alpha, beta, depth - 1);
				if (test_node.get_utility() > max_utility) {
					chosen_node.set_child(test_node.get_child());
					chosen_node.set_utility(test_node.get_utility());
					max_utility = test_node.get_utility();
				}

				if (max_utility >= beta) {
					break;
				}

				if (max_utility > alpha) {
					alpha = max_utility;
				}
			}
		}
		return chosen_node;

	}

	private ArrayList<City[]> get_possibilities(City[] all_cities, Player p, int bonus_armies,
			ArrayList<City[]> possible_placing) {
		if (bonus_armies == 0) {
			ArrayList<City[]> toReturn = new ArrayList<>();
			boolean same_possible = false;
			for (City[] possible : possible_placing) {
				boolean same = true;
				for (int i = 0; i < possible.length; i++) {
					if (possible[i].get_armies() != all_cities[i].get_armies()) {
						same = false;
						break;
					}
				}
				if (same) {
					same_possible = true;
					break;
				}
			}
			if (!same_possible) {
				toReturn.add(all_cities);
			}
			return toReturn;
		}
		ArrayList<City[]> temp_placing = new ArrayList<>();
		ArrayList<City[]> after_placing = helper.get_children_placing(all_cities, p);
		for (int i = 0; i < after_placing.size(); i++) {
			temp_placing = get_possibilities(after_placing.get(i), p, bonus_armies - 1, possible_placing);
			String s = change_to_string(after_placing.get(i));
			if (temp_placing.size() == 1 && bonus_armies == 1) {
				possible_placing.add(temp_placing.get(0));
				//check_exist.add(s);
			}
		}
		return possible_placing;
	}

	private ArrayList<Pair<ArrayList<City[]>, City[]>> get_attacked_children(ArrayList<City[]> possible_placing,
			Player p) {

		ArrayList<Pair<ArrayList<City[]>, City[]>> children = new ArrayList<>();
		for (int i = 0; i < possible_placing.size(); i++) {
			ArrayList<City[]> childrenOfPlacing = new ArrayList<>();
			HashMap<City[], Integer> children_temp = helper.get_attack_children(possible_placing.get(i), p);
			for (Map.Entry<City[], Integer> entry : children_temp.entrySet()) {
				childrenOfPlacing.add(entry.getKey());
			}
			if (childrenOfPlacing.size() > 0 && possible_placing.get(i) != null) {
				Pair<ArrayList<City[]>, City[]> temp = new Pair<ArrayList<City[]>, City[]>(childrenOfPlacing,
						possible_placing.get(i));
				children.add(temp);
			}
		}
		return children;
	}

	String change_to_string(City[] all_cites) {
		String s = "";
		for (City i : all_cites) {
			s += i.get_color().charAt(0);
			s += Character.forDigit(i.get_armies(), 10);
		}
		return s;

	}

}
