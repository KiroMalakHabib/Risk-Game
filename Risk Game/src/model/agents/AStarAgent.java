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

public class AStarAgent implements Agents{
	
	Helper help = new Helper();
	static PriorityQueue<Pair<Integer,  City[]>> frontier = new PriorityQueue<Pair<Integer, City[]>>(
			Comparator.comparing(Pair::getKey));
	static HashMap<City[], Integer> hash_cost = new HashMap<City[], Integer>();
	static HashMap<City[], Integer> hash_cost_p = new HashMap<City[], Integer>();
	static HashMap<City[], Integer> hash_cost_iter = new HashMap<City[], Integer>();
	static HashMap<City[], Integer> hash_cost_iter_a = new HashMap<City[], Integer>();
	static PriorityQueue<Pair<Integer, City[]>> frontier_attacked = new PriorityQueue<Pair<Integer, City[]>>(
			Comparator.comparing(Pair::getKey));
	static HashMap<City[], City[]> parent = new HashMap<City[], City[]>();
	static Set<Pair<Integer, City[]>> explored_placing = new HashSet<Pair<Integer, City[]>>();
	static Set<Pair<Integer, City[]>> explored_attack = new HashSet<Pair<Integer, City[]>>();
	int old_h = 0;
	int counter = 0;
	boolean attack = false;
	static City[] last_statePlacing;
	static HashMap<City[], Integer> hashmap = new HashMap<City[], Integer>();

	@Override
	public void placing_armies(City[] all_cities, Player p, Player p2,int bonus_armies) {
		// TODO Auto-generated method stub
		hashmap.clear();
		frontier.clear();
		explored_placing.clear();
		hash_cost_iter.clear();
		last_statePlacing = new City[all_cities.length];
		Pair<Integer, City[]> initial_state = new Pair<Integer, City[]>(0, all_cities);
		hash_cost_p.put(all_cities,0);
		hash_cost_iter.put(all_cities,0);
		frontier.add(initial_state);
		hashmap.put(all_cities, 0);
		int current_iter = 0;
		int iteration = 0;
		while (!frontier.isEmpty()) {
			Pair<Integer, City[]> current_state = frontier.poll();
			City[] state = current_state.getValue();
			int number = hashmap.get(state);
			
			if(is_explored(current_state,explored_placing)) {
				continue;
			}
			explored_placing.add(current_state);
			
			if (number == bonus_armies) {
				counter+= explored_placing.size();
				last_statePlacing = state;
				break;
			}
			
			if (explored_placing.size() > 25) {
				counter+= explored_placing.size();
				int rest_armies = bonus_armies - number;
				int id = -1; 
				while(true){
					int random = help.getRandomInteger(state.length - 1, 0);
					if(state[random].get_color().equals(p.get_color())) {
							id = state[random].get_id();
							break;
						}
					else {
						continue;
					}	
			}
				state[id].set_armies(state[id].get_armies() + rest_armies);
				last_statePlacing = state;
				break;
			}
			int cost = hash_cost_p.get(state);
			ArrayList<City[]> my_children = help.get_children_placing(state, p);
			for (int i = 0; i < my_children.size(); i++) {
				City[] child = my_children.get(i);
				int heuristic_child = help.calculate_heuristsic(child, p);
				int child_cost = help.calculate_cost(child, p);
				int new_cost = cost + current_iter + child_cost;
				int f = new_cost + heuristic_child;
				Pair<Integer, City[]> pair_child = new Pair<Integer, City[]>(f, child);
				if (IsFrontier(child, frontier)) {
					if (old_h > f) {
						Pair<Integer, City[]> old_pair = new Pair<Integer, City[]>(old_h, child);
						frontier.remove(old_pair);
						frontier.add(pair_child);
						hashmap.put(child, number+1);
						hash_cost_p.put(child, new_cost);
						hash_cost_iter.put(child, current_iter);
					}
				}
				else {
					frontier.add(pair_child);
					hashmap.put(child, number+1);
					hash_cost_p.put(child, new_cost);
					hash_cost_iter.put(child, current_iter);
				}
			}
			hashmap.remove(state);
			iteration++;
		}
		help.update_allCities(all_cities, last_statePlacing);
		if(!attack) {
		help.update_player(all_cities, p);
		}
		hash_cost_p.clear();
	}

