package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Initial_Game Game = new Initial_Game("Egypt");
		Player p1 = Game.p1;
		Player p2 = Game.p2;
		ArrayList<City> cities1 = Game.all_cities;
        for (int i = 0; i < cities1.size(); i++) {
            System.out.println("id: " + cities1.get(i).get_id());
            System.out.println("color: " + cities1.get(i).get_color());
            System.out.println("armies: " + cities1.get(i).get_armies());  
        }
        for (int i = 0; i < p1.my_cities.size(); i++) {
        	System.out.println(p1.my_cities.get(i));
        }
        System.out.println("----------------------------------");
        for (int i = 0; i < p2.my_cities.size(); i++) {
        	System.out.println(p2.my_cities.get(i));
        }
        
	}

}
