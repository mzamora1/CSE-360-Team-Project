package restaurant.old;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import restaurant.App;
import restaurant.users.Customer;

public class OldCreateAccount {
    public OldCreateAccount() {
    }

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField cardNumField;
    @FXML
    private TextField ccvField;
    @FXML
    private TextField expirationField;
    @FXML
    private RadioButton adminOption;

    @FXML
    private void createAccount() {
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
