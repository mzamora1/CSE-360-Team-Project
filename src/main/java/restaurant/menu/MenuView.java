package restaurant.menu;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import restaurant.App;
import restaurant.AppEvent;
import restaurant.BackButton;
import restaurant.Item;
import restaurant.SceneEvent;
import restaurant.cart.Cart;
import restaurant.cart.CartEvent;
import restaurant.cart.CartItem;
import restaurant.checkout.CheckoutController;

public class MenuView extends BorderPane {
    private final Menu menu = new Menu();
    private final Cart cart = new Cart();

    private final BackButton backBtn = new BackButton();
    private final Label title = new Label("Menu");
    private final TextField searchBar = new TextField();
    private final VBox titleContainer = new VBox(title);
    private final AnchorPane top = new AnchorPane();
    private final Label priceTotal = new Label("Total Price: $0");
    private final Button checkoutBtn = new Button("Checkout");
    private final VBox right = new VBox(new Label("Cart"), cart, priceTotal, checkoutBtn);
    private final CheckBox couponCheckBox = new CheckBox("Apply Coupon");
    private final VBox bottom = new VBox(couponCheckBox);

    public MenuView() {
        super();
        build();
    }

    // construct and layout nodes on screen
    private void build() {
        AnchorPane.setLeftAnchor(backBtn, 10d);
        AnchorPane.setRightAnchor(searchBar, 10d);
        searchBar.setPromptText("search...");
        searchBar.setId("searchBar");
        // titleContainer.setStyle("-fx-border-color: blue");
        titleContainer.setAlignment(Pos.CENTER);
        top.getChildren().addAll(titleContainer, backBtn, searchBar);
        setTop(top);
        setCenter(menu);
        setRight(right);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(20));
        setBottom(bottom);
        right.setSpacing(10);
        right.setAlignment(Pos.CENTER);
        searchBar.textProperty().addListener(this::onSearch);
        addEventFilter(CartEvent.ADD_TO_CART, this::onAddToCart);
        addEventFilter(CartEvent.REMOVE_FROM_CART, this::onRemoveFromCart);
        checkoutBtn.setOnAction(AppEvent
                .firer(new SceneEvent(SceneEvent.CHANGE_SCENE, new CheckoutController()), this));
        checkoutBtn.setId("checkoutBtn");
        couponCheckBox.setOnAction(this::onApplyCoupon);
        couponCheckBox.setId("couponCheckBox");
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
    public void update(double maxWidth, double maxHeight) {
        titleContainer.setPrefWidth(maxWidth);
        menu.update(menuWidth(maxWidth), maxHeight - top.getHeight());
        cart.update(cartWidth(maxWidth), cartHeight(maxHeight));
    }

    // add elements to the screen that only admins can use
    public boolean addAdminAbilities() {
        if (!menu.addAdminAbilities())
            return false;
        addEventFilter(MenuEvent.REMOVE_FROM_MENU, this::onRemoveFromMenu);
        addEventFilter(MenuEvent.START_NEW_ITEM, this::onStartNewMenuItem);
        addEventFilter(MenuEvent.ADD_NEW_ITEM, this::onAddNewMenuItem);
        return true;
    }

    public void removeAdminAbilities() {
        menu.removeAdminAbilities();
    }

    private boolean isFilteredMenu = false;

    private <T extends Event> void onSearch(ObservableValue<? extends String> obs, String old, String newVal) {
        String input = newVal.trim().toLowerCase();
        System.out.println("Got: " + input);
        if (input.isEmpty() && isFilteredMenu) {
            menu.reset();
            isFilteredMenu = false;
            return;
        }

        menu.clear();
        for (var item : App.menuItems) {
            var menuItem = (MenuItem) item;
            if (menuItem.getName().toLowerCase().contains(input)) {
                menu.addUnsaved(menuItem);
            }
        }
        isFilteredMenu = true;
    }

    private <T extends Event> void onAddToCart(T event) {
        event.consume();
        var item = (Item) event.getTarget();
        cart.addToCart(item);
        changeTotalPriceBy(item.getPrice());
    }

    private <T extends Event> void onRemoveFromCart(T event) {
        event.consume();
        var item = cart.removeFromCart((Item) event.getTarget());
        if (item.isPresent())
            changeTotalPriceBy(-item.get().getPrice());
        else
            System.err.println("could not remove '" + event.getTarget() + "' from the cart");
    }

    private <T extends Event> void onStartNewMenuItem(T event) {
        event.consume();
        var startNewMenuItemBtn = event.getTarget();
        var itemInput = new MenuItemInput();
        menu.remove(startNewMenuItemBtn);
        menu.add(itemInput);
    }

    private <T extends Event> void onAddNewMenuItem(T event) {
        event.consume();
        var itemInput = (MenuItemInput) event.getTarget();
        if (itemInput.getFile().isEmpty())
            return;
        menu.remove(itemInput);
        var newItem = new MenuItem(menuWidth(getWidth()))
                .setName(itemInput.getName())
                .setDescription(itemInput.getDescription())
                .setImage(itemInput.getFile().get())
                .setIngredients(itemInput.getIngredients())
                .setPrice(itemInput.getPrice());
        menu.add(newItem);
        menu.add(itemInput);
    }

    private <T extends Event> void onRemoveFromMenu(T event) {
        event.consume();
        if (!menu.remove(event.getTarget())) {
            System.err.println("could not remove '" + event.getTarget() + "' from the menu");
        }
    }

    static final float COUPON_PRICE = -5f;

    private <T extends Event> void onApplyCoupon(T event) {
        event.consume();
        var coupon = new CartItem(cart.getPrefWidth(), "coupon", COUPON_PRICE, 1);
        if (couponCheckBox.isSelected() && totalPrice() > -COUPON_PRICE) {
            cart.addToCart(coupon);
            changeTotalPriceBy(COUPON_PRICE);
        } else {
            couponCheckBox.setSelected(false);
            cart.removeFromCart(coupon).ifPresent(c -> {
                changeTotalPriceBy(-c.getPrice());
            });
        }
    }

    private void changeTotalPriceBy(float changeAmount) {
        totalPrice(totalPrice() + changeAmount);
    }

    private void totalPrice(float iprice) {
        priceTotal.setText("Total Price: $" + iprice);
    }

    public float totalPrice() {
        String totalText = priceTotal.getText();
        return Float.parseFloat(totalText.substring(totalText.indexOf('$') + 1));
    }

}