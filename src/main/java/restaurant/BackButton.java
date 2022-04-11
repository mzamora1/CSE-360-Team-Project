package restaurant;

import javafx.scene.control.Button;

public class BackButton extends Button {
    public BackButton() {
        super("Back");
        setOnAction(event -> {
            var goBackEvent = new AppEvent(this, event.getTarget(), AppEvent.GO_BACK);
            fireEvent(goBackEvent);
            App.onGoBack(event);
        });
    }
}
