package model.agents;

import model.City;
import model.Player;

public class HumanAgent implements Agents{
	
	private int selectedCity;
	private int attackedCity;
	private int numOfAttackingArmies;

	@Override
	public void placing_armies(City[] all_cities, Player p, int bonus_armies) {
		all_cities[selectedCity].set_armies(all_cities[selectedCity].get_armies() + bonus_armies);
		p.set_armies(p.get_armies() + bonus_armies);
	}

	@Override
	public boolean attack(City[] all_cities, Player p1, Player p2) {
		p2.set_armies(p2.get_armies() - all_cities[attackedCity].get_armies());
		p2.get_cities().remove((Integer) attackedCity);
		all_cities[attackedCity].set_color(p1.get_color());
		all_cities[attackedCity].set_armies(numOfAttackingArmies);
		all_cities[selectedCity].set_armies(all_cities[selectedCity].get_armies() - numOfAttackingArmies);
		p1.get_cities().add(attackedCity);
		
		return true;
	}

	public void setCity(int selected) {
		selectedCity = selected;
	}
	
	public void setAttacked(int selected, int attckingArmies) {
		attackedCity = selected;
		numOfAttackingArmies = attckingArmies;
	}
}
