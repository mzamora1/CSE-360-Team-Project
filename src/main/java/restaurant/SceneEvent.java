package restaurant;

import javafx.event.Event;
import javafx.event.EventType;

public class SceneEvent extends AppEvent {
    public final Controller controller;

    public SceneEvent(EventType<? extends Event> type, Controller newController) {
        super(type);
        controller = newController;
    }

    private static final long serialVersionUID = 16849861;
    public static final EventType<SceneEvent> CHANGE_SCENE = new EventType<>("CHANGE_SCENE");
}
