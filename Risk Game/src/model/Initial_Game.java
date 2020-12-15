package model;

import java.util.ArrayList;
import java.util.Random;

public class Initial_Game {

	Player p1 = new Player();
	Player p2 = new Player();
	Data citiesOfCountry = new Data();
	int num_num_armies = 0;
	ArrayList<ArrayList<Integer>> citiesCountry;
	City[] all_cities;
	int size = 0;

	public Initial_Game(String country) {
		if (country.equals("Egypt")) {
			citiesCountry = citiesOfCountry.Egypt();
			num_num_armies = 20;
		} else {
			citiesCountry = citiesOfCountry.USA();
			num_num_armies = 30;
		}
		all_cities = new City[citiesCountry.size()];
		size = citiesCountry.size();
		p1.set_color("Blue");
		p1.set_armies(20);
		p2.set_color("Red");
		p2.set_armies(20);
		if (size % 2 != 0) {
			set_initialCities(p1, p2, size / 2 + 1);
			set_initialCities(p2, p1, size / 2);
		} else {
			set_initialCities(p1, p2, size / 2);
			set_initialCities(p2, p1, size / 2);
		}

	}

	public void set_initialCities(Player p1, Player p2, int first_armies) {

		while (first_armies > 0) {
			int rnd = new Random().nextInt(size);
			if (!(p1.get_cities().contains(rnd)) && !(p2.get_cities().contains(rnd))) {
				City city = new City();
				city.set_id(rnd);
				city.set_color(p1.get_color());
				city.set_neighbours(citiesCountry.get(rnd));
				city.set_armies(1);
				all_cities[rnd] = city;
				p1.get_cities().add(rnd);
				first_armies--;
			}
		}
		int remain_armies = num_num_armies - citiesCountry.size() / 2;

		while (remain_armies > 0) {
			int rnd = new Random().nextInt(p1.get_cities().size());
			int rnd2 = new Random().nextInt(4 + 1 - 1) + 1;
			if (remain_armies - rnd2 >= 0) {
				all_cities[p1.get_cities().get(rnd)]
						.set_armies(all_cities[p1.get_cities().get(rnd)].get_armies() + rnd2);
				remain_armies = remain_armies - rnd2;
			}
		}
	}

	public City[] get_allCities() {
		return all_cities;
	}

	public int get_size() {
		return size;
	}

	public static int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

}
