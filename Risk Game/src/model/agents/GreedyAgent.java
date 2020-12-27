package model.agents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import model.City;
import model.Helper;
import model.Player;
import javafx.util.Pair;

public class GreedyAgent implements Agents {

	Helper help = new Helper();
	static City[] child_city;
	static PriorityQueue<Pair<Integer, City[]>> frontier = new PriorityQueue<Pair<Integer, City[]>>(
			Comparator.comparing(Pair::getKey));
	static Set<Pair<Integer, City[]>> explored = new HashSet<Pair<Integer, City[]>>();
	static Pair<Integer, City[]> check_pair;
	static City[] last_statePlacing;
	static HashMap<City[], Integer> hashmap = new HashMap<City[], Integer>();
	HashMap<City[], Integer> attacked = new HashMap<City[], Integer>();
	PriorityQueue<Pair<Integer, City[]>> frontier_attacked = new PriorityQueue<Pair<Integer, City[]>>(
			Comparator.comparing(Pair::getKey));

	@Override
	public void placing_armies(City[] all_cities, Player p,Player p2, int bonus_armies) {
		// TODO Auto-generated method stub
		hashmap.clear();
		frontier.clear();
		explored.clear();
		last_statePlacing = new City[all_cities.length];
		Pair<Integer, City[]> initial_state = new Pair<Integer, City[]>(0, all_cities);
		frontier.add(initial_state);
		hashmap.put(all_cities, 0);
		while (!frontier.isEmpty()) {
			Pair<Integer, City[]> current_state = frontier.poll();
			City[] state = current_state.getValue();
			int number = hashmap.get(state);
			
			if(is_explored(current_state)) {
				continue;
			}
			explored.add(current_state);
			
			if (number == bonus_armies) {
				last_statePlacing = state;
				break;
			}
			ArrayList<City[]> my_children = help.get_children_placing(state, p);
			for (int i = 0; i < my_children.size(); i++) {
				City[] child = my_children.get(i);
				int heuristic_child = help.calculate_heuristsic(child, p);
				Pair<Integer, City[]> pair_child = new Pair<Integer, City[]>(heuristic_child, child);
				if (check_stateInFrontier(child)) {
					if (check_pair.getKey() > heuristic_child) {
						Pair<Integer, City[]> old_pair = new Pair<Integer, City[]>(check_pair.getKey(), child);
						frontier.remove(old_pair);
						frontier.add(pair_child);
						hashmap.put(child, number+1);
					}
				}
				else {
					frontier.add(pair_child);
					hashmap.put(child, number+1);
				}
			}
			hashmap.remove(state);
		}
		help.update_allCities(all_cities, last_statePlacing);
		help.update_player(all_cities, p);
	}

	@Override
	public boolean attack(City[] all_cities, Player p1, Player p2) {
		// TODO Auto-generated method stub
		attacked.clear();
		frontier_attacked.clear();
		City[] first = help.clone_array(all_cities);
		attacked = help.get_attack_children(first, p1);
		for (Map.Entry<City[], Integer> entry : attacked.entrySet()) {
			City[] city = entry.getKey();
			int heuristic = help.calculate_heuristsic(city, p1);
			Pair<Integer, City[]> my_pair = new Pair<Integer, City[]>(heuristic, city);
			boolean found = false;
			for (Pair<Integer, City[]> c : frontier_attacked) {
				City[] cities = c.getValue();
				if (areSame(cities, city)) {
					if(heuristic < c.getKey())
					{
						Pair<Integer, City[]> old_pair = new Pair<Integer, City[]>(c.getKey(), city);
						frontier_attacked.remove(old_pair);
						frontier_attacked.add(my_pair);
					}
					found = true;
					break;
				}
			}
			if(!found) {
				frontier_attacked.add(my_pair);
			}
		}
		Pair<Integer, City[]> attacked_pair = frontier_attacked.poll();
		if (Arrays.deepEquals(all_cities, attacked_pair.getValue())) {
			help.update_players(all_cities, p1, p2);
			return false;
		}
		help.update_allCities(all_cities, attacked_pair.getValue());
		help.update_players(all_cities, p1, p2);
		return true;
	}

	private boolean is_explored(Pair<Integer, City[]> state) {
		for (Pair<Integer, City[]> c : explored) {
			City[] cities = c.getValue();
			int f = c.getKey();
			if (Arrays.deepEquals(cities, state.getValue()) && f== state.getKey()) {
				return true;
			}
		}
		return false;
	}

	private boolean check_stateInFrontier(City[] state) {
		for (Pair<Integer, City[]> c : frontier) {
			City[] cities = c.getValue();
			if (areSame(cities, state)) {
				check_pair = c;
				return true;
			}
		}
		return false;
	}

	private boolean areSame(City[] A, City[] B) {
		if(Arrays.deepEquals(A, B)) {
			return true;
		}
		return false;
	}

}
