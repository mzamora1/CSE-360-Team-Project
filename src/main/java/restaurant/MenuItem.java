package restaurant;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MenuItem extends VBox {

    public enum Type {
        Entree("Entree"), Side("Side"), Drink("Drink");

        private String value;

        private Type(String val) {
            value = val;
        }

        public String toString() {
            return value;
        }
    }

    private final Label nameLabel = new Label();
    private final Label descriptionLabel = new Label();
    private Image image;
    private final Label priceLabel = new Label("Price: $");
    private final Label ingredientsLabel = new Label("Ingredients: ");
    private final Button addToCartBtn = new Button("Add To Cart");
    private final Button rmvFromCartBtn = new Button("Remove From Cart");
    private final HBox buttonContainer = new HBox(10, addToCartBtn, rmvFromCartBtn);
    private Type type;
    private int prepareTime;

    public MenuItem() {
        super(10);
    }

    public MenuItem(String iname, String descrip, String path, float iprice, Type itype,
            String[] ingred, int ptime) {
        super(10);
        setName(iname);
        setDescription(descrip);
        setImage(path);
        setPrice(iprice);
        setType(itype);
        setIngredients(ingred);
        setPrepareTime(ptime);
        build();
    }

    public MenuItem build() {
        setAlignment(Pos.CENTER);
        buttonContainer.setAlignment(Pos.CENTER);
        // setUserData(null);

        addToCartBtn.setOnMouseClicked(event -> {
            event.consume();
            // must be inside handler or else could be dangling
            Menu menu = App.getControllerUnsafe();
            for (var item : menu.cartItems) {
                CartItem cartItem = (CartItem) item;
                if (cartItem.name() == getName()) {
                    cartItem.updateQuantity(1);
                    menu.updateTotalPrice(cartItem.price());
                    return;
                }
            }
            menu.cartItems.add(new CartItem(menu.cartContainer.getPrefWidth(), getName(), getPrice(), 1));
            menu.updateTotalPrice(getPrice());
        });

        rmvFromCartBtn.setOnMouseClicked(event -> {
            event.consume();
            Menu menu = App.getControllerUnsafe();
            for (var item : menu.cartItems) {
                CartItem cartItem = (CartItem) item;
                if (cartItem.name() != getName())
                    continue;

                if (cartItem.quantity() > 1) {
                    cartItem.updateQuantity(-1);
                    menu.updateTotalPrice(-cartItem.price());
                    return;
                } else if (cartItem.quantity() <= 1) {
                    menu.cartItems.remove(cartItem);
                    menu.updateTotalPrice(-cartItem.price());
                    return;
                }
            }

        });

        if (App.user.getAdmin()) {
            addAdminAbilities();
        }
        Menu menu = App.getControllerUnsafe();
        // var maxWidth = Menu.menuPrefWidth;

        var maxWidth = menu.menuContainer.getPrefWidth();
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(maxWidth * .75);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setAlignment(Pos.CENTER);

        ingredientsLabel.setWrapText(true);
        ingredientsLabel.setMaxWidth(maxWidth * .75);
        ingredientsLabel.setTextAlignment(TextAlignment.CENTER);
        ingredientsLabel.setAlignment(Pos.CENTER);

        getChildren().setAll(
                nameLabel,
                new Group(descriptionLabel),
                new ImageView(image),
                priceLabel,
                new Group(ingredientsLabel),
                buttonContainer);
        return this;
    }

    private static final int ADMIN_LENGTH = 3;

    private boolean hasAdminAbilities() {
        return buttonContainer.getChildren().size() == ADMIN_LENGTH;
    }

    private boolean addAdminAbilities() {
        if (hasAdminAbilities())
            return false;
        Button rmvFromMenuBtn = new Button("Remove From Menu");
        rmvFromMenuBtn.setOnMouseClicked(event -> {
            event.consume();
            Menu menu = App.getControllerUnsafe();
            menu.menuItems.remove(this);
        });
        buttonContainer.getChildren().add(rmvFromMenuBtn);
        return true;
    }

    public boolean removeAdminAbilities() {
        if (!hasAdminAbilities())
            return false;
        buttonContainer.getChildren().remove(ADMIN_LENGTH - 1);
        return true;
    }

    public String getName() {
        return nameLabel.getText();
    }

    private float getPrice() {
        var text = priceLabel.getText();
        return Float.parseFloat(text.substring(text.indexOf('$') + 1));
    }

    public MenuItem setName(String val) {
        nameLabel.setText(val);
        return this;
    }

    public MenuItem setDescription(String val) {
        descriptionLabel.setText(val);
        return this;
    }

    public MenuItem setImage(Image img) {
        image = img;
        return this;
    }

    public MenuItem setImage(File file) {
        var width = ((Menu) App.getController()).menuContainer.getPrefWidth();
        image = new Image(file.toURI().toString(), width,
                width, true, true);
        return this;
    }

    public MenuItem setImage(String path) {
        var width = ((Menu) App.getController()).menuContainer.getPrefWidth();
        image = new Image(App.class.getResourceAsStream(path), width,
                width, true, true);
        return this;
    }

    public MenuItem setPrice(float val) {
        priceLabel.setText("Price: $" + Float.toString(val));
        return this;
    }

    public MenuItem setType(Type val) {
        type = val;
        return this;
    }

    public MenuItem setIngredients(String[] val) {
        ingredientsLabel.setText("Ingredients: " + Arrays.stream(val).collect(Collectors.joining(", ")));
        return this;
    }

    public MenuItem setIngredients(String val) {
        ingredientsLabel.setText(val);
        return this;
    }

    public MenuItem setPrepareTime(int val) {
        prepareTime = val;
        return this;
    }
}
