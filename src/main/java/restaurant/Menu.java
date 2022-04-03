package restaurant;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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
    static double menuPrefWidth;

    @FXML
    ScrollPane cartContainer;
    static double cartPrefWidth;

    @FXML
    private void switchToCheckOut() throws IOException {
        App.setRoot("checkout");
    }

    private static File file;

    // called when a .fxml file with this class as a controller is loaded
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("init menu");
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(30);
        cart.setAlignment(Pos.TOP_CENTER);
        cart.setSpacing(5);
        menuPrefWidth = menuContainer.getPrefWidth();
        cartPrefWidth = cartContainer.getPrefWidth();
        if (cartItems != null) {
            System.out.println("using exisiting cart items" + cartItems.size());
            cartItems.forEach(item -> {
                ((CartItem) item).build(cartPrefWidth);
                System.out.println(item);
            });
            cart.getChildren().setAll(cartItems);
            cartItems = cart.getChildren();
        } else {
            cartItems = cart.getChildren();
        }

        if (menuItems != null) {
            // use already created menu
            menu.getChildren().setAll(menuItems);
            menuItems = menu.getChildren();
        } else {
            // create a new menu
            menuItems = menu.getChildren();
            menuItems.setAll(
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
                            .build()

            );
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
                file = new FileChooser().showOpenDialog(App.getStage());
            });

            createMenuItemBtn.setOnMouseClicked(event -> {
                if (file == null)
                    return;
                menuItems.remove(itemInput);
                menuItems.add(new MenuItem().setName(nameInput.getText())
                        .setDescription(descriptInput.getText())
                        .setImage(file.toURI())
                        .setIngredients(ingredInput.getText())
                        .setPrice(Float.parseFloat(priceInput.getText()))
                        .build());
                menuItems.add(itemInput);
            });

            addMenuItemBtn.setOnMouseClicked(event -> {
                // addMenuItemBtn.setVisible(false);
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
            menuItems.add(addMenuItemBtn);
        } else {
            // remove admin abilities from menu
            var last = menuItems.get(menuItems.size() - 1);
            var lastData = (String) last.getUserData();
            if (lastData == "newItemBtn" || lastData == "itemInput") {
                menuItems.remove(last);
            }
            menuItems.forEach(item -> {
                if (MenuItem.class.isInstance(item))
                    ((MenuItem) item).removeRemoveBtn();
            });
        }
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
