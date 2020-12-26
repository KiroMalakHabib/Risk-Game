package fxml;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.City;
import model.Helper;
import model.Initial_Game;
import model.agents.Agents;
import model.agents.HumanAgent;



public class GameEgyptController implements Initializable{
	ArrayList<JFXButton> myButtons = new ArrayList<JFXButton>();
	
	@FXML
    private JFXButton state01;

    @FXML
    private JFXButton state02;

    @FXML
    private JFXButton state03;

    @FXML
    private JFXButton state04;

    @FXML
    private JFXButton state05;

    @FXML
    private JFXButton state06;

    @FXML
    private JFXButton state07;

    @FXML
    private JFXButton state08;

    @FXML
    private JFXButton state09;

    @FXML
    private JFXButton state10;

    @FXML
    private JFXButton state11;

    @FXML
    private JFXButton state12;

    @FXML
    private JFXButton state13;

    @FXML
    private JFXButton state14;

    @FXML
    private JFXButton state15;

    @FXML
    private JFXButton state16;

    @FXML
    private JFXButton state17;

    @FXML
    private JFXButton state18;

    @FXML
    private JFXButton state19;

    @FXML
    private JFXButton state20;

    @FXML
    private JFXButton state21;

    @FXML
    private JFXButton state22;

    @FXML
    private JFXButton state23;

    @FXML
    private JFXButton state24;

    @FXML
    private JFXButton state25;

    @FXML
    private JFXButton state26;

    @FXML
    private JFXButton state27;
    
    @FXML
    private JFXTextField addArmiesField;

    @FXML
    private JFXTextField avArmiesTxtField;

    @FXML
    private Label warningLabel;

    @FXML
    private JFXButton addArmiesBtn;

    @FXML
    private JFXButton attackBtn;

    @FXML
    private Label attackLabel;

    @FXML
    private JFXButton endTurnBtn;
    
    @FXML
    private JFXButton exitGameBtn;
    
    @FXML
    private JFXButton startSimBtn;

