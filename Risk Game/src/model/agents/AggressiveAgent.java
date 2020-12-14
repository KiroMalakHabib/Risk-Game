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
		p.set_armies(p.get_armies() + bonus_armies);
	}

	public boolean attack(ArrayList<City> all_cities, Player p, Player p2) {
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		boolean attacked = false;
		ArrayList<Integer> attacked_cities = new ArrayList<Integer>();
		for (int i = 0; i < all_cities.size(); i++) {
			City city = new City();
			city = all_cities.get(i);
			if (citiesOfPlayer.contains(city.get_id())) {
				ArrayList<Integer> neighbours = city.get_neighbours();
				City max_city = new City();
				int max_armies = 0;
				for (int j = 0; j < neighbours.size(); j++) {
					City cityOfNeighbours = new City();
					cityOfNeighbours = get_requiredCity(all_cities, neighbours.get(j));
					if (cityOfNeighbours.get_armies() == 0) {
						max_city = cityOfNeighbours;
					} else if (!(cityOfNeighbours.get_color().equals(p.get_color()))
							&& cityOfNeighbours.get_armies() < city.get_armies() - 1) {
						if (cityOfNeighbours.get_armies() > max_armies) {
							max_armies = cityOfNeighbours.get_armies();
							max_city = cityOfNeighbours;
						}
					}
				}
				if (max_city.get_id() != -1 && !(attacked_cities.contains(max_city.get_id()))) {
					p2.set_armies(p2.get_armies() - max_armies);
					max_city.set_color(p.get_color());
					max_city.set_armies(max_armies + 1);
					city.set_armies(city.get_armies() - (max_armies + 1));
					p.get_cities().add(max_city.get_id());
					p2.get_cities().remove(new Integer(max_city.get_id()));
					attacked_cities.add(max_city.get_id());
					attacked = true;
				}
			}
		}
		return attacked;
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
