package restaurant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MenuController extends BorderPane implements Updatable {
    private final Menu menu = new Menu();
    private final Cart cart = new Cart();

    private final Button backBtn = new Button("Back");
    private final HBox top = new HBox(backBtn);
    private final Label priceTotal = new Label("Total Price: $0");
    private final Button checkoutBtn = new Button("Checkout");
    private final VBox right = new VBox(cart, priceTotal, checkoutBtn);

    public MenuController(double maxWidth, double maxHeight) {
        super();
        build();
        update(maxWidth, maxHeight);
        if (App.user.isAdmin()) {
            addAdminAbilities();
        }
    }

    // construct and layout nodes on screen
    private void build() {
        setTop(top);
        setCenter(menu);
        setRight(right);
        menu.setOnAddToCart(this::onAddToCart);
        menu.setOnRemoveFromCart(this::onRemoveFromCart);
        backBtn.setOnAction(this::onGoBack);
        checkoutBtn.setOnAction(this::onCheckout);
    }

    private double menuWidth(double maxWidth) {
        return maxWidth * 0.75;
    }

    private double cartWidth(double maxWidth) {
        return maxWidth * 0.25;
    }

    private double cartHeight(double maxHeight) {
        return maxHeight * 0.75;
    }

    // update size of nodes
    @Override
    public void update(double maxWidth, double maxHeight) {
        menu.update(menuWidth(maxWidth), maxHeight);
        cart.update(cartWidth(maxWidth), cartHeight(maxHeight));
    }

    // add elements to the screen that only admins can use
    private boolean addAdminAbilities() {
        var result = menu.addAdminAbilities();
        menu.setOnRemoveFromMenu(this::onRemoveFromMenu);
        menu.setOnStartNewMenuItem(this::onStartNewMenuItem);
        return result;
    }

    public void removeAdminAbilities() {
        menu.removeAdminAbilities();
    }

    private <T extends Event> void onAddToCart(T event) {
        event.consume();
        var item = (Item) event.getSource();
        cart.addToCart(item);
        changeTotalPriceBy(item.getPrice());
    }

    private <T extends Event> void onRemoveFromCart(T event) {
        event.consume();
        var item = cart.removeFromCart((Item) event.getSource());
        item.ifPresent(cartItem -> changeTotalPriceBy(-cartItem.getPrice()));
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
        var startNewMenuItemBtn = event.getSource();
        var itemInput = new MenuItemInput();
        itemInput.setOnOpenImageChooser(MenuController::onOpenImageChooser);
        itemInput.setOnCreateMenuItem(e -> {
            if (choosenFile == null)
                return;
            menu.remove(itemInput);
            var newItem = new MenuItem(
                    this::onAddToCart, this::onRemoveFromCart, this::onRemoveFromMenu, menuWidth(getWidth()))
                    .setName(itemInput.getName())
                    .setDescription(itemInput.getDescription())
                    .setImage(choosenFile)
                    .setIngredients(itemInput.getIngredients())
                    .setPrice(itemInput.getPrice());
            menu.add(newItem);
            menu.add(itemInput);
        });
        menu.remove(startNewMenuItemBtn);
        menu.add(itemInput);
    }

    private <T extends Event> void onRemoveFromMenu(T event) {
        event.consume();
        if (!menu.remove(event.getSource())) {
            System.err.println("could not remove '" + event.getSource() + "' from the menu");
        }
    }

    private <T extends Event> void onCheckout(T event) {
        System.out.println("not implemented");
    }

    private <T extends Event> void onGoBack(T event) {
        System.out.println("not implemented");
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
