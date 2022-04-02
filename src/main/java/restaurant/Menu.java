package restaurant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Menu implements Initializable {

    @FXML
    TextField searchField;

    @FXML
    VBox menu;

    @FXML
    VBox cart;

    @FXML
    ScrollPane menuContainer;

    @FXML
    private void switchToCheckOut() throws IOException {
        App.setRoot("checkOut");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Menu init url: " + arg0);
        System.out.println("found: " +
                App.class.getResource("pasta.jpeg").toString());

        EventHandler<? super MouseEvent> onClick = event -> {
            event.consume();
            var clicked = (MenuItem) event.getSource();
            System.out.println(clicked);
            var item = new CartItem(
                    clicked.name, clicked.price, 1);
            item.setPrefWidth(cart.getWidth());
            cart.getChildren().add(item);
        };
        MenuItem[] menuItems = {
                new MenuItem(onClick).setName("pasta")
                        .setImage(new Image(App.class.getResource("pasta.jpeg").toString(),
                                menuContainer.getPrefWidth(),
                                menuContainer.getPrefHeight(), true, true))
                        .setPrice(20.0f).build() };
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
