package restaurant;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateAccountView extends BorderPane {

    private final BackButton backBtn = new BackButton();
    private final Label createAccountLabel = new Label("Create an Account");
    private final TextField email = new TextField();
    private final TextField password = new TextField();
    private final TextField cardNumber = new TextField();
    private final TextField ccv = new TextField();
    private final TextField expiration = new TextField();
    private final CheckBox adminOption = new CheckBox("Admin Account");
    private final Button submitBtn = new Button("Submit");
    private final HBox partialCard = new HBox(ccv, expiration);
    private final VBox inputContainer = new VBox(email, password, cardNumber,
            partialCard);

    private final VBox container = new VBox(createAccountLabel, inputContainer,
            adminOption, submitBtn);

    public CreateAccountView() {
        super();
        build();
    }

    private void build() {
        email.setPromptText("email...");
        password.setPromptText("password...");
        cardNumber.setPromptText("card number...");
        ccv.setPromptText("ccv");
        expiration.setPromptText("ex: (mm/yy)");
        container.setAlignment(Pos.CENTER);
        partialCard.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        partialCard.setSpacing(10);
        inputContainer.setSpacing(10);
        setTop(backBtn);
        setCenter(container);
        submitBtn.setOnAction(this::onCreateAccount);
    }

    private double inputWidth(double maxWidth) {
        return maxWidth * 0.5;
    }

    public void update(double maxWidth, double maxHeight) {
        inputContainer.setMaxWidth(inputWidth(maxWidth));
    }

    private <T extends Event> void onCreateAccount(T event) {
        if (!email.getText().isEmpty() && !password.getText().isEmpty() && !cardNumber.getText().isEmpty()
                && !ccv.getText().isEmpty()) {
            var newUser = new Customer(email.getText(), password.getText(), adminOption.isSelected());

            newUser.setCardCCV(ccv.getText());
            newUser.setCardExp(expiration.getText());
            newUser.setCardNum(cardNumber.getText());

            App.user = newUser;

            App.setRoot(new LoginController());
        }
    }
}
