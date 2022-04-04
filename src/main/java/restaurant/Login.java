package restaurant;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Login {

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;
    
    @FXML
    private ImageView myImageView;

    /*
     * @FXML
     * private void login() throws IOException {
     * 
     * if (usernameField.getText().equals(user.getName()) &&
     * passwordField.getText().equals(user.getPass()) ){
     * App.setRoot("menu");
     * }
     * }
     */
    Image myImage = new Image(getClass().getResourceAsStream("alfredologo.PNG"));
    
    public void displayImage() {
    	myImageView.setImage(myImage);
    }
    

    @FXML
    private void login() throws IOException {
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            if (checkLogin()) {
                App.setRoot("menu");
            }
        }
    }

    @FXML
    private void switchToCreateAccount() throws IOException {
        App.setRoot("createAccount");
    }

    // in this case we have a guest user
    @FXML
    private void switchToMenu() throws IOException {
        // if continue as guest is clicked, make a new user that is a guest with null
        // name and password.
        App.user = new User("Guest User", "", false);
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