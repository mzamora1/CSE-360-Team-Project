package restaurant.menu;

import java.util.List;

import javafx.collections.ObservableList;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import restaurant.App;
import restaurant.AppEvent;

public class MenuItems extends VBox {
    private final ObservableList<Node> menuItems;

    public MenuItems() {
        super(30);
        menuItems = getChildren();
        build();
    }

    private void build() {
        setAlignment(Pos.CENTER);
        if (App.menuItems.isEmpty()) {
            // create a new menu
            App.menuItems.addAll(List.of(
                    new MenuItem()
                            .setName("Spaghetti MeatBalls")
                            .setDescription(
                                    "Our signature Spaghetti dish served with meatballs and topped with garlic, black pepper, parmesan, and olive oil.")
                            .setImage("spaghetti.png")
                            .setPrice(12.75f)
                            .setIngredients(new String[] {
                                    "Spaghetti", "Marinara", "White breadcrumbs", "Lean ground beef",
                                    "Black pepper", "Garlic", "Parmesan cheese", "Olive oil" }),
                    new MenuItem()
                            .setName("Fettuccine Alfredo")
                            .setDescription(
                                    "A classic Italian dish, Fettuccine pasta sauteed cream sauce and topped with parmesan.")
                            .setImage("fettuccine.png")
                            .setPrice(10.50f)
                            .setIngredients(new String[] {
                                    "Fettuccine pasta", "Butter", "Garlic", "Black pepper",
                                    "Parsley", "Parmesan" }),
                    new MenuItem()
                            .setName("Pasta al'Ortolana")
                            .setDescription("Fusilli pasta served with a variety of vegetables.")
                            .setImage("ortolana.png")
                            .setPrice(11)
                            .setIngredients(new String[] {
                                    "Fusili pasta", "Carrots", "Onions", "Garlic", "Zucchini",
                                    "Black pepper", "Olive oil", "Celery" }),
                    new MenuItem()
                            .setName("Fried Mozzarella")
                            .setDescription("Hand cut and breaded fried mozzarella served with marinara dipping sauce.")
                            .setImage("mozzarella.png")
                            .setPrice(8.5f)
                            .setIngredients(new String[] {
                                    "Mozzarella", "Flour", "Breadcrumbs", "Garlic",
                                    "Oregano", "Black pepper", "Parsley", "Side of mariana" }),
                    new MenuItem()
                            .setName("Balsamic Bruschetta (Trio)")
                            .setDescription("Fresh tomatoes and basil on a toasted baguette")
                            .setImage("bruschetta.png")
                            .setPrice(9.5f)
                            .setIngredients(new String[] {
                                    "French bread", "Extra virgin olive oil", "Tomatoes", "Fresh basil",
                                    "Parmesan", "Garlic", "Black pepper", "Balsamic vinegar" }),
                    new MenuItem()
                            .setName("Ceasar Salad")
                            .setDescription("Romaine tossed with Caesar dressing and topped with croutons")
                            .setImage("ceasar.png")
                            .setPrice(7.75f)
                            .setIngredients(new String[] {
                                    "Garlic Croutons", "Parmesan", "Ceasar dressing", "Romaine lettuce" }),
                    new MenuItem()
                            .setName("Mediterranean Salad")
                            .setDescription("Assorted greens and veggies, topped with balsamic vinaigrette")
                            .setImage("mediterranean.png")
                            .setPrice(8.75f)
                            .setIngredients(new String[] {
                                    "Arugula", "Roasted red peppers", "Feta cheese", "Cucumber",
                                    "Red Onion" })));
        }
        // use already created menu
        menuItems.setAll(App.menuItems);
        for (var item : menuItems) {
            App.safeCast(MenuItem.class, item).ifPresent(MenuItem::build);
        }
    }

    public void update(double maxWidth, double maxHeight) {
        setPrefWidth(maxWidth);
        // setPrefHeight(maxHeight);
        menuItems.setAll(App.menuItems);
        for (var item : menuItems) {
            App.safeCast(MenuItem.class, item).ifPresent(menuItem -> menuItem.update(maxWidth));
        }
    }

    private boolean hasAdminAbilities() {
        return App.safeCast(MenuItem.class, menuItems.get(menuItems.size() - 1)).isEmpty();
    }

    public boolean addAdminAbilities() {
        if (hasAdminAbilities())
            return false;

        for (var item : menuItems) {
            App.safeCast(MenuItem.class, item).ifPresent(MenuItem::addAdminAbilities);
        }
        Button startNewItemBtn = new Button("New Item");
        startNewItemBtn.setId("startMenuItemBtn");
        startNewItemBtn.setOnAction(AppEvent.firer(new MenuEvent(MenuEvent.START_NEW_ITEM), startNewItemBtn));
        getChildren().add(startNewItemBtn);
        return true;
    }

    public void removeAdminAbilities() {
        menuItems.removeIf(raw -> {
            var item = App.safeCast(MenuItem.class, raw);
            if (item.isPresent()) {
                item.get().removeAdminAbilities();
                return false;
            }
            return true;
        });
    }

}
