package restaurant;

import javafx.scene.Parent;

// These methods will be called by the App when switching scenes
public abstract class Controller {
    public Controller() {
    }

    abstract public void build();

    abstract public void update(double maxWidth, double maxHeight);

    abstract public Parent getRoot();
}
