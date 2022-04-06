package restaurant;

@FunctionalInterface
public interface Loadable<T> {
    // will be called when 'this' is brought on screen by App
    // during goBack
    public T load();
}
