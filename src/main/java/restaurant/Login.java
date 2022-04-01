package restaurant;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    private void login() throws IOException {
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty())
            App.setRoot("menu");
    }

    @FXML
    private void switchToCreateAccount() throws IOException {
        App.setRoot("createAccount");
    }
}
