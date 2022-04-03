package restaurant;

import java.util.Random;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;



public class Checkout {
	
	//random numbers for ppl + expected waiting time
	int max = 20;
	int min = 1;
	
	Random randomNum = new Random();
    int num = min + randomNum.nextInt(max);
  
    
	@FXML
	private Label a;
	@FXML
	private Label b;
	
	@FXML
	public void initialize() {
		a.setText("People in line: " + num);
		b.setText("Expected waiting time: " + (num * 5) + " minutes");
		
	}
	
	//clicking order button
	public void order(ActionEvent a) throws IOException {
		//alarm window which switched back to menu after pressing confirmation
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Order successfully sent! Please press \"OK\" to be redirected back to the menu.");
		alert.showAndWait();
	
		switchToMenu();
	}
	//back button
	public void back(ActionEvent c) throws IOException {
		switchToMenu();
	}
	
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    
    
}