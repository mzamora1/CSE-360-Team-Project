package restaurant.menu;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import restaurant.AppEvent;

public class MenuEvent extends AppEvent {

    public MenuEvent(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
        super(arg0, arg1, arg2);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 69;
    // public static final EventType<MenuEvent> ANY = new EventType<>("ANY");
    public static final EventType<MenuEvent> ADD_TO_CART = new EventType<>("ADD_TO_CART");
    public static final EventType<MenuEvent> REMOVE_FROM_CART = new EventType<>("REMOVE_FROM_CART");
    public static final EventType<MenuEvent> START_NEW_ITEM = new EventType<>("START_NEW_ITEM");
    public static final EventType<MenuEvent> REMOVE_FROM_MENU = new EventType<>("REMOVE_FROM_MENU");
}
