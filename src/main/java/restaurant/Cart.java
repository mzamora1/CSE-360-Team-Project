package restaurant;

import java.util.Optional;

import javafx.scene.control.ScrollPane;

public class Cart extends ScrollPane {
    private final CartItems cartItems;

    public Cart() {
        super();
        cartItems = new CartItems();
        build();
    }

    private void build() {
        setContent(cartItems);
    }

    public void update(double maxWidth, double maxHeight) {
        // setPrefWidth(maxWidth);
        // setPrefHeight(maxHeight);
        cartItems.update(maxWidth, maxHeight);
    }

    public void addToCart(Item item) {
        cartItems.addToCart(item);
    }

    public Optional<CartItem> removeFromCart(Item item) {
        return cartItems.removeFromCart(item);
    }
}
