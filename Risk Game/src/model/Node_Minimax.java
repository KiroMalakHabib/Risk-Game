package model;

import java.util.HashMap;
import java.util.Map;

public class Node_Minimax {

	private int heuristic;
	private  City[] child = null;
	
	public Node_Minimax() {
		
	}
	public void set_heueistic(int h) {
		heuristic = h;
	}
	public void set_child(City[] c) {
		child = c;
	}
	public int get_heuristic() {
		return heuristic;
	}
	public City[] get_child(){
		return child;
	}
}
