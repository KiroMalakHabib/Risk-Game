package model;

import javafx.util.Pair;

public class Node_Minimax {

	private int utility;
	private  Pair<City[],City[]> child = null;
	
	public Node_Minimax() {
		
	}
	public void set_utility(int h) {
		utility = h;
	}
	public void set_child(Pair<City[],City[]> c) {
		child = c;
	}
	public int get_utility() {
		return utility;
	}
	public Pair<City[],City[]> get_child(){
		return child;
	}
}
