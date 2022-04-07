package restaurant;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Menu implements Initializable, Updatable {

    @FXML
    private TextField searchField;

    @FXML
    private VBox menu;
    ObservableList<Node> menuItems;

    @FXML
    private VBox cart;
    ObservableList<Node> cartItems;

    @FXML
    ScrollPane menuContainer;

    @FXML
    ScrollPane cartContainer;

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
    }

    private void updateCart() {
        cartItems = cart.getChildren();
        cartItems.setAll(App.cartItems);
        cartItems.forEach(item -> {
            ((CartItem) item).update(cartContainer.getPrefWidth());
        });
    }

    private void updateMenu() {
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
        // non menu items could reference garbage at this point so remove them
        // otherwise menuItems might need to be updated
        menuItems.removeIf(item -> {
            var menuItem = App.safeCast(MenuItem.class, item);
            if (menuItem.isEmpty()) {
                return true;
            }
            menuItem.get().update();
            return false;
        });
    }

    private void buildMenu() {
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(30);
    }

    // will be called during initialize
    private void build() {
        System.out.println("building menu");
        buildCart();
        buildMenu();
    }

    // also called during initialize but also after goBack
    @Override
    public void update() {
        System.out.println("updating menu");
        updateCart();
        updateMenu();
        if (App.user.getAdmin()) {
            addAdminAbilities();
        } else {
            removeAdminAbilities();
        }
    }

    private static File choosenFile;

    private void addAdminAbilities() {
        Button addMenuItemBtn = new Button("New Item");
        // addMenuItemBtn.setUserData("newItemBtn");
        addMenuItemBtn.setOnMouseClicked(this::onStartNewMenuItem);
        menuItems.add(addMenuItemBtn);
        // scroll to bottom when item is added to menu
        menu.heightProperty().addListener((observable, old, newVal) -> {
            if ((double) newVal > (double) old) {
                menuContainer.setVvalue((double) old);
            }
        });
    }

    private static void onOpenImageChooser(MouseEvent event) {
        var chooser = new FileChooser();
        List<String> validExts = new ArrayList<>(List.of("*.bmp", "*.gif", "*.jpeg", "*.png"));
        validExts.addAll(validExts.stream().map(String::toUpperCase)
                .collect(Collectors.toList()));

        chooser.getExtensionFilters().add(
                new ExtensionFilter("Image files(bmp, gif, jpeg, png)", validExts));

        choosenFile = chooser.showOpenDialog(App.getStage());
        System.out.println(choosenFile.getAbsolutePath());
    }

    private void onStartNewMenuItem(MouseEvent event) {
        var addMenuItemBtn = event.getSource();

        VBox itemInput = new VBox(5);
        itemInput.setAlignment(Pos.CENTER);
        // itemInput.setUserData("itemInput");

        TextField nameInput = new TextField();
        nameInput.setPromptText("name of item...");
        Button openFileChooserBtn = new Button("Choose an image");
        TextField descriptInput = new TextField();
        descriptInput.setPromptText("description of item...");
        TextField ingredInput = new TextField();
        ingredInput.setPromptText("comma seperated ingredients...");
        TextField priceInput = new TextField();
        priceInput.setPromptText("price of item...");
        Button createMenuItemBtn = new Button("Create Item");
        createMenuItemBtn.setOnMouseClicked(e -> {
            if (choosenFile == null)
                return;
            menuItems.remove(itemInput);
            menuItems.add(new MenuItem().setName(nameInput.getText())
                    .setDescription(descriptInput.getText())
                    .setImage(choosenFile)
                    .setIngredients(ingredInput.getText())
                    .setPrice(Float.parseFloat(priceInput.getText())));
            menuItems.add(itemInput);
        });

        openFileChooserBtn.setOnMouseClicked(Menu::onOpenImageChooser);

        menuItems.remove(addMenuItemBtn);
        itemInput.getChildren().addAll(
                nameInput,
                openFileChooserBtn,
                descriptInput,
                ingredInput,
                priceInput,
                createMenuItemBtn);
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
        var coupon = new CartItem(cartContainer.getPrefWidth(), "coupon", -5f, 1);
        if (couponCheck.isSelected() && totalPrice() > -coupon.price()) {
            cartItems.add(coupon);
            updateTotalPrice(coupon.price());
        } else {
            couponCheck.setSelected(false);
            cartItems.removeIf(item -> {
                CartItem cartItem = (CartItem) item;
                if (cartItem.name() == coupon.name()) {
                    updateTotalPrice(-coupon.price());
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

    private void saveCart() {
        App.cartItems.clear();
        App.cartItems.addAll(cartItems);
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

    public void updateTotalPrice(float changeAmount) {
        totalPrice(totalPrice() + changeAmount);
    }

    public void totalPrice(float iprice) {
        priceTotal.setText("Total Price: $" + iprice);
    }

    public float totalPrice() {
        String totalText = priceTotal.getText();
        return Float.parseFloat(totalText.substring(totalText.indexOf('$') + 1));
    }
}
