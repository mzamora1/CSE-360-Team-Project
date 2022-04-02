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
    TextField expirationField;
    @FXML
    RadioButton adminOption;

    @FXML
    void createAccount() throws IOException {
        if (!emailField.getText().isEmpty() && !passwordField.getText().isEmpty() && !cardNumField.getText().isEmpty()
                && !ccvField.getText().isEmpty()) {
            var newUser = new Customer(emailField.getText(), passwordField.getText(), adminOption.isSelected());

            newUser.setCardCCV(ccvField.getText());
            newUser.setCardExp(expirationField.getText());
            newUser.setCardNum(cardNumField.getText());

            App.user = newUser;

            App.setRoot("login");
        }
    }
}
