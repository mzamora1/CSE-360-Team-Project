package restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * JavaFX App
 */
public class App extends Application {
    public static void main(String[] args) {
        launch();
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
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    public static void goBack() {
        if (fxmlLoaders.size() <= 1)
            return;
        fxmlLoaders.pop();
        scene.setRoot(getLoader().getRoot());
    }

    static FXMLLoader getLoader() {
        return fxmlLoaders.lastElement();
    }

    public static Object getController() {
        return getLoader().getController();
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