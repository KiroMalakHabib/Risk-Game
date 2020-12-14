package model.agents;

import java.util.ArrayList;

import model.City;
import model.Player;

public class PassiveAgent {
	
	public void placing_armies(ArrayList<City> all_cities, Player p, int bonus_armies) {
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		City min_city = new City();
		int min_armies = 1000000;
		for (int i = 0; i < all_cities.size(); i++) {
			City city = all_cities.get(i);
			if (citiesOfPlayer.contains(city.get_id())) {
				if (city.get_armies() < min_armies) {
					min_armies = city.get_armies();
					min_city = city;
				}
			}
		}
		min_city.set_armies(min_armies + bonus_armies);
		p.set_armies(p.get_armies() + bonus_armies);
	}

}
