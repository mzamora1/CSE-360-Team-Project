package restaurant;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Checkout {
	
	public void order(ActionEvent a) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Order successfully sent! Please press \"OK\" to be redirected back to the menu.");
		alert.showAndWait();
		switchToMenu();
	}
	
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
}