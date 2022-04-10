package restaurant;

import java.util.Optional;

import javafx.scene.control.ScrollPane;

public class Cart extends ScrollPane {
    private final CartItems cartItems = new CartItems();

    public Cart() {
        super();
        build();
    }

    private void build() {
        setContent(cartItems);
    }

    public void update(double maxWidth, double maxHeight) {
        cartItems.update(maxWidth, maxHeight);
    }

    public void addToCart(Item item) {
        cartItems.addToCart(item);
    }

    public Optional<CartItem> removeFromCart(Item item) {
        return cartItems.removeFromCart(item);
    }
}
