package model;

import model.agents.Agents;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Initial_Game g = new Initial_Game("Egypt");
		Helper help = new Helper();
		City[] cities = g.get_allCities();
		for (int i = 0; i < cities.length; i++) {
			//City c = cities[i];
			//System.out.println("ID: " + c.get_id() + " Color: " + c.get_color() + " Armies: " + c.get_armies()
					//+ " Neighbours: " + c.get_neighbours());
		}
		AgentFactory af = new AgentFactory();
		Agents g2 = af.get_agent("Aggressive agent");
		Agents g1 = af.get_agent("Aggressive agent");
		String color_turn = "Blue";
		String current_color = "Blue";
		int cost = 0;
		int quit = 0;
		boolean attacked1 = false;
		while (!help.test_goal(cities, current_color)) {
			attacked1 = false;
			if(cost > 100) {
				break;
			}
			if (color_turn == "Blue") {
				g1.placing_armies(cities, g.p1, help.calculate_bonus(g.p1));
				attacked1 = g1.attack(cities, g.p1, g.p2);
				if (!attacked1) {
					quit++;
				} else {
					quit = 0;
				}
				color_turn = g.p2.get_color();
				current_color = g.p1.get_color();
			} else {
				g2.placing_armies(cities, g.p2, help.calculate_bonus(g.p2));
				attacked1 = g2.attack(cities, g.p2, g.p1);
				if (!attacked1) {
					quit++;
				} else {
					quit = 0;
				}
				color_turn = g.p1.get_color();
				current_color = g.p2.get_color();
			}
			if (quit == 4) {
				break;
			}
			cost++;
			System.out.println("P"+current_color);
			for (int i = 0; i < cities.length; i++) {
				City c = cities[i];
				System.out.println("ID: " + c.get_id() + " Color: " + c.get_color() + " Armies: " + c.get_armies()
						+ " Neighbours: " + c.get_neighbours());
			}
		}
		if (!help.test_goal(cities, current_color)) {
			System.out.println("No one wins");
		} else {
			System.out.println("Player with color: " + current_color + " wins.");
		}
		for (int i = 0; i < cities.length; i++) {
			City c = cities[i];
			System.out.println("ID: " + c.get_id() + " Color: " + c.get_color() + " Armies: " + c.get_armies()
					+ " Neighbours: " + c.get_neighbours());
		}
	}

}
