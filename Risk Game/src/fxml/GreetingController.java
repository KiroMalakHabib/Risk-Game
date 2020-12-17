package fxml;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

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
import model.AgentFactory;
import model.agents.Agents;

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
    		// defining agents.
    		AgentFactory af = new AgentFactory();
    		Agents Agent1 = af.get_agent(agentOneBox.getValue());
    		Agents Agent2 = af.get_agent(agentTwoBox.getValue());
    		boolean isHuman = false;
    		if (agentOneBox.getValue() == "Human agent" || agentTwoBox.getValue() =="Human agent") {
    			isHuman = true;
    		}
    		//open the Game.
			try {
				if (egyptBool) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxml/GameEgypt.fxml"));
					Parent gameParent = loader.load();
					Scene gameScene = new Scene(gameParent);
					//access StoreController.
					GameEgyptController controller = loader.getController();
					controller.intiateData(Agent1, Agent2, isHuman);
					//this line gets the stage information.
					Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
					window.setScene(gameScene);
					window.setTitle("Conquer Egypt");
//					window.setResizable(false);
					window.show();
				} else {
					//access StoreController.
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxml/GameUsa.fxml"));
					Parent gameParent = loader.load();
					Scene gameScene = new Scene(gameParent);
					//access StoreController.
					GameUSAController controller = loader.getController();
					controller.intiateData(Agent1, Agent2, isHuman);
					//this line gets the stage information.
					Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
					window.setScene(gameScene);
					window.setTitle("Conquer USA");
//					window.setResizable(false);
					window.show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
