package restaurant.menu;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import restaurant.App;
import restaurant.Item;

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

    // both of these methods go around MenuItems
    // instead they directly modify its underlying Observable list
    public boolean remove(Object item) {
        if (menuItems.getChildren().remove(item)) {
            return App.menuItems.remove(item);
        }
        return false;
    }

    // allows non menu items to be added to the menu
    public void add(Item item) {
        menuItems.getChildren().add((Node) item);
        App.menuItems.add((Node) item);
    }

    public void addUnsaved(Item item) {
        menuItems.getChildren().add((Node) item);
    }

    public void reset() {
        menuItems.getChildren().setAll(App.menuItems);
    }

    public void clear() {
        menuItems.getChildren().clear();
    }

}
