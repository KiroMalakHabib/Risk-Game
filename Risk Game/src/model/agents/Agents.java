package model.agents;

import model.City;
import model.Player;

public interface Agents {

	public void placing_armies(City[] all_cities, Player p, Player p2,int bonus_armies);

	public boolean attack(City[] all_cities, Player p1, Player p2);
}
