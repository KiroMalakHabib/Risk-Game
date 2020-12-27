package model.agents;

import java.util.ArrayList;

import model.City;
import model.Player;

public class PassiveAgent implements Agents {

	public void placing_armies(City[] all_cities, Player p, Player P1,int bonus_armies) {
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		City min_city = new City();
		int min_armies = 1000000;
		for (int i = 0; i < citiesOfPlayer.size(); i++) {
			City city = all_cities[citiesOfPlayer.get(i)];
			if (city.get_armies() < min_armies) {
				min_armies = city.get_armies();
				min_city = city;
			}
		}
		min_city.set_armies(min_armies + bonus_armies);
		p.set_armies(p.get_armies() + bonus_armies);
	}

	@Override
	public boolean attack(City[] all_cities, Player p1, Player p2) {
		// TODO Auto-generated method stub
		return false;
	}

}