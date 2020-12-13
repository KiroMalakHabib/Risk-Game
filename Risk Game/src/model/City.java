package model;

import java.util.ArrayList;

public class City {
	
	int armies;
	int id;
	String color;
	ArrayList<Integer> neighbours;
	public City() {
		id = -1;
		armies = 0;
		color = "";
		neighbours = new ArrayList<Integer>();
	}
	
	public void set_id(int my_id) {
		id = my_id;
	}
	
	public void set_armies(int my_armies) {
		armies = my_armies;
	}
	
	public void set_color(String my_color) {
		color = my_color;
	}
	
	public void set_neighbours(ArrayList<Integer> my_neighbours) {
		neighbours = my_neighbours;
	}
	
	public int get_id() {
		return id;
	}
	
	public int get_armies() {
		return armies;
	}
	
	public String get_color() {
		return color;
	}
	
	public ArrayList<Integer> get_neighbours(){
		return neighbours;
	}
}
