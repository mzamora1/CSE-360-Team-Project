package restaurant;

import javafx.scene.control.Button;

public class BackButton extends Button {
    public BackButton() {
        super("Back");
        setOnAction(event -> {
            var goBackEvent = new AppEvent(AppEvent.BEFORE_GO_BACK);
            fireEvent(goBackEvent);
            App.onGoBack(event);
            event.consume();
        });
    }
}
