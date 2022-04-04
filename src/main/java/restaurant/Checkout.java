package restaurant;

import java.util.Random;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class Checkout{

	// random numbers for ppl + expected waiting time
	int max = 20;
	int min = 1;

	Random randomNum = new Random();
	int num = min + randomNum.nextInt(max);

	@FXML
	private Label price;
	
	@FXML
	private Label a;
	@FXML
	private Label b;

	@FXML
	TextField cardNumField;
	@FXML
	TextField ccvField;
	@FXML
	TextField expirationField;
	@FXML
	TextField emailField;

	@FXML
	private VBox cart;

	@FXML
	private ScrollPane cartContainer;
	static double cartPrefWidth;

	@FXML
	public void initialize() {
		a.setText("People in line: " + num);
		b.setText("Expected waiting time: " + (num * 5) + " minutes");
		
		
		if (Customer.class.isInstance(App.user)) {
			Customer user = (Customer) App.user;
			cardNumField.setText(user.getCardNum());
			ccvField.setText(user.getCardCCV());
			expirationField.setText(user.getCardExp());
			emailField.setText(user.getEmail());
		}
		// someone will have to change the layout for the size to work right
		cartPrefWidth = 170;// cart.getPrefWidth();
		System.out.println("cart width: " + cartPrefWidth);
		Menu.cartItems.forEach(item -> ((CartItem) item).build(cartPrefWidth));
		cart.getChildren().setAll(Menu.cartItems);
	}

	// clicking order button
	public void order(ActionEvent a) throws IOException {
		// alarm window which switched back to menu after pressing confirmation
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Order successfully sent! Please press \"OK\" to be redirected back to the menu.");
		alert.showAndWait();

		switchToMenu();
	}

	// back button
	public void back(ActionEvent c) throws IOException {
		App.goBack();
	}

	private void switchToMenu() throws IOException {
		App.setRoot("menu");
	}

}