package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

public class Helper {

	public int calculate_bonus(City[] all_cities, Player p) {
		int numOfCities = 0;
		for (int i = 0; i < all_cities.length; i++) {
			if (all_cities[i].get_color().equals(p.get_color())) {
				numOfCities ++;
			}
		}
		return Math.max(3, numOfCities / 3);
	}
	
	public int calculate_cost(City[] all_cities, Player p) {
		int weakCities = 0;
		for (int i = 0; i < all_cities.length; i++) {
			City city = all_cities[i];
			boolean is_weak = false;
			if (city.get_color().equals(p.get_color())) {
				ArrayList<Integer> my_neighbours = city.get_neighbours();
				for(int j=0; j<my_neighbours.size();j++) {
					City city_neighbour = all_cities[my_neighbours.get(j)];
					if(!(city_neighbour.get_color().equals(city.get_color())) && !(city_neighbour.get_armies() < city.get_armies() - 1)){
						is_weak = true;
					}
				}
			}
			if(is_weak) {
				weakCities++;
			}
		}
		return weakCities;
	}
	public boolean test_loss(City[] cities, String color) {
		for (int i = 0; i < cities.length; i++) {
			if ((cities[i].get_color().equals(color))) {
				return false;
			}
		}
		return true;
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
		int heuristic = 0;
		int armies_p1 = 0;
		int armies_p2 = 0;
		int count_neededArmies = 0;
		for (int i = 0; i < all_cities.length; i++) {
			City city = all_cities[i];
			if (!(city.get_color().equals(p.get_color()))) {
				armies_p2 += city.get_armies();
			} else {
				armies_p1 += city.get_armies();
				ArrayList<Integer> my_neighbours = city.get_neighbours();
				for(int j=0; j<my_neighbours.size();j++) {
					City city_neighbour = all_cities[my_neighbours.get(j)];
					if(!(city_neighbour.get_color().equals(city.get_color())) && !(city_neighbour.get_armies() < city.get_armies() - 1))
					{
						count_neededArmies += (city_neighbour.get_armies() - city.get_armies() + 2);
					}
				}
			}
		}
		//float division = (float)armies_p2 / armies_p1;
		//heuristic = (int) Math.ceil(division);
		heuristic = armies_p2 + count_neededArmies;
		return heuristic;
	}

	public ArrayList<City[]> get_children_placing(City[] state, Player p) {
		ArrayList<City[]> childern_states = new ArrayList<City[]>();
		for (int i = 0; i < state.length; i++) {
			City c = state[i];
			if(c.get_color().equals(p.get_color())) {
				City[] copy_state = clone_array(state);
				copy_state[c.get_id()].set_armies(c.get_armies() + 1);
				childern_states.add(copy_state);
			}
		}
		return childern_states;
	}

	public HashMap<City[],Integer> get_attack_children(City[] states_after_placing, Player p) {
		HashMap<City[],Integer> children = new HashMap<City[],Integer>();
		boolean enter = false;
		for (int j = 0; j < states_after_placing.length; j++) {
			City c = new City();
			c = states_after_placing[j];
			if (c.get_color().equals(p.get_color())) { // We can attack by this city
				for (int k = 0; k < c.get_neighbours().size(); k++) {
					int neighbour_id = c.get_neighbours().get(k);
					City neighbour = states_after_placing[neighbour_id];
					if (!(neighbour.get_color().equals(p.get_color())) && neighbour.get_armies() <= c.get_armies() - 2) { 
						int cost = neighbour.get_armies();
						int max_attack = c.get_armies() - cost - 1;
						for (int m = 1; m <= max_attack; m++) {
							City[] copyState = clone_array(states_after_placing);
							copyState[neighbour_id].set_color(p.get_color());
							copyState[neighbour_id].set_armies(m);
							copyState[c.get_id()].set_armies(c.get_armies() - cost - m);
							children.put(copyState,cost);
						}
						enter = true;
					}
				}
			}
		}
		if (!enter) {
			children.put(states_after_placing,0);
		}
		return children;
	}

	public void update_players(City[] all_cities, Player p1, Player p2) {
		int armiesOfPlayer1 = 0;
		int armiesOfPlayer2 = 0;
		for (int i = 0; i < all_cities.length; i++) {
			City city = all_cities[i];
			if (city.get_color().equals(p1.get_color())) {
				armiesOfPlayer1 = armiesOfPlayer1 + city.get_armies();
			} else {
				armiesOfPlayer2 = armiesOfPlayer2 + city.get_armies();
			}
			if (city.get_color().equals(p1.get_color()) && !(p1.get_cities().contains(city.get_id()))) {
				p1.get_cities().add(city.get_id());
				p2.get_cities().remove((Integer) city.get_id());
			}
		}
		p1.set_armies(armiesOfPlayer1);
		p2.set_armies(armiesOfPlayer2);
	}

	public void update_player(City[] all_cities, Player p1) {
		int armiesOfPlayer1 = 0;
		for (int i = 0; i < all_cities.length; i++) {
			if (all_cities[i].get_color().equals(p1.get_color())) {
				armiesOfPlayer1 += all_cities[i].get_armies();
			}
		}
			p1.set_armies(armiesOfPlayer1);
		}
		/*int armiesOfPlayer1 = 0;
		for (int i = 0; i < p1.get_cities().size(); i++) {
			City city = all_cities[p1.get_cities().get(i)];
			armiesOfPlayer1 += city.get_armies();
		}
		p1.set_armies(armiesOfPlayer1);*/

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
			//City city = new City();
			//city.set_armies(new_city[i].get_armies());
			//city.set_color(new_city[i].get_color());
			//city.set_id(new_city[i].get_id());
			//city.set_neighbours(new_city[i].get_neighbours());
			old[i].set_armies(new_city[i].get_armies());
			old[i].set_color(new_city[i].get_color());
			//old[i].set_id(new_city[i].get_id());
			old[i].set_neighbours(new_city[i].get_neighbours());
		}
	}

	public int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}
	
	public void print_cities(City[] cities)
	{
		for (int i = 0; i < cities.length; i++) {
			City c = cities[i];
			System.out.println("ID: " + c.get_id() + " Color: " + c.get_color() + " Armies: " + c.get_armies()
					+ " Neighbours: " + c.get_neighbours());
		}
	}
}
