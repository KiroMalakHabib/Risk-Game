package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper {

	public int calculate_bonus(City[] all_cities, Player p) {
		int numOfCities = 0;
		for (int i = 0; i < all_cities.length; i++) {
			if (all_cities[i].get_color().equals(p.get_color())) {
				numOfCities += all_cities[i].get_armies();
			}
		}
		return Math.max(3, numOfCities / 3);
	}

	public boolean test_goal(City[] cities, String color) {
		for (int i = 0; i < cities.length; i++) {
			if (!(cities[i].get_color().equals(color))) {
				return false;
			}
		}
		return true;
	}

	public int calculate_heuristsic(City[] all_cities, Player p) {
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		int heuristic = 0;
		for (int i = 0; i < all_cities.length; i++) {
			City city = all_cities[i];
			if (!(citiesOfPlayer.contains(city.get_id()))) {
				heuristic += city.get_armies();
			}
		}
		return heuristic;
	}

	public int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

	public HashMap<City[], City[]> get_children(City[] state, Player p, int bonus_armies) {
		// parent : cities after placing armies to specific city
		// child : cities after attack by this specific city
		HashMap<City[], City[]> childern_states = new HashMap<City[], City[]>();
		ArrayList<City[]> states_after_placing = new ArrayList<City[]>();
		for (int i = 0; i < p.get_cities().size(); i++) {
			City c = state[p.get_cities().get(i)];
			City[] copyState = clone_array(state);
			int armies = c.get_armies();
			copyState[i].set_armies(armies + bonus_armies);
			states_after_placing.add(copyState);
		}
		for (int i = 0; i < states_after_placing.size(); i++) {
			City[] state_before_attack = states_after_placing.get(i);
			for (int j = 0; j < state_before_attack.length; j++) {
				City c = new City();
				c = state_before_attack[j];
				boolean attacked = false;
				if (c.get_color().equals(p.get_color())) { // We can attack by this city
					for (int k = 0; k < c.get_neighbours().size(); k++) {
						int neighbour_id = c.get_neighbours().get(k);
						City neighbour = state_before_attack[neighbour_id];
						if (!(neighbour.get_color().equals(p.get_color()))
								&& neighbour.get_armies() < c.get_armies() - 1) { // we can attack this neighbour
							attacked = true;
							City[] copyState = clone_array(state_before_attack);
							int random = getRandomInteger(c.get_armies() - 1, neighbour.get_armies() + 1);
							copyState[neighbour_id].set_color(p.get_color());
							copyState[neighbour_id].set_armies(random);
							copyState[c.get_id()].set_armies(c.get_armies() - random);
							childern_states.put(state_before_attack, copyState);
						}
					}
					if (!attacked) {
						childern_states.put(state_before_attack, state_before_attack);
					}
				}
			}
		}
		return childern_states;
	}

	public void update_players(City[] all_cities, Player p1, Player p2) {
		int armiesOfPlayer1 = 0;
		int armiesOfPlayer2 = 0;
		for (int i = 0; i < all_cities.length; i++) {
			City city = all_cities[i];
			if (city.get_color().equals(p1.get_color())) {
				armiesOfPlayer1 += city.get_armies();
			} else {
				armiesOfPlayer2 += city.get_armies();
			}
			if (city.get_color().equals(p1.get_color()) && !(p1.get_cities().contains(city.get_id()))) {
				p1.get_cities().add(city.get_id());
				p2.get_cities().remove((Integer) city.get_id());
			}
		}
		p1.set_armies(armiesOfPlayer1);
		p2.set_armies(armiesOfPlayer2);
	}

	public City[] clone_array(City[] city) {
		City[] copy = new City[city.length];
		for (int i = 0; i < city.length; i++) {
			City c = new City();
			c.set_armies(city[i].get_armies());
			c.set_color(city[i].get_color());
			c.set_id(city[i].get_id());
			c.set_neighbours(city[i].get_neighbours());
			copy[i] = c;
		}
		return copy;
	}
	
	public void update_allCities(City[] old, City[] new_city) {
		for (int i = 0; i < new_city.length; i++) {
			City city = new City();
			city.set_armies(new_city[i].get_armies());
			city.set_color(new_city[i].get_color());
			city.set_id(new_city[i].get_id());
			city.set_neighbours(new_city[i].get_neighbours());
			if (old[i] == null) {
				old[i] = city;
			} else {
				old[i].set_armies(new_city[i].get_armies());
				old[i].set_color(new_city[i].get_color());
				old[i].set_id(new_city[i].get_id());
				old[i].set_neighbours(new_city[i].get_neighbours());
			}
		}
	}
}
