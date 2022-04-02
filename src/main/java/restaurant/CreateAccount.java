package restaurant;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CreateAccount {
    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    TextField cardNumField;
    @FXML
    TextField ccvField;
    @FXML
    TextField expriationField;
    @FXML
    RadioButton adminOption;

    @FXML
    void createAccount() throws IOException {
        App.setRoot("menu");
    }
}
