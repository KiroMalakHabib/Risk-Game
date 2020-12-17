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
import javafx.stage.Stage;
import model.City;
import model.Helper;
import model.Initial_Game;
import model.agents.Agents;

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
    
    private int lastBtnClicked;
    private Initial_Game g = new Initial_Game("Egypt");
    private Helper help = new Helper();
    private City[] cities = g.get_allCities();

    @FXML
    void stateBtnAction(MouseEvent event) {
    	myButtons.get(lastBtnClicked-1).setText(String.valueOf(Integer.parseInt(myButtons.get(lastBtnClicked-1).getText())+1));
    }
    
    public void intiateData(Agents agent1, Agents agent2, boolean isHuman) {
		if (!isHuman) {
			endTurnBtn.setVisible(false);
			attackLabel.setVisible(false);
			attackBtn.setVisible(false);
			addArmiesBtn.setVisible(false);
			warningLabel.setVisible(false);
			avArmiesTxtField.setVisible(false);
			addArmiesField.setVisible(false);
		}
	}

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
		state01.setOnAction(e->lastBtnClicked = 1);
		state02.setOnAction(e->lastBtnClicked = 2);
		state03.setOnAction(e->lastBtnClicked = 3);
		state04.setOnAction(e->lastBtnClicked = 4);
		state05.setOnAction(e->lastBtnClicked = 5);
		state06.setOnAction(e->lastBtnClicked = 6);
		state07.setOnAction(e->lastBtnClicked = 7);
		state08.setOnAction(e->lastBtnClicked = 8);
		state09.setOnAction(e->lastBtnClicked = 9);
		state10.setOnAction(e->lastBtnClicked = 10);
		state11.setOnAction(e->lastBtnClicked = 11);
		state12.setOnAction(e->lastBtnClicked = 12);
		state13.setOnAction(e->lastBtnClicked = 13);
		state14.setOnAction(e->lastBtnClicked = 14);
		state15.setOnAction(e->lastBtnClicked = 15);
		state16.setOnAction(e->lastBtnClicked = 16);
		state17.setOnAction(e->lastBtnClicked = 17);
		state18.setOnAction(e->lastBtnClicked = 18);
		state19.setOnAction(e->lastBtnClicked = 19);
		state20.setOnAction(e->lastBtnClicked = 20);
		state21.setOnAction(e->lastBtnClicked = 21);
		state22.setOnAction(e->lastBtnClicked = 22);
		state23.setOnAction(e->lastBtnClicked = 23);
		state24.setOnAction(e->lastBtnClicked = 24);
		state25.setOnAction(e->lastBtnClicked = 25);
		state26.setOnAction(e->lastBtnClicked = 26);
		state27.setOnAction(e->lastBtnClicked = 27);
		
		for(JFXButton button : myButtons) {
			button.setText("0");
		}
		
	}
}
