package restaurant;

// Interface for both CartItems and MenuItems
public interface Item {
    public String getName();

    public Item setName(String val);

    public float getPrice();

    public Item setPrice(float val);

}
