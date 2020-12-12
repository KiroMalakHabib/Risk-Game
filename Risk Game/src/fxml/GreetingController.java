package fxml;


import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class GreetingController implements Initializable{
    
    @FXML
    private JFXComboBox<String> agentOneBox;
    
    @FXML
    private JFXComboBox<String> agentTwoBox;
    
    @FXML
    private JFXButton startBtn;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
    
    @FXML
    void btnAct(ActionEvent event) {

    }
    
    @FXML
    void egyptPressed(MouseEvent event) {

    }

    @FXML
    void usaPressed(MouseEvent event) {

    }

}