    @FXML
    void exitGameAct(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/Greeting.fxml"));
			Parent greeting = loader.load();
			
			Scene greetingScene = new Scene(greeting);
			
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(greetingScene);
			window.setTitle("Risk Game");
			window.show();
			window.setResizable(false);
		} catch (Exception e) {
			// Nothing happens.
		}
    }
    
    private int lastBtnClicked = 0;
    private int prevSelectedBtn;
    private Agents agent1, agent2;
    private boolean isHuman1, isHuman2;
    private int playerTurn = 0;
    private Initial_Game g = new Initial_Game("Egypt");
    private Helper helpH = new Helper();
    private City[] citiesH = g.get_allCities();
    private int avArmiesInt = 0;

    @FXML
    void stateBtnAction(MouseEvent event) {
    	// if the player does not attack and re push another city of his own disable the other player cities
    	if (playerTurn == 0) {
    		disableCities("Red");
    	} else {
    		disableCities("Blue");
    	}
    	
    	// if no armies to add disable add armies button.
    	if (avArmiesInt == 0) {
    		addArmiesBtn.setDisable(true);
        	addArmiesField.setDisable(true);
        	if (playerTurn == 0) {
        		enableCities("Red");
        	} else {
        		enableCities("Blue");
        	}
        	if ((citiesH[lastBtnClicked-1].get_color().equals("Blue") && playerTurn == 0) ||
        			(citiesH[lastBtnClicked-1].get_color().equals("Red") && playerTurn == 1)) {
        		warningLabel.setText("Choose opponent near city to attack");
        		enableCities("Blue");
        		enableCities("Red");
        	} else {
        		attackBtn.setDisable(false);
        		warningLabel.setText("Press on attack button");
        	}
        	
    	} else {
    		addArmiesBtn.setDisable(false);
        	addArmiesField.setDisable(false);
        	String available = "availble =  ";
        	available += Integer.toString(avArmiesInt);
        	avArmiesTxtField.setText(available);
    	}
    	
    }
    
    @FXML
    void addArmiesBtnAct(ActionEvent event) {
    	if (isInt(addArmiesField.getText())) {
    		int addedArmies = Integer.parseInt(addArmiesField.getText());
    		if (addedArmies > avArmiesInt) {// if added armies are more than bounce armies drop an error.
    			warningLabel.setText("added armies is more than available armies");
    		} else {// add armies are less than or equal bounce armies.
    			warningLabel.setText("Choose a state from your own to enable buttons.");
    			attackBtn.setDisable(true);
    	    	addArmiesBtn.setDisable(true);
    	    	addArmiesField.setDisable(true);
    	    	// add new armies
    	    	if (playerTurn == 0) {
    	    		// player 1 is human
    	    		((HumanAgent) agent1).setCity(lastBtnClicked-1);
    	    		agent1.placing_armies(citiesH, g.getPlayer1(), addedArmies);
    	    		// decrease available armies
    	    		avArmiesInt -= addedArmies;
    	    		String available = "availble =  ";
    	        	available += Integer.toString(avArmiesInt);
    	        	avArmiesTxtField.setText(available);
    	    	} else {
    	    		// player 2 is human
    	    		((HumanAgent) agent2).setCity(lastBtnClicked-1);
    	    		agent2.placing_armies(citiesH, g.getPlayer1(), addedArmies);
    	    		// decrease available armies
    	    		avArmiesInt -= addedArmies;
    	    		String available = "availble =  ";
    	        	available += Integer.toString(avArmiesInt);
    	        	avArmiesTxtField.setText(available);
    	    	}
    	    	//update map
            	updateMap();
            	addArmiesField.setText("");
    		}
    	} else {
    		warningLabel.setText("you should enter an integer.");
    	}
    	if (avArmiesInt == 0) {
    		endTurnBtn.setDisable(false);
    	}
    }

    @FXML
    void attackBtnAct(ActionEvent event) {
    	// check previous button belongs to player turn color
    	if (playerTurn == 0) {
    		if (citiesH[prevSelectedBtn-1].get_color().equals("Blue") 
    				&& citiesH[lastBtnClicked-1].get_color().equals("Red")) {
    			if (citiesH[prevSelectedBtn-1].get_armies() - 1 > citiesH[lastBtnClicked-1].get_armies()) {
    				if (citiesH[prevSelectedBtn-1].get_neighbours().contains(lastBtnClicked-1)) {
    					((HumanAgent) agent1).setCity(prevSelectedBtn-1);
    					((HumanAgent) agent1).setAttacked(lastBtnClicked-1);
    					agent1.attack(citiesH,  g.getPlayer1(),  g.getPlayer2());
    					warningLabel.setText("attacked successfully");
    					
    				} else {
    					warningLabel.setText("the two cities are not neighbours");
    				}
    			} else {
    				warningLabel.setText("your city has a leakage of armies");
    			}
    		} else {
    			warningLabel.setText("the two cities belong to the same army");
    		}
    		disableCities("Red");
        	attackBtn.setDisable(true);
    	} else {
    		if (citiesH[prevSelectedBtn-1].get_color().equals("Red") 
    				&& citiesH[lastBtnClicked-1].get_color().equals("Blue")) {
    			if (citiesH[prevSelectedBtn-1].get_armies() - 1 > citiesH[lastBtnClicked-1].get_armies()) {
    				if (citiesH[prevSelectedBtn-1].get_neighbours().contains(lastBtnClicked-1)) {
    					((HumanAgent) agent2).setCity(prevSelectedBtn-1);
    					((HumanAgent) agent2).setAttacked(lastBtnClicked-1);
    					agent2.attack(citiesH,  g.getPlayer2(),  g.getPlayer1());
    					warningLabel.setText("attacked successfully");
    				} else {
    					warningLabel.setText("the two cities are not neighbours");
    				}
    			} else {
    				warningLabel.setText("your city has a leakage of armies");
    			}
    		} else {
    			warningLabel.setText("the two cities belong to the same army");
    		}
    		disableCities("Blue");
        	attackBtn.setDisable(true);      	
    	}
    	updateMap();
    	isGameEnded();
    }
    
    @FXML
    void endTurnBtnAct(ActionEvent event) {
    	if (isGameEnded()) {
    		gameEndedChanges();
    	} else {
    		playerTurn += 1;
        	playerTurn %= 2;
        	if (playerTurn == 0) {
        		if (isHuman1) {
        			humansPlay();
        		} else {
        			nonHumanPlay();
        			System.out.println("non human next");
        		}
        	} else {
        		if (isHuman2) {
        			humansPlay();
        		} else {
        			nonHumanPlay();
        			System.out.println("non human next");
        		}
        	}
    	}
    }
    
    
    // initialize agents and choose which function to begin in case of presence of any human agents 
    public void intiateData(Agents agent1, Agents agent2, boolean isHuman1, boolean isHuman2) {
    	this.isHuman1 = isHuman1;
    	this.isHuman2 = isHuman2;
    	this.agent1 = agent1;
		this.agent2 = agent2;
		if (!isHuman1 && !isHuman2) {
			endTurnBtn.setVisible(false);
			attackLabel.setVisible(false);
			attackBtn.setVisible(false);
			addArmiesBtn.setVisible(false);
			warningLabel.setVisible(false);
			avArmiesTxtField.setVisible(false);
			addArmiesField.setVisible(false);
			startSimBtn.setVisible(true);
			for(JFXButton button : myButtons) {
				button.setDisable(true);
			}
		} else if (!isHuman1 && isHuman2){
			nonHumanPlay();
		} else {
			humansPlay();
		}
	}
    
    //simulate two non human agents
    @FXML
    void startSimAct(ActionEvent event) {
    	Initial_Game g = new Initial_Game("Egypt");
	    Helper help = new Helper();
	    City[] cities = g.get_allCities();
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
				agent1.placing_armies(cities, g.getPlayer1(), help.calculate_bonus(citiesH, g.getPlayer1()));
				attacked1 = agent1.attack(cities, g.getPlayer1(), g.getPlayer2());
				if (!attacked1) {
					quit++;
				} else {
					quit = 0;
				}
				color_turn = g.getPlayer2().get_color();
				current_color = g.getPlayer1().get_color();
			} else {
				agent2.placing_armies(cities, g.getPlayer2(), help.calculate_bonus(citiesH, g.getPlayer2()));
				attacked1 = agent2.attack(cities, g.getPlayer2(), g.getPlayer1());
				if (!attacked1) {
					quit++;
				} else {
					quit = 0;
				}
				color_turn = g.getPlayer1().get_color();
				current_color = g.getPlayer2().get_color();
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
				myButtons.get(i).setText(String.valueOf(c.get_armies()));
				if (c.get_color().equals("Blue")) {
					myButtons.get(i).setStyle("-fx-background-color: #20B2AA");
				} else {
					myButtons.get(i).setStyle("-fx-background-color: #f08080");
				}
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
    
    // initialize controller. 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myButtons.add(state01);
		myButtons.add(state02);
		myButtons.add(state03);
		myButtons.add(state04);
		myButtons.add(state05);
		myButtons.add(state06);
		myButtons.add(state07);
		myButtons.add(state08);
		myButtons.add(state09);
		myButtons.add(state10);
		myButtons.add(state11);
		myButtons.add(state12);
		myButtons.add(state13);
		myButtons.add(state14);
		myButtons.add(state15);
		myButtons.add(state16);
		myButtons.add(state17);
		myButtons.add(state18);
		myButtons.add(state19);
		myButtons.add(state20);
		myButtons.add(state21);
		myButtons.add(state22);
		myButtons.add(state23);
		myButtons.add(state24);
		myButtons.add(state25);
		myButtons.add(state26);
		myButtons.add(state27);
		state01.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 1;});
		state02.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 2;});
		state03.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 3;});
		state04.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 4;});
		state05.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 5;});
		state06.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 6;});
		state07.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 7;});
		state08.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 8;});
		state09.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 9;});
		state10.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 10;});
		state11.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 11;});
		state12.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 12;});
		state13.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 13;});
		state14.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 14;});
		state15.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 15;});
		state16.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 16;});
		state17.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 17;});
		state18.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 18;});
		state19.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 19;});
		state20.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 20;});
		state21.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 21;});
		state22.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 22;});
		state23.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 23;});
		state24.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 24;});
		state25.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 25;});
		state26.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 26;});
		state27.setOnAction(e->{prevSelectedBtn = lastBtnClicked;lastBtnClicked = 27;});
	}
	

	// check if a string is an integer or not
	private static boolean isInt(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	

	// function to update map and visualize new cities assignment
	private void updateMap() {
		for (int i = 0; i < citiesH.length; i++) {
			City c = citiesH[i];
			System.out.println("ID: " + c.get_id() + " Color: " + c.get_color() + " Armies: " + c.get_armies()
					+ " Neighbours: " + c.get_neighbours());
			myButtons.get(i).setText(String.valueOf(c.get_armies()));
			if (c.get_color().equals("Blue")) {
				myButtons.get(i).setStyle("-fx-background-color: #20B2AA");
			} else {
				myButtons.get(i).setStyle("-fx-background-color: #f08080");
			}
		}
	}
	
	// function to disable cities with specific color
	private void disableCities(String theColor) {
		for(int i = 0; i < citiesH.length; i++) {
			City c = citiesH[i];
			if (c.get_color().equals(theColor)) {
				myButtons.get(i).setDisable(true);
			}
		}
	}
	
	// function to enable cities with specific color
	private void enableCities(String theColor) {
		for(int i = 0; i < citiesH.length; i++) {
			City c = citiesH[i];
			if (c.get_color().equals(theColor)) {
				myButtons.get(i).setDisable(false);
			}
		}
	}
	
	
	// do changes to buttons and fields if a winner is declared.
	private void gameEndedChanges() {
    	addArmiesBtn.setVisible(false);
		attackBtn.setVisible(false);
		avArmiesTxtField.setVisible(false);
		addArmiesField.setVisible(false);
		warningLabel.setVisible(false);
		endTurnBtn.setVisible(false);
		attackLabel.setVisible(true);
		disableCities("Blue");
		disableCities("Red");
		if (playerTurn == 0) {
			attackLabel.setText("Blue player is the WINNER");
			attackLabel.setTextFill(Color.web("#20B2AA"));
		} else {
			attackLabel.setText("Red player is the WINNER");
			attackLabel.setTextFill(Color.web("#f08080"));
		}
    }
	
	// check if the game is finished
    private boolean isGameEnded() {
    	System.out.println(playerTurn);
    	if (helpH.test_goal(citiesH, "Blue")) {
    		playerTurn = 0;
    		return helpH.test_goal(citiesH, "Blue");
    	} else if (helpH.test_goal(citiesH, "Red")) {
    		playerTurn = 1;
    		return helpH.test_goal(citiesH, "Red");
    	}
    	return false;
    }
    
 // used to fill the map before the first turn
    private boolean firstTurn  = true;
    
    //non human agent turn in case of human agent presence.
    private void nonHumanPlay() {
    	isGameEnded();
		if(isHuman2) {
			System.out.println("non human agent 1");
			// non human agent is Blue colored
			// place bonus armies
			agent1.placing_armies(citiesH, g.getPlayer1(), helpH.calculate_bonus(citiesH, g.getPlayer1()));
			// attack if possible
			agent1.attack(citiesH, g.getPlayer1(), g.getPlayer2());
			playerTurn = 1;
		} else if (isHuman1) {
			System.out.println("non human agent 2");
			// non human agent is Red colored
			// place bonus armies
			agent2.placing_armies(citiesH, g.getPlayer2(), helpH.calculate_bonus(citiesH, g.getPlayer2()));
			// attack if possible
			agent2.attack(citiesH, g.getPlayer2(), g.getPlayer1());
			playerTurn = 0;
		}
    	updateMap();
    	if (isGameEnded()) {
    		gameEndedChanges();
    	} else {
    		humansPlay();
    	}
    }
    
    // human agent turn
    private void humansPlay() {
    	isGameEnded();
    	warningLabel.setText("Choose a state from your own to enable buttons.");
    	if (firstTurn) {
    		updateMap();
    		firstTurn = false;
    	}
		if ((!isHuman1 && isHuman2) || (isHuman1 && !isHuman2)) {
			// only one agent is a human agent
    		System.out.println("Here 1 human");
    		if(isHuman1) {
    			// agent 1 is human & agent 2 is not human
    			// enable my cities
    			enableCities("Blue");
    			// disable other player cities
    			disableCities("Red");
    			//calculate bounce armies
    			avArmiesInt = helpH.calculate_bonus(citiesH, g.getPlayer1());
    			//show bounce armies
    			String available = "availble =  ";
            	available += Integer.toString(avArmiesInt);
            	avArmiesTxtField.setText(available);
    			// allow to choose a city of your own
    			// human place armies using addArmiesBtn
    			// human should place all available armies
    			// human may attack opponent
    			
    		} else if (isHuman2) {
    			// agent 1 is not human & agent 2 is human
    			// enable my cities
    			enableCities("Red");
    			// disable other player cities
    			disableCities("Blue");
    			//calculate bounce armies
    			avArmiesInt = helpH.calculate_bonus(citiesH, g.getPlayer2());
    			//show bounce armies
    			String available = "availble =  ";
            	available += Integer.toString(avArmiesInt);
            	avArmiesTxtField.setText(available);
    			// allow to choose a city of your own
    			// human place armies using addArmiesBtn
    			// human should place all available armies
    			// human may attack opponent
    		}
    	} else {
    		// agent 1 is human & agent 2 is human
    		System.out.println("Here 2 humans");
    		if (playerTurn == 0) {
    			// enable my cities
    			enableCities("Blue");
    			// disable other player cities
    			disableCities("Red");
    			//calculate bounce armies
    			avArmiesInt = helpH.calculate_bonus(citiesH, g.getPlayer1());
    			//show bounce armies
    			String available = "availble =  ";
            	available += Integer.toString(avArmiesInt);
            	avArmiesTxtField.setText(available);
    			// allow to choose a city of your own
    			// human place armies using addArmiesBtn
    			// human should place all available armies
    		} else {
    			// enable my cities
    			enableCities("Red");
    			// disable other player cities
    			disableCities("Blue");
    			//calculate bounce armies
    			avArmiesInt = helpH.calculate_bonus(citiesH, g.getPlayer2());
    			//show bounce armies
    			String available = "availble =  ";
            	available += Integer.toString(avArmiesInt);
            	avArmiesTxtField.setText(available);
    			// allow to choose a city of your own
    			// human place armies using addArmiesBtn
    			// human should place all available armies
    		}	
    	}
    }
    
}
