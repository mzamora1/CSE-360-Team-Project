package restaurant;

@FunctionalInterface
public interface Saveable {
    // called before App switches root
    public void save();
}
