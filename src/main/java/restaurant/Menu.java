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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Menu implements Initializable {

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

    private static File choosenFile;

    // called when a .fxml file with this class as a controller is loaded
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("init menu");
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(30);
        cart.setAlignment(Pos.TOP_CENTER);
        cart.setSpacing(5);
        // menuPrefWidth = menuContainer.getPrefWidth();
        // cartPrefWidth = cartContainer.getPrefWidth();

        App.cartItems.forEach(item -> {
            ((CartItem) item).build(cartContainer.getPrefWidth());
        });
        cart.getChildren().setAll(App.cartItems);
        cartItems = cart.getChildren();

        if (!App.menuItems.isEmpty()) {
            // use already created menu
            menu.getChildren().setAll(App.menuItems);
            menuItems = menu.getChildren();
        } else {
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
                                    "Black pepper", "Garlic", "Parmesan cheese", "Olive oil" })
                            .build(),
                    new MenuItem()
                            .setName("Fettuccine Alfredo")
                            .setDescription(
                                    "A classic Italian dish, Fettuccine pasta sauteed cream sauce and topped with parmesan.")
                            .setImage("fettuccine.png")
                            .setPrice(10.50f)
                            .setIngredients(new String[] {
                                    "Fettuccine pasta", "Butter", "Garlic", "Black pepper",
                                    "Parsley", "Parmesan" })
                            .build(),
                    new MenuItem()
                            .setName("Pasta al'Ortolana")
                            .setDescription("Fusilli pasta served with a variety of vegetables.")
                            .setImage("ortolana.png")
                            .setPrice(11)
                            .setIngredients(new String[] {
                                    "Fusili pasta", "Carrots", "Onions", "Garlic", "Zucchini",
                                    "Black pepper", "Olive oil", "Celery" })
                            .build(),
                    new MenuItem()
                            .setName("Fried Mozzarella")
                            .setDescription("Hand cut and breaded fried mozzarella served with marinara dipping sauce.")
                            .setImage("mozzarella.png")
                            .setPrice(8.5f)
                            .setIngredients(new String[] {
                                    "Mozzarella", "Flour", "Breadcrumbs", "Garlic",
                                    "Oregano", "Black pepper", "Parsley", "Side of mariana" })
                            .build(),
                    new MenuItem()
                            .setName("Balsamic Bruschetta (Trio)")
                            .setDescription("Fresh tomatoes and basil on a toasted baguette")
                            .setImage("bruschetta.png")
                            .setPrice(9.5f)
                            .setIngredients(new String[] {
                                    "French bread", "Extra virgin olive oil", "Tomatoes", "Fresh basil",
                                    "Parmesan", "Garlic", "Black pepper", "Balsamic vinegar" })
                            .build(),
                    new MenuItem()
                            .setName("Ceasar Salad")
                            .setDescription("Romaine tossed with Caesar dressing and topped with croutons")
                            .setImage("ceasar.png")
                            .setPrice(7.75f)
                            .setIngredients(new String[] {
                                    "Garlic Croutons", "Parmesan", "Ceasar dressing", "Romaine lettuce" })
                            .build(),
                    new MenuItem()
                            .setName("Mediterranean Salad")
                            .setDescription("Assorted greens and veggies, topped with balsamic vinaigrette")
                            .setImage("mediterranean.png")
                            .setPrice(8.75f)
                            .setIngredients(new String[] {
                                    "Arugula", "Roasted red peppers", "Feta cheese", "Cucumber",
                                    "Red Onion" })
                            .build()));
            menuItems = menu.getChildren();
            menuItems.setAll(App.menuItems);
        }

        if (App.user.getAdmin()) {
            // add admin abilities to menu

            Button addMenuItemBtn = new Button("New Item");
            addMenuItemBtn.setUserData("newItemBtn");

            VBox itemInput = new VBox(5);
            itemInput.setAlignment(Pos.CENTER);
            itemInput.setUserData("itemInput");

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

            openFileChooserBtn.setOnMouseClicked(event -> {
                var chooser = new FileChooser();
                List<String> validExts = new ArrayList<>(List.of("*.bmp", "*.gif", "*.jpeg", "*.png"));
                validExts.addAll(validExts.stream().map(String::toUpperCase)
                        .collect(Collectors.toList()));

                chooser.getExtensionFilters().add(
                        new ExtensionFilter("Image files(bmp, gif, jpeg, png)", validExts));

                choosenFile = chooser.showOpenDialog(App.getStage());
            });

            createMenuItemBtn.setOnMouseClicked(event -> {
                if (choosenFile == null)
                    return;
                menuItems.remove(itemInput);
                menuItems.add(new MenuItem().setName(nameInput.getText())
                        .setDescription(descriptInput.getText())
                        .setImage(choosenFile.toURI())
                        .setIngredients(ingredInput.getText())
                        .setPrice(Float.parseFloat(priceInput.getText()))
                        .build());
                menuItems.add(itemInput);
            });

            addMenuItemBtn.setOnMouseClicked(event -> {
                menuItems.remove(addMenuItemBtn);
                itemInput.getChildren().addAll(
                        nameInput,
                        openFileChooserBtn,
                        descriptInput,
                        ingredInput,
                        priceInput,
                        createMenuItemBtn);
                menuItems.add(itemInput);
            });

            menuItems.forEach(item -> ((MenuItem) item).build());
            menuItems.add(addMenuItemBtn);
            // scroll to bottom when item is added to menu
            menu.heightProperty().addListener((observable, old, newVal) -> {
                if ((Double) newVal > (Double) old) {
                    menuContainer.setVvalue((Double) old);
                }
            });
        } else {
            // remove admin abilities from menu
            for (int i = 0; i < menuItems.size(); ++i) {
                var item = menuItems.get(i);
                if (MenuItem.class.isInstance(item))
                    ((MenuItem) item).removeRemoveBtn();
                else {
                    menuItems.remove(item);
                    --i;
                }

            }
        }
    }

    @FXML
    private void coupon(ActionEvent c) {
        if (couponCheck.isSelected()) {
            cartItems.add(new CartItem(cartContainer.getPrefWidth(), "coupon", -5f, 1));
            updateTotalPrice(-5f);
        } else {
            for (var item : cartItems) {
                CartItem cartItem = (CartItem) item;
                if (cartItem.name() == "coupon") {
                    cartItems.remove(cartItem);
                    updateTotalPrice(-cartItem.price());
                    return;
                }
            }
        }
    }

    public void save() {
        if (!isFilteredMenu) {
            App.menuItems.clear();
            App.menuItems.addAll(menuItems);
        }
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
        if (!isFilteredMenu) {
            // save menu items
            App.menuItems.clear();
            App.menuItems.addAll(menuItems);
        }

        menuItems.clear();
        for (int i = 0; i < App.menuItems.size(); ++i) {
            var item = (MenuItem) App.menuItems.get(i);
            if (item.name.toLowerCase().contains(input)) {
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
