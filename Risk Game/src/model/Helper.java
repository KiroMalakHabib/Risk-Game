package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper {

	public int calculate_bonus(Player p) {
		return Math.max(3, p.get_cities().size() / 3);
	}
	
	public int calculate_bonus_minimax(Player p, City[] cities) {
		int bonus =0;
		for(int i = 0; i< cities.length;i++) {
			if(cities[i].get_color().equals(p.get_color())) {
				bonus++;
			}
		}
		return Math.max(3, bonus/ 3);
	}

	public boolean test_goal(City[] cities, String color) {
		for (int i = 0; i < cities.length; i++) {
			if (!(cities[i].get_color().equals(color))) {
				return false;
			}
		}
		return true;
	}
	

	public int calculate_heuristsic(City []all_cities, Player p) {
		ArrayList<Integer> citiesOfPlayer = p.get_cities();
		int heuristic = 0;
		for(int  i = 0; i < all_cities.length; i++) {
			City city = all_cities[i];
			if(!(citiesOfPlayer.contains(city.get_id()))) {
				heuristic += city.get_armies();
			}
		}
		return heuristic;
	}
	
	public int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}
	
	public HashMap<City[],City[]> get_children(City[] state, Player p, int bonus_armies){
		//parent : cities after placing armies to specific city
		//child : cities after attack by this specific city
		HashMap<City[],City[]> childern_states = new HashMap<City[], City[]>();
		ArrayList<City[]> states_after_placing = new ArrayList<City[]>();
		for (int i =0; i< state.length; i++) {
			City c = state[i];
			if (c.get_color().equals(p.get_color())) {
				City[] copyState = state.clone();
				int armies = copyState[i].get_armies();
				copyState[i].set_armies(armies + bonus_armies);
				states_after_placing.add(copyState);
			}
			}
		for (int i =0; i< states_after_placing.size(); i++) {
			City[] state_before_attack = states_after_placing.get(i);
			for (int j =0; j < state_before_attack.length; j++) {
				City c = state_before_attack[j];
				if (c.get_color().equals(p.get_color())) { //We can attack by this city
					for (int k =0; k < c.neighbours.size(); k++) {
						int neighbour_id = c.neighbours.get(k);
						City neighbour = state_before_attack[neighbour_id];
						if (!(neighbour.get_color().equals(p.get_color())) && neighbour.armies < c.get_armies() - 1) { //we can attack this neighbour
							City[] copyState =  state_before_attack.clone();
							int random = getRandomInteger(c.get_armies() - 1, neighbour.armies + 1);
							copyState[neighbour_id].set_color(p.get_color());
							copyState[neighbour_id].set_armies(random);
							copyState[c.get_id()].set_armies(c.get_armies() - random);
							//parent = state_before_attack , child = copyState;
							childern_states.put(state_before_attack, copyState);
						}
					}
				}
			}
		}
		return childern_states;
	}
	
	public void update_players(City[] all_cities, Player p1, Player p2) {
		int armiesOfPlayer1 = 0;
		int armiesOfPlayer2 = 0;
		for(int i = 0; i < all_cities.length;i++) {
			City city = all_cities[i];
			if(city.get_color().equals("Blue")) {
				armiesOfPlayer1 += city.get_armies();
			}else {
				armiesOfPlayer2 += city.get_armies();
			}
			if(city.get_color().equals(p1.get_color()) && !(p1.get_cities().contains(city.get_id()))) {
				p1.get_cities().add(city.get_id());
				p2.get_cities().remove((Integer) city.get_id());
			}
		}
		p1.set_armies(armiesOfPlayer1);
		p2.set_armies(armiesOfPlayer2);
	}
}
