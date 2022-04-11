package restaurant.checkout;

import java.util.Random;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import restaurant.App;
import restaurant.BackButton;
import restaurant.cart.Cart;
import restaurant.cart.CartItem;
import restaurant.menu.MenuController;
import restaurant.users.Customer;

public class CheckoutView extends BorderPane {
    private final BackButton backBtn = new BackButton();
    private final Label title = new Label("Checkout");
    private final AnchorPane top = new AnchorPane(title, backBtn);
    private final Cart cart = new Cart();
    private final Label paymentLabel = new Label("Payment Info");
    private final TextField cardNumber = new TextField();
    private final TextField ccv = new TextField();
    private final TextField expiration = new TextField();
    private final Label contactLabel = new Label("Contact Info");
    private final TextField email = new TextField();
    private final TextField phoneNumber = new TextField();
    private final Label priceTotal = new Label("Total Price: $0.00");
    private final Button orderBtn = new Button("Order");
    private final Label peopleInLineLabel = new Label("People in line: ");
    private final Label expectedWaitLabel = new Label("Expected wait: ");
    private final HBox lineContainer = new HBox(peopleInLineLabel, expectedWaitLabel);
    private final VBox center = new VBox(cart, paymentLabel, cardNumber, ccv, expiration, contactLabel, email,
            phoneNumber, priceTotal, orderBtn, lineContainer);

    private final VBox footer = new VBox();

    // random numbers for ppl + expected waiting time
    private static final int max = 20;
    private static final int min = 1;

    private static final Random randomNum = new Random();

    private final int peopleInLine = min + randomNum.nextInt(max);

    public CheckoutView() {
        super();
        build();
    }

    private void build() {
        setPeopleInLine();
        setExpectedWait();
        updateTotalPrice();
        email.setPromptText("email...");
        phoneNumber.setPromptText("phone number...");
        cardNumber.setPromptText("card number...");
        ccv.setPromptText("ccv");
        expiration.setPromptText("ex: (mm/yy)");
        App.safeCast(Customer.class, App.user).ifPresent(cust -> {
            cardNumber.setText(cust.getCardNum());
            ccv.setText(cust.getCardCCV());
            expiration.setText(cust.getCardExp());
            email.setText(cust.getEmail());
        });

        AnchorPane.setLeftAnchor(backBtn, 10d);
        // make title fill up all of top
        AnchorPane.setLeftAnchor(title, 0d);
        AnchorPane.setRightAnchor(title, 0d);
        AnchorPane.setBottomAnchor(title, 0d);
        // center it in that space
        title.setAlignment(Pos.CENTER);
        setTop(top);

        cart.setHbarPolicy(ScrollBarPolicy.NEVER);

        center.setAlignment(Pos.CENTER);
        center.setSpacing(10);
        lineContainer.setAlignment(Pos.CENTER);
        lineContainer.setSpacing(30);
        setCenter(center);

        footer.setMinHeight(20);
        setBottom(footer);

        orderBtn.setOnAction(this::onOrder);
    }

    public void update(double maxWidth, double maxHeight) {
        center.setMaxWidth(maxWidth * 0.75);
        cart.update(maxWidth * 0.75, maxHeight * .5);
    }

    private void setPeopleInLine() {
        peopleInLineLabel.setText("People in line: " + peopleInLine);
    }

    private void setExpectedWait() {
        expectedWaitLabel.setText("Expected wait: " + peopleInLine * 5);
    }

    private void updateTotalPrice() {
        float totalPrice = 0;
        for (var item : App.cartItems) {
            CartItem cartItem = (CartItem) item;
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }
        priceTotal.setText("Total Price: $" + totalPrice);
    }

    private <T extends Event> void onOrder(T event) {
        // alarm window which switched back to menu after pressing confirmation
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Place Order?");
        alert.setHeaderText(
                "Press \"OK\" to REALLY place your order or close this window to cancel your order.");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    App.cartItems.clear();
                    App.setRoot(new MenuController());
                });
    }
}
