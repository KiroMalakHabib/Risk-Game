package fxml;


import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GreetingController implements Initializable{
    
    @FXML
    private JFXComboBox<String> agentOneBox;
    
    @FXML
    private JFXComboBox<String> agentTwoBox;
    
    @FXML
    private JFXButton startBtn;
    
    @FXML
    private Label warningLabel;
    
    @FXML
    private Label warningLabel1;
    
    boolean egyptBool = false;
    boolean usaBool = false;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		agentOneBox.getItems().addAll("Human agent",
				"Passive agent",
				"Aggressive agent",
				"Pacifist agent",
				"Greedy agent",
				"A* with heuristic",
				"Real time A*",
				"Minimax agent");
		agentTwoBox.getItems().addAll("Human agent",
				"Passive agent",
				"Aggressive agent",
				"Pacifist agent",
				"Greedy agent",
				"A* with heuristic",
				"Real time A*",
				"Minimax agent");
		
	}
    
    @FXML
    void btnAct(ActionEvent event) {
    	if (!egyptBool && !usaBool) {
    		warningLabel.setText("You should choose a country first to start a war!");
    		warningLabel.setVisible(true);
    	} else if (agentOneBox.getValue() == null || agentTwoBox.getValue() == null) {
    		warningLabel1.setVisible(true);
    	} else {
    		
    	}
    }
    
    @FXML
    void egyptPressed(MouseEvent event) {
    	egyptBool = true;
    	usaBool = false;
    	warningLabel.setText("Egypt is choosen.");
    	warningLabel.setVisible(true);
    }

    @FXML
    void usaPressed(MouseEvent event) {
    	usaBool = true;
    	egyptBool = false;
    	warningLabel.setText("USA is choosen.");
    	warningLabel.setVisible(true);
    }

}
