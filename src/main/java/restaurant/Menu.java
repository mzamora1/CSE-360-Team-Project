package restaurant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class Menu implements Initializable {

    @FXML
    TextField searchField;
    // @FXML
    // ListView<String> cart;
    // @FXML
    // ListView<MenuItem> menu;

    @FXML
    VBox menu;

    @FXML
    ScrollPane menuContainer;

    @FXML
    private void switchToCheckOut() throws IOException {
        App.setRoot("checkOut");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Menu init url: " + arg0);
        MenuItem[] menuItems = {
                new MenuItem().setName("pasta")
                        .setImage(new Image("restaurant/pasta.jpeg",
                                menuContainer.getPrefWidth(),
                                menuContainer.getPrefHeight(), true, true))
                        .setPrice(20.0).build() };
        menu.getChildren().addAll(menuItems);
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
