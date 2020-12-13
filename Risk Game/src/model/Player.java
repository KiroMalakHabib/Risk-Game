package model;

import java.util.ArrayList;

public class Player {
	int armies;
	String color;
	ArrayList<Integer> my_cities;

	public Player() {
		this.armies = 20;
		this.color = "Blue";
		this.my_cities = new ArrayList<Integer>();
	}

	public void set_armies(int my_armies) {
		armies = my_armies;
	}

	public void set_color(String my_color) {
		color = my_color;
	}

	public void set_cities(ArrayList<Integer> cities) {
		my_cities = cities;
	}

	public int get_armies() {
		return armies;
	}

	public String get_color() {
		return color;
	}

	public ArrayList<Integer> get_cities() {
		return my_cities;
	}
}
