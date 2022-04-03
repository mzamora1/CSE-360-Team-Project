package restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static final Stack<Parent> prevParents = new Stack<>();
    public static User user;
    private static Stage stage;

    @Override
    public void start(Stage istage) throws IOException {
        stage = istage;
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    static void setRoot(String fxml) throws IOException {
        prevParents.push(scene.getRoot());
        scene.setRoot(loadFXML(fxml));
    }

    static void goBack() {
        if (!prevParents.empty())
            scene.setRoot(prevParents.pop());
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}