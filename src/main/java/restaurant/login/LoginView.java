package restaurant.login;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import restaurant.App;
import restaurant.createAccount.CreateAccountController;
import restaurant.menu.MenuController;
import restaurant.users.GuestUser;

public class LoginView extends BorderPane {
    private final TextField usernameField = new TextField();

    private final TextField passwordField = new TextField();

    private final ImageView imageView = new ImageView(App.class.getResource("alfredologo.PNG").toString());

    private final Button guestBtn = new Button("Continue as Guest");
    private final Button createAccountBtn = new Button("Create Account");
    private final Button loginBtn = new Button("Login");

    private final VBox container = new VBox(imageView, usernameField, passwordField,
            createAccountBtn, loginBtn, guestBtn);

    public LoginView() {
        super();
        build();
    }

    private void build() {
        usernameField.setPromptText("username");
        passwordField.setPromptText("password");
        setCenter(container);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        imageView.setPreserveRatio(true);
        createAccountBtn.setOnAction(this::onCreateAccount);
        guestBtn.setOnAction(this::onContinueAsGuest);
        loginBtn.setOnAction(this::onLogin);
    }

    public void update(double maxWidth, double maxHeight) {
        container.setMaxWidth(inputWidth(maxWidth));
        imageView.setFitWidth(maxWidth * .5);
        imageView.setFitHeight(maxHeight * .5);
    }

    private double inputWidth(double maxWidth) {
        return maxWidth * 0.5;
    }

    private <T extends Event> void onCreateAccount(T event) {
        App.setRoot(new CreateAccountController());
    }

    private <T extends Event> void onContinueAsGuest(T event) {
        App.user = new GuestUser();
        App.cartItems.clear();
        App.setRoot(new MenuController());
    }

    private <T extends Event> void onLogin(T event) {
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            if (checkLogin()) {
                App.setRoot(new MenuController());
            }
        }
    }

    private boolean checkLogin() {
        if (App.user != null && App.user.getName().equals(usernameField.getText())
                && App.user.getPass().equals(passwordField.getText())) {
            return true;
        } else {
            return false;
        }
    }

}
