package model;

import java.util.ArrayList;
import java.util.Random;

public class Initial_Game {

	Player p1 = new Player();
	Player p2 = new Player();
	Data citiesOfCountry = new Data();
	ArrayList<ArrayList<Integer>> citiesCountry;
	ArrayList<City> all_cities;

	public Initial_Game(String country) {
		if (country.equals("Egypt")) {
			citiesCountry = citiesOfCountry.Egypt();
		} else {
			citiesCountry = citiesOfCountry.USA();
		}
		all_cities = new ArrayList<City>();
		p1.set_color("Blue");
		p1.set_armies(20);
		p2.set_color("Red");
		p2.set_armies(20);
		set_initialCities(p1, p2);
		set_initialCities(p2, p1);
		for(int i = 0; i < citiesCountry.size(); i++) {
			if(!(p1.get_cities().contains(i)) && !(p2.get_cities().contains(i))) {
				City empty_city = new City();
				empty_city.set_id(i);
				empty_city.set_neighbours(citiesCountry.get(i));
				all_cities.add(empty_city);
			}
		}
	}

	public void set_initialCities(Player p1, Player p2) {
		int count_armies = 0;
		while (count_armies < 20) {
			int rnd = new Random().nextInt(citiesCountry.size());
			if (!(p1.get_cities().contains(rnd)) && !(p2.get_cities().contains(rnd))) {
				City city = new City();
				city.set_id(rnd);
				city.set_color(p1.get_color());
				city.set_neighbours(citiesCountry.get(rnd));
				int armies = getRandomInteger(5, 1);
				if (armies + count_armies > 20) {
					armies = 20 - count_armies;
				}
				count_armies = count_armies + armies;
				city.set_armies(armies);
				all_cities.add(city);
				p1.get_cities().add(rnd);
			}
		}
	}

	public ArrayList<City> get_allCities(){
		return all_cities;
	}
	
	public static int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

}
