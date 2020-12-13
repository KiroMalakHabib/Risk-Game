package model.agents;

import java.util.ArrayList;

import model.City;
import model.Player;

public class AggressiveAgent {

	public void placing_armies(ArrayList<City> all_cities, Player p, int bonus_armies) {
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		City max_city = new City();
		int max_armies = 0;
		for (int i = 0; i < all_cities.size(); i++) {
			City city = all_cities.get(i);
			if (citiesOfPlayer.contains(city.get_id())) {
				if (max_armies < city.get_armies()) {
					max_armies = city.get_armies();
					max_city = city;
				}
			}
		}
		max_city.set_armies(max_armies + bonus_armies);
	}

	public void attack(ArrayList<City> all_cities, Player p) {
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		City max_city = new City();
		int max_armies = 0;
		for (int i = 0; i < all_cities.size(); i++) {
			City city = new City();
			city = all_cities.get(i);
			if (citiesOfPlayer.contains(city.get_id())) {
				ArrayList<Integer> neighbours = city.get_neighbours();
				for (int j = 0; j < neighbours.size(); j++) {
					City cityOfNeighbours = new City();
					cityOfNeighbours = get_requiredCity(all_cities, neighbours.get(j));
					if (!(cityOfNeighbours.get_color().equals(city.get_color()))
							&& cityOfNeighbours.get_armies() < city.get_armies() - 1) {
						if (cityOfNeighbours.get_armies() > max_armies) {
							max_armies = cityOfNeighbours.get_armies();
							max_city = cityOfNeighbours;
						}
					}
				}
				if (max_armies != 0) {
					max_city.set_color(city.get_color());
					max_city.set_armies(max_armies + 1);
					city.set_armies(city.get_armies() - (max_armies + 1));
				}
			}
		}
	}

	private City get_requiredCity(ArrayList<City> all_cities, int idOfCity) {
		for (int k = 0; k < all_cities.size(); k++) {
			if (all_cities.get(k).get_id() == idOfCity) {
				return all_cities.get(k);
			}
		}
		return new City();
	}
}
