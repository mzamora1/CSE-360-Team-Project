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
import javafx.scene.input.MouseEvent;
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
    private final ImageView imageView = new ImageView();
    private final Label priceLabel = new Label("Price: $");
    private final Label ingredientsLabel = new Label("Ingredients: ");
    private final Button addToCartBtn = new Button("Add To Cart");
    private final Button rmvFromCartBtn = new Button("Remove From Cart");
    private final HBox buttonContainer = new HBox(10, addToCartBtn, rmvFromCartBtn);
    private Type type;
    private int prepareTime;

    public MenuItem() {
        super(10);
        build();
        update();
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
        update();
    }

    private MenuItem build() {
        setAlignment(Pos.CENTER);
        buttonContainer.setAlignment(Pos.CENTER);

        addToCartBtn.setOnMouseClicked(this::onAddToCart);
        rmvFromCartBtn.setOnMouseClicked(this::onRemoveFromCart);

        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setAlignment(Pos.CENTER);
        ingredientsLabel.setWrapText(true);
        ingredientsLabel.setTextAlignment(TextAlignment.CENTER);
        ingredientsLabel.setAlignment(Pos.CENTER);

        getChildren().setAll(
                nameLabel,
                new Group(descriptionLabel),
                imageView,
                priceLabel,
                new Group(ingredientsLabel),
                buttonContainer);

        return this;
    }

    public void update() {
        Menu menu = App.getControllerUnsafe();
        var maxWidth = menu.menuContainer.getPrefWidth();

        descriptionLabel.setMaxWidth(maxWidth * .75);
        ingredientsLabel.setMaxWidth(maxWidth * .75);
        if (App.user.getAdmin()) {
            addAdminAbilities();
        } else
            removeAdminAbilities();
    }

    private static final int ADMIN_LENGTH = 3;

    private boolean hasAdminAbilities() {
        return buttonContainer.getChildren().size() == ADMIN_LENGTH;
    }

    private boolean addAdminAbilities() {
        if (hasAdminAbilities())
            return false;
        Button rmvFromMenuBtn = new Button("Remove From Menu");
        rmvFromMenuBtn.setOnMouseClicked(this::onRemoveFromMenu);
        buttonContainer.getChildren().add(rmvFromMenuBtn);
        return true;
    }

    public boolean removeAdminAbilities() {
        if (!hasAdminAbilities())
            return false;
        buttonContainer.getChildren().remove(ADMIN_LENGTH - 1);
        return true;
    }

    private void onAddToCart(MouseEvent event) {
        event.consume();
        Menu menu = App.getControllerUnsafe();
        for (var item : menu.cartItems) {
            CartItem cartItem = (CartItem) item;
            if (cartItem.name().equals(getName())) {
                cartItem.updateQuantity(1);
                menu.updateTotalPrice(cartItem.price());
                return;
            }
        }
        menu.cartItems.add(new CartItem(menu.cartContainer.getPrefWidth(), getName(), getPrice(), 1));
        menu.updateTotalPrice(getPrice());
    }

    private void onRemoveFromCart(MouseEvent event) {
        event.consume();
        Menu menu = App.getControllerUnsafe();
        menu.cartItems.removeIf(item -> {
            CartItem cartItem = (CartItem) item;
            if (!cartItem.name().equals(getName()))
                return false;
            menu.updateTotalPrice(-cartItem.price());
            if (cartItem.quantity() > 1) {
                cartItem.updateQuantity(-1);
                return false;
            }
            return true;
        });
    }

    private void onRemoveFromMenu(MouseEvent event) {
        event.consume();
        Menu menu = App.getControllerUnsafe();
        menu.menuItems.remove(this);
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

    private MenuItem setImage(Image img) {
        imageView.setImage(img);
        return this;
    }

    public MenuItem setImage(File file) {
        var width = ((Menu) App.getController()).menuContainer.getPrefWidth();
        return setImage(new Image(file.toURI().toString(), width,
                width, true, true));
    }

    public MenuItem setImage(String path) {
        var width = ((Menu) App.getController()).menuContainer.getPrefWidth();
        return setImage(new Image(App.class.getResourceAsStream(path), width,
                width, true, true));
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
