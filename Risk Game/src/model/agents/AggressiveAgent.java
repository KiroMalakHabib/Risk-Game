package model.agents;

import java.util.ArrayList;
import model.City;
import model.Helper;
import model.Player;

public class AggressiveAgent implements Agents {
	
Helper help = new Helper();
	
	public void placing_armies(City[] all_cities, Player p, int bonus_armies) {
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		City max_city = new City();
		int max_armies = 0;
		for (int i = 0; i < citiesOfPlayer.size(); i++) {
			City city = all_cities[citiesOfPlayer.get(i)];
			if (max_armies < city.get_armies()) {
				max_armies = city.get_armies();
			}
		}
		max_city = check_bestCity(max_armies, all_cities, p);
		all_cities[max_city.get_id()].set_armies(max_armies + bonus_armies);
		p.set_armies(p.get_armies() + bonus_armies);
	}

	public boolean attack(City[] all_cities, Player p1, Player p2) {
		ArrayList<Integer> citiesOfPlayer = p1.get_cities();
		boolean attacked = false;
		ArrayList<Integer> attacked_cities = new ArrayList<Integer>();
		for (int i = 0; i < citiesOfPlayer.size(); i++) {
			City city = new City();
			city = all_cities[citiesOfPlayer.get(i)];
			if (!(attacked_cities.contains(city.get_id()))) {
				ArrayList<Integer> neighbours = city.get_neighbours();
				City max_city = null;
				int max_armies = 0;
				for (int j = 0; j < neighbours.size(); j++) {
					City cityOfNeighbours = new City();
					cityOfNeighbours = all_cities[neighbours.get(j)];
					if (!(cityOfNeighbours.get_color().equals(p1.get_color()))
							&& cityOfNeighbours.get_armies() < city.get_armies() - 1) {
						if (cityOfNeighbours.get_armies() > max_armies) {
							max_armies = cityOfNeighbours.get_armies();
							max_city = cityOfNeighbours;
						}
					}
				}
				if (max_city != null && !(attacked_cities.contains(max_city.get_id()))) {
					int random = help.getRandomInteger(city.get_armies() - 1, max_armies + 1);
					p2.set_armies(p2.get_armies() - max_armies);
					p2.get_cities().remove((Integer) max_city.get_id());
					max_city.set_color(p1.get_color());
					max_city.set_armies(random);
					all_cities[max_city.get_id()].set_color(p1.get_color());
					all_cities[max_city.get_id()].set_armies(random);
					city.set_armies(city.get_armies() - random);
					p1.get_cities().add(max_city.get_id());
					attacked_cities.add(max_city.get_id());
					attacked = true;
				}
			}
		}
		return attacked;
	}

	private City check_bestCity(int max_armies, City[] all_cities, Player p) {
		int count = 0;
		int maximum = 0;
		City max_city = new City();
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		for (int i = 0; i < citiesOfPlayer.size(); i++) {
			City city = all_cities[citiesOfPlayer.get(i)];
			if (city.get_armies() == max_armies) {
				count = 0;
				ArrayList<Integer> neighbours = city.get_neighbours();
				for (int j = 0; j < neighbours.size(); j++) {
					City cityOfNeighbour = all_cities[neighbours.get(j)];
					if (!(cityOfNeighbour.get_color().equals(city.get_color()))) {
						count++;
					}
				}
				if (count > maximum) {
					maximum = count;
					max_city = city;
				} else if (maximum == 0 && count == 0) {
					max_city = city;
				}
			}
		}
		return max_city;
	}
}