	@Override
	public boolean attack(City[] all_cities, Player p1, Player p2) {
		parent.clear();
		frontier_attacked.clear();
		explored_attack.clear();
		hash_cost_iter_a.clear();
		attack = true;
		HashMap<City[], Integer> attacked = help.get_attack_children(all_cities, p1);
		for (Map.Entry<City[], Integer> entry : attacked.entrySet()) {
			City[] city = entry.getKey();
			int child_cost = help.calculate_cost(city, p1);
			hash_cost.put(city, child_cost);
			hash_cost_iter_a.put(city,1);
			int heuristic = help.calculate_heuristsic(city, p1);
			int f = child_cost + heuristic; //+cost
			Pair<Integer,City[]> my_pair = new Pair<Integer, City[]>(f, city);
			frontier_attacked.add(my_pair);
			parent.put(city,city);
		}
		int iteration = 0;
		int current_iter =0;
		City[] first_attack = new City[all_cities.length];
		City[] goal_attack = new City[all_cities.length];
		while (!frontier_attacked.isEmpty()) {
			Pair<Integer, City[]> fr = frontier_attacked.poll();
			City[] child = fr.getValue();
			
			if(iteration == 0) {
				first_attack = help.clone_array(child);
			}
			if(parent.containsKey(child)) {
				goal_attack = help.clone_array(child);
			}
			
			if(is_explored(fr,explored_attack)) {
				continue;
			}
			explored_attack.add(fr);
			
			if(help.test_goal(child, p1.get_color())) {
				counter += explored_attack.size();
				//System.out.println("expanded nodes:" + counter);
				hash_cost.clear();
				attack = false;
				//System.out.println("goaaaaaaaaaaaaaaaaaaaaaaaaaaaal");
				help.update_allCities(all_cities,goal_attack);
				help.update_players(all_cities, p1, p2);
				return true;
			}
			
			//avoid infinite loop
			if(explored_attack.size() > 25) {
				counter += explored_attack.size();
				//System.out.println("expanded nodes:" + counter);
				hash_cost.clear();
				attack = false;
				if (Arrays.deepEquals(all_cities, first_attack)) {
					help.update_players(all_cities, p1, p2);
					//System.out.println("false");
					return false;
				}else {	
				help.update_allCities(all_cities,first_attack);
				help.update_players(all_cities, p1, p2);
				return true;
				}
			}
			int cost = hash_cost.get(child);
			current_iter = hash_cost_iter_a.get(child) +1;
			int new_armies = help.calculate_bonus(child, p1);
			placing_armies(child, p1,p2, new_armies);
			HashMap<City[], Integer> attacked2 = help.get_attack_children(all_cities, p1);
			for (Map.Entry<City[], Integer> e : attacked2.entrySet()) {
				City[] new_child = e.getKey();
				int child_cost = help.calculate_cost(new_child, p1);
				int new_cost = cost + current_iter + child_cost;
				int f = new_cost + help.calculate_heuristsic(new_child, p1);
				Pair<Integer, City[]> new_state = new Pair<Integer, City[]>(f, new_child);
				if(IsFrontier(new_child,frontier_attacked)) {
					if(old_h > f) {
						Pair<Integer, City[]> old_state = new Pair<Integer, City[]>(old_h, new_child);
						frontier_attacked.remove(old_state);
						frontier_attacked.add(new_state);
						hash_cost.put(new_child, new_cost);
						hash_cost_iter_a.put(new_child, current_iter);
					}
				}
				else {
					frontier_attacked.add(new_state);
					hash_cost.put(new_child, new_cost);
					hash_cost_iter_a.put(new_child, current_iter);
				}
				
			}
			iteration++;
		}
		hash_cost.clear();
		return false;
	}
		
	private boolean is_explored(Pair<Integer, City[]> state, Set<Pair<Integer, City[]>> explored) {
		for (Pair<Integer, City[]> c : explored) {
			City[] cities = c.getValue();
			int f = c.getKey();
			if (Arrays.deepEquals(cities, state.getValue()) && f== state.getKey()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean IsFrontier(City[] state, PriorityQueue<Pair<Integer, City[]>> frontier) {
		for (Pair<Integer, City[]> c : frontier) {
			City[] cities = c.getValue();
			if (Arrays.deepEquals(cities, state)) {
				old_h = c.getKey();
				return true;
			}
		}
		return false;
	}
}
