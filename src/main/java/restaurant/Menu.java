package restaurant;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class Menu extends ScrollPane {
    private final MenuItems menuItems = new MenuItems();

    public Menu() {
        super();
        build();
    }

    // construct and layout nodes on screen
    private void build() {
        setContent(menuItems);
    }

    // update size of nodes
    public void update(double maxWidth, double maxHeight) {
        // setPrefWidth(maxWidth);
        // setPrefHeight(maxHeight);
        menuItems.update(maxWidth, maxHeight);
    }

    // add elements to the screen that only admins can use
    public boolean addAdminAbilities() {
        var result = menuItems.addAdminAbilities();
        // scroll to bottom when item is added to menu
        menuItems.heightProperty().addListener((observable, old, newVal) -> {
            if ((double) newVal > (double) old) {
                setVvalue((double) old);
            }

        });
        return result;
    }

    public void removeAdminAbilities() {
        menuItems.removeAdminAbilities();
    }

    public void setOnAddToCart(EventHandler<ActionEvent> val) {
        menuItems.setOnAddToCart(val);
    }

    public void setOnRemoveFromCart(EventHandler<ActionEvent> val) {
        menuItems.setOnRemoveFromCart(val);
    }

    public void setOnRemoveFromMenu(EventHandler<ActionEvent> val) {
        menuItems.setOnRemoveFromMenu(val);
    }

    public void setOnStartNewMenuItem(EventHandler<ActionEvent> val) {
        menuItems.setOnStartNewMenuItem(val);
    }

    // both of these methods go around MenuItems
    // instead they directly modify its underlying Observable list
    public boolean remove(Object item) {
        return menuItems.getChildren().remove(item);
    }

    // allows non menu items to be added to the menu
    public void add(Node item) {
        menuItems.getChildren().add(item);
    }

}
