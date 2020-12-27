package model.agents;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import model.City;
import model.Helper;
import model.Player;

public class PacifistAgent implements Agents {
Helper helper = new Helper();
	public void placing_armies(City[] all_cities, Player p1,Player p2, int bonus_armies) {
		int size = all_cities.length;
		ArrayList<Integer> citiesOfPlayer = p1.get_cities();
		int min_city = -1;
		int min_armies = Integer.MAX_VALUE;
		for (int i = 0; i < size; i++) {
			if (citiesOfPlayer.contains(all_cities[i].get_id())) {
				if (all_cities[i].get_armies() < min_armies) {
					min_armies = all_cities[i].get_armies();
					min_city = i;
				}
			}
		}
		all_cities[min_city].set_armies(min_armies + bonus_armies);
		p1.set_armies(p1.get_armies() + bonus_armies);
	}

	public boolean attack(City[] all_cities, Player p1, Player p2) {
		ArrayList<Map.Entry<Integer, ArrayList<Integer>>> cities_and_min_neighbors = get_min_neighbor_of_min_neighbor(
				all_cities, p1);
		if (cities_and_min_neighbors.size() > 0) {
			int index = new Random().nextInt(cities_and_min_neighbors.size());
			int city = cities_and_min_neighbors.get(index).getKey();
			int min_neigbor_city = cities_and_min_neighbors.get(index).getValue()
					.get(new Random().nextInt(cities_and_min_neighbors.get(index).getValue().size()));
			int armies_to_distrbute = all_cities[city].get_armies() - all_cities[min_neigbor_city].get_armies() - 2;
//			all_cities[city].set_armies(1);
//			all_cities[min_neigbor_city].set_armies(1);
			p2.set_armies(p2.get_armies() - all_cities[min_neigbor_city].get_armies());
			p1.set_armies(p1.get_armies() - all_cities[min_neigbor_city].get_armies());
			int newArmies_defened = helper.getRandomInteger(armies_to_distrbute, 0);
			// int newArmies_attacker = all_cities[city].get_armies() -
			// all_cities[min_neigbor_city].get_armies() - 1;
			int newArmies_attacker = armies_to_distrbute - newArmies_defened;
			all_cities[city].set_armies(newArmies_attacker + 1);
			all_cities[min_neigbor_city].set_armies(newArmies_defened + 1);
			all_cities[min_neigbor_city].set_color(p1.get_color());
			p1.get_cities().add(min_neigbor_city);
			
			for (int i = 0; i < p2.get_cities().size(); i++) {
				if (min_neigbor_city == p2.get_cities().get(i)) {
					p2.get_cities().remove(i);
					return true;
				}
			}
		}
		return false;

	}

	private Collection<Integer> get_min_neighbor(City[] all_cities, ArrayList<Integer> neighbor, String color) {
		int min = 10000;
		Collection<Integer> cities = new HashSet<Integer>();
		for (int city : neighbor) {
			if (all_cities[city].get_color() != color) {
				if (all_cities[city].get_armies() < min) {
					cities = new ArrayList<Integer>();
					min = all_cities[city].get_armies();
					cities.add(city);
				} else if (all_cities[city].get_armies() == min) {
					cities.add(city);
				}
			}
		}
		return cities;

	}

	private ArrayList<Map.Entry<Integer, ArrayList<Integer>>> get_min_neighbor_of_min_neighbor(City[] all_cities,
			Player p) {
		ArrayList<Map.Entry<Integer, ArrayList<Integer>>> result = new ArrayList<Map.Entry<Integer, ArrayList<Integer>>>();
		Collection<Integer> min_neigbor_city = new HashSet<Integer>();
		int min_neigbor = 10000000;
		int new_min_neigbor = 10000000;
		Collection<Integer> new_min_neigbor_city = new HashSet<Integer>();
		for (int city : p.get_cities()) {
			new_min_neigbor_city = get_min_neighbor(all_cities, all_cities[city].get_neighbours(), p.get_color());
			for (int index : new_min_neigbor_city) {
				new_min_neigbor = all_cities[index].get_armies();
				break;
			}
			
			if (new_min_neigbor_city.size() > 0 && all_cities[city].get_armies() >= new_min_neigbor + 2) {
				if (min_neigbor > new_min_neigbor) {
					min_neigbor_city = new HashSet<Integer>();
					result = new ArrayList<Map.Entry<Integer, ArrayList<Integer>>>();
					min_neigbor = new_min_neigbor;
					min_neigbor_city.addAll(new_min_neigbor_city);
					result.add(new AbstractMap.SimpleEntry<>(city, new ArrayList<>(min_neigbor_city)));
				} else if (min_neigbor == new_min_neigbor) {
					min_neigbor_city = new HashSet<Integer>();
					min_neigbor_city.addAll(new_min_neigbor_city);
					result.add(new AbstractMap.SimpleEntry<>(city, new ArrayList<>(min_neigbor_city)));
				}
			}
		}
		return result;
	}

}
