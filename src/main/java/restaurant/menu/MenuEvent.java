package restaurant.menu;

import javafx.event.Event;
import javafx.event.EventType;
import restaurant.AppEvent;

public class MenuEvent extends AppEvent {

    public MenuEvent(EventType<? extends Event> type) {
        super(type);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 69;
    public static final EventType<MenuEvent> START_NEW_ITEM = new EventType<>("START_NEW_ITEM");
    public static final EventType<MenuEvent> REMOVE_FROM_MENU = new EventType<>("REMOVE_FROM_MENU");
}
