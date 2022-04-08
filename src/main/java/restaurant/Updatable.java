package restaurant;

@FunctionalInterface
public interface Updatable {
    // will be called when 'this' is brought on screen by App
    // during goBack
    // useful for reassigning values that may be referencing garbage after a goBack
    // such as event handlers referencing a controller
    // since a new instance of the controller class may have been created after
    // switching to a new scene, these handlers could become stale garbage
    // that listen to Nodes that are no longer visible on screen
    public void update();
}
