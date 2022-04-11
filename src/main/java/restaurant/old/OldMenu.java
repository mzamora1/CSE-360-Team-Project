package restaurant.old;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import restaurant.App;
import restaurant.cart.CartItem;
import restaurant.menu.MenuItem;
import restaurant.menu.MenuItemInput;

@Deprecated(since = "MenuController became resizeable")
public class OldMenu implements Initializable, OldUpdatable {
    public OldMenu() {
    }

    @FXML
    private TextField searchField;

    @FXML
    private VBox menu;
    private ObservableList<Node> menuItems;

    @FXML
    private VBox cart;
    private ObservableList<Node> cartItems;

    @FXML
    private ScrollPane menuContainer;

    @FXML
    private ScrollPane cartContainer;

    @FXML
    private Label priceTotal;

    @FXML
    private CheckBox couponCheck;

    @FXML
    private void switchToCheckOut() {
        if (cartItems.isEmpty())
            return;
        save();
        App.setRoot("checkout");
    }

    private void buildCart() {
        cart.setAlignment(Pos.TOP_CENTER);
        cart.setSpacing(5);
        cartItems = cart.getChildren();
    }

    private void updateCart() {
        cartItems.setAll(App.cartItems);
        cartItems.forEach(item -> {
            ((CartItem) item).update(cartContainer.getPrefWidth());
        });
    }

    private void buildMenu() {
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(30);
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
        menuItems = menu.getChildren();
        menuItems.setAll(App.menuItems);
        for (var item : menuItems) {
            App.safeCast(MenuItem.class, item).ifPresent(MenuItem::build);
        }
    }

    private void updateMenu() {
        menuItems.setAll(App.menuItems);
        // non menu items could reference garbage at this point so remove them
        // otherwise menuItems might need to be updated
        // menuItem handlers could also reference garbage so we have to reassign them
        // basically anything referencing 'this' could be referencing garbage here
        menuItems.removeIf(item -> {
            var optMenuItem = App.safeCast(MenuItem.class, item);
            if (optMenuItem.isEmpty()) {
                return true;
            }
            var menuItem = optMenuItem.get();
            // menuItem.setOnAddToCart(this::onAddToCart);
            // menuItem.setOnRemoveFromCart(this::onRemoveFromCart);
            menuItem.update(menuContainer.getPrefWidth());
            return false;
        });
    }

    private <T extends Event> void onAddToCart(T event) {
        event.consume();
        var menuItem = (MenuItem) event.getSource();
        for (var item : cartItems) {
            CartItem cartItem = (CartItem) item;
            if (cartItem.getName().equals(menuItem.getName())) {
                cartItem.changeQuantityBy(1);
                changeTotalPriceBy(cartItem.getPrice());
                return;
            }
        }
        cartItems.add(new CartItem(cartContainer.getPrefWidth(),
                menuItem.getName(), menuItem.getPrice(), 1));
        changeTotalPriceBy(menuItem.getPrice());
    }

    private <T extends Event> void onRemoveFromCart(T event) {
        event.consume();
        var menuItem = (MenuItem) event.getSource();
        cartItems.removeIf(item -> {
            CartItem cartItem = (CartItem) item;
            if (!cartItem.getName().equals(menuItem.getName()))
                return false;
            changeTotalPriceBy(-cartItem.getPrice());
            if (cartItem.getQuantity() > 1) {
                cartItem.changeQuantityBy(-1);
                return false;
            }
            return true;
        });
    }

    private <T extends Event> void onRemoveFromMenu(T event) {
        event.consume();
        menuItems.remove((MenuItem) event.getSource());
    }

    // will be called during initialize
    private void build() {
        System.out.println("building menu");
        buildCart();
        buildMenu();

    }

    // also called during initialize but also after goBack
    // must update anything that could be referencing a previous 'this' value
    // or risk reading from dangling memory
    @Override
    public void update() {
        System.out.println("updating menu");
        updateCart();
        updateMenu();
        if (App.user.isAdmin()) {
            addAdminAbilities();
        } else {
            removeAdminAbilities();
        }
    }

    private boolean hasAdminAbilities() {
        return App.safeCast(MenuItem.class, menuItems.get(menuItems.size() - 1)).isEmpty();
    }

