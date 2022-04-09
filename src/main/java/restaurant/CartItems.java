package restaurant;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CartItems extends VBox {
    private final ObservableList<Node> cartItems;

    public CartItems() {
        super(5);
        cartItems = getChildren();
        build();
    }

    private void build() {
        setAlignment(Pos.TOP_CENTER);
    }

    public void update(double maxWidth, double maxHeight) {
        setPrefWidth(maxWidth);
        setPrefHeight(maxHeight);
        cartItems.setAll(App.cartItems);
        cartItems.forEach(item -> {
            ((CartItem) item).update(maxWidth);
        });
    }

    public CartItem addToCart(Item inputItem) {
        for (var item : cartItems) {
            CartItem cartItem = (CartItem) item;
            if (cartItem.getName().equals(inputItem.getName())) {
                cartItem.changeQuantityBy(1);
                return cartItem;
            }
        }
        var newCartItem = new CartItem(getWidth(),
                inputItem.getName(), inputItem.getPrice(), 1);
        cartItems.add(newCartItem);
        return newCartItem;
    }

    public Optional<CartItem> removeFromCart(Item inputItem) {
        int i = 0;
        for (var item : cartItems) {
            CartItem cartItem = (CartItem) item;
            if (cartItem.getName().equals(inputItem.getName())) {
                if (cartItem.getQuantity() >= 1)
                    cartItem.changeQuantityBy(-1);
                else {
                    cartItem = (CartItem) cartItems.remove(i);
                }
                return Optional.of(cartItem);
            }
            ++i;
        }
        return Optional.empty();
    }
}
