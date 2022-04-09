package restaurant;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public App() {
        super();
    }

    private static Stage stage;
    private static Scene scene;
    private static final Stack<FXMLLoader> fxmlLoaders = new Stack<>();

    public static User user;
    public static final List<Node> menuItems = new ArrayList<>();
    public static final List<Node> cartItems = new ArrayList<>();

    @Override
    public void start(Stage istage) {
        stage = istage;
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("alfredologo.PNG")));
        stage.setTitle("Alfredo Restaurant");
        // this listener allows the app to be responsive
        stage.widthProperty().addListener(onReisze);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    private static Parent controller;
    private static ChangeListener<Number> onReisze = (obs, old, newVal) -> {
        if (controller instanceof Updatable) {
            ((Updatable) controller).update(scene.getWidth(), scene.getHeight());
        }
    };

    public static void setRoot(String fxml) {
        // TODO make other controllers updatable
        if (fxml == "menu") {
            controller = new MenuController(scene.getWidth(), scene.getHeight());
            scene.setRoot(controller);
        } else
            scene.setRoot(loadFXML(fxml));
    }

    public static void goBack() {
        if (fxmlLoaders.size() <= 1)
            return;
        fxmlLoaders.pop();
        scene.setRoot(getLoader().getRoot());
        App.getController(OldUpdatable.class).ifPresent(OldUpdatable::update);
    }

    private static FXMLLoader getLoader() {
        return fxmlLoaders.lastElement();
    }

    public static <To, From> Optional<To> safeCast(From obj, Class<To> clazz) {
        if (clazz.isInstance(obj))
            return Optional.of(clazz.cast(obj));
        return Optional.empty();
    }

    // not needed but makes it impossible to call the function wrong
    public static <To, From> Optional<To> safeCast(Class<To> clazz, From obj) {
        return safeCast(obj, clazz);
    }

    private static Object getController() {
        return getLoader().getController();
    }

    public static <Controller> Optional<Controller> getController(Class<Controller> clazz) {
        return safeCast(getController(), clazz);
    }

    // unsafe because no checks are done before the cast
    public static <Controller> Controller getControllerUnsafe(Class<Controller> clazz) {
        return clazz.cast(getController());
    }

    private static Parent loadFXML(String fxml) {
        var loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoaders.push(loader);
        try {
            return loader.load();
        } catch (Exception e) {
            fxmlLoaders.pop();
            e.printStackTrace();
            return new Label("Error loading fxml file: " + fxml + ".fxml");
        }
    }
}