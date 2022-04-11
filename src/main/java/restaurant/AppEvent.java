package restaurant;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class AppEvent extends Event {

    public AppEvent(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
        super(arg0, arg1, arg2);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 69420;
    public static final EventType<AppEvent> ANY = new EventType<>("ANY");
    public static final EventType<AppEvent> GO_BACK = new EventType<>("GO_BACK");

}
