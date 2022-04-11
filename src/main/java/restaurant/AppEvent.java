package restaurant;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class AppEvent extends Event {

    public AppEvent(EventType<? extends Event> type) {
        super(type);
    }

    public static <T extends Event, E extends AppEvent> EventHandler<T> firer(E event, EventTarget target) {
        return e -> {
            Event.fireEvent(target, event);
            if (event.isConsumed())
                e.consume();
        };
    }

    private static final long serialVersionUID = 69420;
    public static final EventType<AppEvent> ANY_APP_EVENT = new EventType<>("ANY_APP_EVENT");
    public static final EventType<AppEvent> BEFORE_GO_BACK = new EventType<>("BEFORE_GO_BACK");

}
