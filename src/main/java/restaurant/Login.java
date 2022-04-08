package restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Login {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ImageView myImageView;

    // Image myImage = new Image(getClass().getResourceAsStream("alfredologo.PNG"));

    // public void displayImage() {
    // myImageView.setImage(myImage);
    // }

    @FXML
    private void login() {
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            if (checkLogin()) {
                App.setRoot("menu");
            }
        }
    }

    @FXML
    private void switchToCreateAccount() {
        App.setRoot("createAccount");
    }

    // in this case we have a guest user
    @FXML
    private void switchToMenu() {
        // if continue as guest is clicked, make a new user that is a guest with null
        // name and password.
        App.user = new GuestUser();
        App.cartItems.clear();
        App.setRoot("menu");
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