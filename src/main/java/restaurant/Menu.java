package restaurant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class Menu implements Initializable {

    @FXML
    TextField searchField;

    @FXML
    VBox menu;
    static ObservableList<Node> menuItems;

    @FXML
    VBox cart;
    static ObservableList<Node> cartItems;

    @FXML
    ScrollPane menuContainer;

    @FXML
    private void switchToCheckOut() throws IOException {
        App.setRoot("checkout");
    }

    // called when a .fxml file with this class as a controller is loaded
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Menu init url: " + arg0);
        System.out.println("found: " +
                App.class.getResource("pasta.jpeg").toString());
        cartItems = cart.getChildren();
        menuItems = menu.getChildren();
        menuItems.addAll(new MenuItem().setName("pasta")
                .setImage(new Image(App.class.getResource("pasta.jpeg").toString(),
                        menuContainer.getPrefWidth(),
                        menuContainer.getPrefHeight(), true, true))
                .setPrice(20.0f).build());
    }

    @FXML
    void goBack() {
        App.goBack();
    }

    // called when user presses enter in the search bar
    @FXML
    void search() {
        String input = searchField.getText();
        System.out.println("Got: " + input);
    }
}
