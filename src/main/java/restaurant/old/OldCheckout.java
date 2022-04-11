package restaurant.old;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import restaurant.App;
import restaurant.cart.CartItem;
import restaurant.users.Customer;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
// import static restaurant.Menu.mypriceTotal;

public class OldCheckout implements Initializable {
	public OldCheckout() {
	}

	// random numbers for ppl + expected waiting time
	private static final int max = 20;
	private static final int min = 1;

	private static final Random randomNum = new Random();

	@FXML
	private Label price;

	@FXML
	private Label a;
	@FXML
	private Label b;

	@FXML
	private TextField cardNumField;
	@FXML
	private TextField ccvField;
	@FXML
	private TextField expirationField;
	@FXML
	private TextField emailField;

	@FXML
	private VBox cart;
	@FXML
	private ScrollPane cartContainer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int peopleInLine = min + randomNum.nextInt(max);
		a.setText("People in line: " + peopleInLine);
		b.setText("Expected waiting time: " + (peopleInLine * 5) + " minutes");

		updateTotalPrice(App.cartItems);

		App.safeCast(Customer.class, App.user).ifPresent(cust -> {
			cardNumField.setText(cust.getCardNum());
			ccvField.setText(cust.getCardCCV());
			expirationField.setText(cust.getCardExp());
			emailField.setText(cust.getEmail());
		});

		// someone will have to change the layout for the size to work right
		double cartPrefWidth = 150;// cartContainer.getPrefWidth();

		cart.getChildren().setAll(App.cartItems);
		// only need to update items since they are already built
		cart.getChildren().forEach(item -> ((CartItem) item).update(cartPrefWidth));
	}

	// clicking order button
	@FXML
	private void order(ActionEvent a) {
		// alarm window which switched back to menu after pressing confirmation
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Place Order?");
		alert.setHeaderText(
				"Press \"OK\" to REALLY place your order or close this window to cancel your order.");
		alert.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> {
					App.cartItems.clear();
					switchToMenu();
				});

	}

	// back button
	@FXML
	private void back(ActionEvent c) {
		App.goBack();
	}

	private void switchToMenu() {
		App.setRoot("menu");
	}

	private void updateTotalPrice(List<Node> cart) {
		float totalPrice = 0;
		for (var item : cart) {
			CartItem cartItem = (CartItem) item;
			totalPrice += cartItem.getPrice() * cartItem.getQuantity();
		}
		price.setText("Total Price: $" + totalPrice);
	}

}