    private boolean addAdminAbilities() {
        if (hasAdminAbilities())
            return false;

        for (var item : menuItems) {
            App.safeCast(MenuItem.class, item)
                    .ifPresent(menuItem -> {
                        menuItem.addAdminAbilities();
                        // menuItem.setOnRemoveFromMenu(this::onRemoveFromMenu);
                    });
        }
        Button addMenuItemBtn = new Button("New Item");
        addMenuItemBtn.setOnMouseClicked(this::onStartNewMenuItem);
        menuItems.add(addMenuItemBtn);
        // scroll to bottom when item is added to menu
        menu.heightProperty().addListener((observable, old, newVal) -> {
            if ((double) newVal > (double) old) {
                menuContainer.setVvalue((double) old);
            }
        });
        return true;
    }

    private static File choosenFile;

    private static <T extends Event> void onOpenImageChooser(T event) {
        var chooser = new FileChooser();
        List<String> validExts = new ArrayList<>(List.of("*.bmp", "*.gif", "*.jpeg", "*.png"));
        validExts.addAll(validExts.stream().map(String::toUpperCase)
                .collect(Collectors.toList()));

        chooser.getExtensionFilters().add(
                new ExtensionFilter("Image files(bmp, gif, jpeg, png)", validExts));

        choosenFile = chooser.showOpenDialog(App.getStage());
    }

    private <T extends Event> void onStartNewMenuItem(T event) {
        var addMenuItemBtn = event.getSource();
        var itemInput = new MenuItemInput();
        itemInput.setOnOpenImageChooser(OldMenu::onOpenImageChooser);
        itemInput.setOnCreateMenuItem(e -> {
            if (choosenFile == null)
                return;
            menuItems.remove(itemInput);
            var newItem = new MenuItem(
                    // this::onAddToCart, this::onRemoveFromCart, this::onRemoveFromMenu,
                    menuContainer.getPrefWidth())
                    .setName(itemInput.getName())
                    .setDescription(itemInput.getDescription())
                    .setImage(choosenFile)
                    .setIngredients(itemInput.getIngredients())
                    .setPrice(itemInput.getPrice());
            menuItems.add(newItem);
            menuItems.add(itemInput);
        });
        menuItems.remove(addMenuItemBtn);
        menuItems.add(itemInput);
    }

    private void removeAdminAbilities() {
        menuItems.removeIf(raw -> {
            var item = App.safeCast(MenuItem.class, raw);
            if (item.isPresent()) {
                item.get().removeAdminAbilities();
                return false;
            }
            return true;
        });
    }

    // called when a .fxml file with this class as a controller is loaded
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        build();
        update();
    }

    @FXML
    private void coupon(ActionEvent c) {
        c.consume();
        float couponPrice = -5f;
        var coupon = new CartItem(cartContainer.getPrefWidth(), "coupon", couponPrice, 1);
        if (couponCheck.isSelected() && totalPrice() > -couponPrice) {
            cartItems.add(coupon);
            changeTotalPriceBy(couponPrice);
        } else {
            couponCheck.setSelected(false);
            cartItems.removeIf(item -> {
                CartItem cartItem = (CartItem) item;
                if (cartItem.getName() == coupon.getName()) {
                    changeTotalPriceBy(-couponPrice);
                    return true;
                }
                return false;
            });
        }
    }

    private void save() {
        saveMenu();
        saveCart();
    }

    private boolean saveMenu() {
        if (!isFilteredMenu) {
            App.menuItems.clear();
            App.menuItems.addAll(menuItems);
            return true;
        }
        return false;
    }

    private boolean saveCart() {
        if (App.user.isGuest())
            return false;
        App.cartItems.clear();
        App.cartItems.addAll(cartItems);
        return true;
    }

    @FXML
    private void goBack() {
        save();
        App.goBack();
    }

    private boolean isFilteredMenu = false;

    // called when user presses enter in the search bar
    @FXML
    private void search() {
        String input = searchField.getText().trim().toLowerCase();
        System.out.println("Got: " + input);
        if (input.isEmpty() && isFilteredMenu) {
            menuItems.setAll(App.menuItems);
            isFilteredMenu = false;
            return;
        }

        saveMenu();
        menuItems.clear();
        for (var item : App.menuItems) {
            var menuItem = (MenuItem) item;
            if (menuItem.getName().toLowerCase().contains(input)) {
                menuItems.add(item);
            }
        }
        isFilteredMenu = true;
    }

    private void changeTotalPriceBy(float changeAmount) {
        totalPrice(totalPrice() + changeAmount);
    }

    private void totalPrice(float iprice) {
        priceTotal.setText("Total Price: $" + iprice);
    }

    private float totalPrice() {
        String totalText = priceTotal.getText();
        return Float.parseFloat(totalText.substring(totalText.indexOf('$') + 1));
    }
}
