package restaurant;

import javafx.scene.Parent;

// TODO use custom events instead of Controller
// These methods will be called by the App when switching scenes
public abstract class Controller implements Updatable {
    public Controller() {
    }

    // construct and layout nodes on screen
    abstract public void build();

    // update width and height of nodes as needed
    abstract public void update(double maxWidth, double maxHeight);

    // getter for top-level node of this scene
    abstract public Parent getRoot();
}
