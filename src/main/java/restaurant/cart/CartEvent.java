package restaurant.cart;

import javafx.event.Event;
import javafx.event.EventType;
import restaurant.AppEvent;

public class CartEvent extends AppEvent {

    public CartEvent(EventType<? extends Event> arg0) {
        super(arg0);
    }

    private static final long serialVersionUID = 66876389;

    public static final EventType<CartEvent> ADD_TO_CART = new EventType<>("ADD_TO_CART");
    public static final EventType<CartEvent> REMOVE_FROM_CART = new EventType<>("REMOVE_FROM_CART");
}