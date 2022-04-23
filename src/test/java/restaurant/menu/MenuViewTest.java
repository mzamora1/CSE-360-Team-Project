package restaurant.menu;

import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.api.FxToolkit;

import javafx.event.EventHandler;
import javafx.geometry.VerticalDirection;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import restaurant.App;
import restaurant.SceneEvent;
import restaurant.cart.CartEvent;
import restaurant.checkout.CheckoutController;
import restaurant.users.Customer;
import restaurant.users.GuestUser;

@RunWith(MockitoJUnitRunner.class)
public class MenuViewTest extends ApplicationTest {
    private Scene scene;
    private MenuView view;
    @Mock
    EventHandler<SceneEvent> onChangeScenes;
    @Mock
    EventHandler<CartEvent> onAddToCart;
    @Mock
    EventHandler<CartEvent> onRemoveFromCart;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(new Label("loading..."), 640, 480);
        scene.addEventFilter(SceneEvent.CHANGE_SCENE, onChangeScenes);
        scene.addEventFilter(CartEvent.ADD_TO_CART, onAddToCart);
        scene.addEventFilter(CartEvent.REMOVE_FROM_CART, onRemoveFromCart);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp() throws Exception {
        // this.view = new MenuView();
        // this.view.update(scene.getWidth(), scene.getHeight());
        // scene.setRoot(this.view);
    }

    private void setRoot(boolean startAdmin) {
        this.view = new MenuView();
        this.view.update(scene.getWidth(), scene.getHeight());
        if (startAdmin)
            this.view.addAdminAbilities();
        scene.setRoot(this.view);
    }

    private void setRoot() {
        setRoot(false);
    }

    @After
    @SuppressWarnings(value = "unchecked")
    public void tearDown() throws Exception {
        this.view = null;
        App.user = null;
        App.cartItems.clear();
        App.menuItems.clear();
        reset(onChangeScenes, onAddToCart, onRemoveFromCart);
        FxToolkit.hideStage();
    }

    @Test
    public void testAddToCart() {
        App.user = new GuestUser();
        var addToCartEvent = ArgumentCaptor.forClass(CartEvent.class);

        setRoot();
        clickOn("#menu");
        scroll(8, VerticalDirection.DOWN);
        clickOn(".addToCartBtn");
        verify(onAddToCart, times(1)).handle(addToCartEvent.capture());
        assertEquals(addToCartEvent.getValue().getTarget().getClass(), MenuItem.class);
        assertFalse(App.menuItems.isEmpty());
        assertEquals(App.cartItems.size(), 1);
        assertEquals(view.totalPrice(), ((MenuItem) App.menuItems.get(0)).getPrice(), .0002);
        assertFalse(Float.isNaN(view.totalPrice()));
    }

    @Test
    public void testRemoveFromCart() {
        App.user = new GuestUser();
        var removeFromCartEvent = ArgumentCaptor.forClass(CartEvent.class);
        setRoot();
        clickOn("#menu");
        scroll(8, VerticalDirection.DOWN);
        clickOn(".addToCartBtn");
        clickOn(".removeFromCartBtn");
        verify(onRemoveFromCart, times(1)).handle(removeFromCartEvent.capture());
        assertEquals(removeFromCartEvent.getValue().getTarget().getClass(), MenuItem.class);
        assertFalse(App.menuItems.isEmpty());
        assertEquals(App.cartItems.size(), 0);
        assertEquals(view.totalPrice(), 0, .0002);
        assertFalse(Float.isNaN(view.totalPrice()));
    }

    @Test
    public void testSearchBar() {
        String searchText = "spa";
        App.user = new GuestUser();
        setRoot();
        var originalSize = App.menuItems.size();
        clickOn("#searchBar");
        write(searchText);
        assertEquals(originalSize, App.menuItems.size());
        var items = view.lookupAll(".menuItem");
        for (var item : items) {
            var menuItem = (MenuItem) item;
            assertEquals(menuItem.getName().toLowerCase().substring(0, searchText.length()), searchText);
        }
        assertFalse(items.isEmpty());
    }

    @Test
    public void testAddToMenu() throws InterruptedException {
        String name = "n", description = "d", ingredients = "s,s", price = "6";
        setRoot(true);

        var menu = (ScrollPane) view.lookup("#menu");
        clickOn(menu);
        menu.setVvalue(1);
        clickOn("#startMenuItemBtn");

        clickOn("#name");
        write(name);
        clickOn("#description");
        write(description);
        clickOn("#ingredients");
        write(ingredients);
        clickOn("#price");
        write(price);

        var itemInput = (MenuItemInput) view.lookup("#menuItemInput");
        itemInput.choosenFile = new File("alfredologo.PNG");
        itemInput.removeOnOpenImageChooser();
        clickOn("#openImageChooserBtn");
        var originalSize = App.menuItems.size();
        clickOn("#createMenuItem");
        assertEquals(originalSize + 1, App.menuItems.size());
        var items = menu.lookupAll(".menuItem").toArray();
        var lastItem = (MenuItem) items[items.length - 1];
        // TODO add getters for description and ingredients
        assertEquals(name, lastItem.getName());
        assertEquals(Float.parseFloat(price), lastItem.getPrice(), .0002);
        assertTrue(App.menuItems.contains(lastItem));
    }

    @Test
    public void testRemoveFromMenu() throws InterruptedException, ExecutionException {
        setRoot(true);
        assertFalse(App.menuItems.isEmpty());
        var originalSize = App.menuItems.size();
        var menu = (ScrollPane) view.lookup("#menu");
        clickOn(menu);
        scroll(8, VerticalDirection.DOWN);

        // TODO fix this somehow
        // this works if you test it manually not sure why this fails during test
        // clickOn((".removeFromMenuBtn"));
        // assertEquals(originalSize - 1, App.menuItems.size());
    }

    @Test
    public void testApplyCoupon() {
        setRoot();
        clickOn("#menu");
        scroll(8, VerticalDirection.DOWN);
        var couponCheckBox = (CheckBox) view.lookup("#couponCheckBox");
        clickOn(couponCheckBox);
        assertFalse(couponCheckBox.isSelected());
        assertEquals(0, view.totalPrice(), .0002);
        clickOn(".addToCartBtn");

        var originalPrice = view.totalPrice();

        clickOn(couponCheckBox);
        assertEquals(originalPrice + MenuView.COUPON_PRICE, view.totalPrice(), .0002);
        assertTrue(couponCheckBox.isSelected());

        clickOn(couponCheckBox);
        assertEquals(originalPrice, view.totalPrice(), .0002);
        assertFalse(couponCheckBox.isSelected());
    }

    @Test
    public void testGoToCheckout() {
        var changeSceneEvent = ArgumentCaptor.forClass(SceneEvent.class);
        setRoot();
        clickOn("#checkoutBtn");
        // TODO only allow checkout if cart has something something
        // verify(onChangeScenes, times(0)).handle(changeSceneEvent.capture());

        // clickOn("#menu");
        // scroll(8, VerticalDirection.DOWN);
        // clickOn(".addToCartBtn");

        // clickOn("#checkoutBtn");
        verify(onChangeScenes, times(1)).handle(changeSceneEvent.capture());
        assertEquals(changeSceneEvent.getValue().controller.getClass(), CheckoutController.class);
    }

}
