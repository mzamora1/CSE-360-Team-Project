package restaurant;

@FunctionalInterface
public interface Updatable {
    // will be called when 'this' is brought on screen by App
    // during goBack
    public void update();
}
