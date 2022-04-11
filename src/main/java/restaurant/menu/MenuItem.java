package restaurant.menu;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import restaurant.App;
import restaurant.Item;

// MenuItem is a simple wrapper around a VBox that contains all the information of a MenuItem
// it does not know about any other views or controllers
public class MenuItem extends VBox implements Item {

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
    }

    // admin constructor
    public MenuItem(double maxWidth) {
        super(10);
        build();
        update(maxWidth);
        addAdminAbilities();
    }

    public MenuItem build() {
        setAlignment(Pos.CENTER);
        buttonContainer.setAlignment(Pos.CENTER);

        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setAlignment(Pos.CENTER);
        ingredientsLabel.setWrapText(true);
        ingredientsLabel.setTextAlignment(TextAlignment.CENTER);
        ingredientsLabel.setAlignment(Pos.CENTER);
        imageView.setPreserveRatio(true);
        getChildren().setAll(
                nameLabel,
                new Group(descriptionLabel),
                imageView,
                priceLabel,
                new Group(ingredientsLabel),
                buttonContainer);

        addToCartBtn.setOnAction(eventFirer(MenuEvent.ADD_TO_CART));
        rmvFromCartBtn.setOnAction(eventFirer(MenuEvent.REMOVE_FROM_CART));
        return this;
    }

    private double menuItemWidth(double maxWidth) {
        return maxWidth * .75;
    }

    public void update(double maxWidth) {
        descriptionLabel.setMaxWidth(menuItemWidth(maxWidth));
        ingredientsLabel.setMaxWidth(menuItemWidth(maxWidth));
        imageView.setFitWidth(menuItemWidth(maxWidth));
    }

    private static final int ADMIN_LENGTH = 3;

    private boolean hasAdminAbilities() {
        return buttonContainer.getChildren().size() == ADMIN_LENGTH;
    }

    // responsible for adding elements to the screen that only admins can use
    public boolean addAdminAbilities() {
        if (hasAdminAbilities())
            return false;
        Button rmvFromMenuBtn = new Button("Remove From Menu");
        rmvFromMenuBtn.setOnAction(eventFirer(MenuEvent.REMOVE_FROM_MENU));
        buttonContainer.getChildren().add(rmvFromMenuBtn);
        return true;
    }

    public boolean removeAdminAbilities() {
        if (!hasAdminAbilities())
            return false;
        buttonContainer.getChildren().remove(ADMIN_LENGTH - 1);
        return true;
    }

    private <T extends Event> EventHandler<T> eventFirer(EventType<MenuEvent> type) {
        return event -> {
            var menuEvent = new MenuEvent(this, this, type);
            fireEvent(menuEvent);
            if (menuEvent.isConsumed()) {
                event.consume();
            }
        };
    }

    public String getName() {
        return nameLabel.getText();
    }

    public float getPrice() {
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
        var width = imageView.getFitWidth();
        return setImage(new Image(file.toURI().toString(), width,
                width, true, true));
    }

    public MenuItem setImage(String path) {
        var width = imageView.getFitWidth();
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
