package restaurant;

import javafx.scene.control.Button;

public class BackButton extends Button {
    public BackButton() {
        super("Back");
        setOnAction(App::onGoBack);
    }
}
