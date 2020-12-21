package model.agents;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.City;
import model.Helper;
import model.Player;

public class GreedyAgent implements Agents {

	Helper help = new Helper();
	static City[] child_city;

	@Override
	public void placing_armies(City[] all_cities, Player p, int bonus_armies) {
		// TODO Auto-generated method stub
		child_city = new City[all_cities.length];
		HashMap<City[], City[]> my_children = help.get_children(all_cities, p, bonus_armies);
		int min_heuristic = Integer.MAX_VALUE;
		City[] parent = new City[all_cities.length];
		for (Map.Entry<City[], City[]> entry : my_children.entrySet()) {
			City[] child = entry.getValue();
			int heuristic = help.calculate_heuristsic(child, p);
			if (heuristic < min_heuristic) {
				min_heuristic = heuristic;
				help.update_allCities(child_city, child);
				help.update_allCities(parent, entry.getKey());
			}
		}
		help.update_allCities(all_cities, parent);
	}

	@Override
	public boolean attack(City[] all_cities, Player p1, Player p2) {
		// TODO Auto-generated method stub
		if (Arrays.equals(child_city, all_cities)) {
			help.update_players(all_cities, p1, p2);
			return false;
		}
		help.update_allCities(all_cities, child_city);
		help.update_players(all_cities, p1, p2);
		return true;
	}

	
}
