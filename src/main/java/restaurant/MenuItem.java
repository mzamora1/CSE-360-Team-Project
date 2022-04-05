package restaurant;

import java.net.URI;
import java.util.Arrays;
import javafx.fxml.FXML;

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

    enum Type {
        Entree("Entree"), Side("Side"), Drink("Drink");

        private String value;

        private Type(String val) {
            value = val;
        }

        public String toString() {
            return value;
        }
    }
    @FXML
    public Label priceTotal;
    
    String name;
    String description;
    Image image;
    float price;
    Type type;
    String[] ingredients;
    int prepareTime;
    
    float totalPrice;

    MenuItem() {
        super(10);
    }

    MenuItem(String iname, String descrip, String path, float iprice, Type itype,
            String[] ingred, int ptime) {
        super(10);
        name = iname;
        description = descrip;
        image = new Image(path);
        price = iprice;
        type = itype;
        ingredients = ingred;
        prepareTime = ptime;
        build();
    }

    MenuItem build() {
        setAlignment(Pos.CENTER);
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        var buttons = buttonContainer.getChildren();
        
        Button addToCartBtn = new Button("Add To Cart");
        addToCartBtn.setOnMouseClicked(event -> {
            event.consume();

            for (var item : Menu.cartItems) {
                CartItem cartItem = (CartItem) item;
                if (cartItem.name == name) {
                    cartItem.quantity++;
                    cartItem.build(Menu.cartPrefWidth);
                    Menu.updateTotalPrice();
                    return;
                }
            }
            Menu.cartItems.add(new CartItem(Menu.cartPrefWidth, name, price, 1));
            Menu.updateTotalPrice();
        });
        buttons.add(addToCartBtn);
        
        Button rmvFromCartBtn = new Button ("Remove From Cart");
        rmvFromCartBtn.setOnMouseClicked(event -> {
                event.consume();
                for (var item : Menu.cartItems) {
                    CartItem cartItem = (CartItem) item;
                    if (cartItem.name == name && cartItem.quantity > 1) {
                        cartItem.quantity--;
                        cartItem.build(Menu.cartPrefWidth);
                        Menu.updateTotalPrice();
                        return;
                    }else if(cartItem.name == name && cartItem.quantity <= 1){
                        Menu.cartItems.remove(cartItem);
                        Menu.updateTotalPrice();
                    }
                }
        });
        buttons.add(rmvFromCartBtn);

        if (App.user.getAdmin()) {
            Button removeBtn = new Button("Remove From Menu");
            removeBtn.setOnMouseClicked(event -> {
                event.consume();
                Menu.menuItems.remove(this);
            });
            buttons.add(removeBtn);
            setUserData("isAdmin");
        }
        var maxWidth = Menu.menuPrefWidth;
        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(maxWidth * .75);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setAlignment(Pos.CENTER);

        String ingred = Arrays.toString(ingredients);
        Label ingredLabel = new Label("Ingredients: " + ingred.substring(1, ingred.length() - 1));
        ingredLabel.setWrapText(true);
        ingredLabel.setMaxWidth(maxWidth * .75);
        ingredLabel.setTextAlignment(TextAlignment.CENTER);
        ingredLabel.setAlignment(Pos.CENTER);
        
        getChildren().setAll(
                new Label(name),
                new Group(descriptionLabel),
                new ImageView(image),
                new Label("Price: $" + price),
                new Group(ingredLabel),
                buttonContainer);
        return this;
        
    }

    boolean removeRemoveBtn() {
        if (getUserData() != "isAdmin")
            return false;
        HBox buttonContainer = (HBox) getChildren().get(getChildren().size() - 1);
        var buttons = buttonContainer.getChildren();
        buttons.remove(buttons.get(buttons.size() - 1));
        return true;
    }

    MenuItem setName(String val) {
        name = val;
        return this;
    }

    MenuItem setDescription(String val) {
        description = val;
        return this;
    }

    MenuItem setImage(Image img) {
        image = img;
        return this;
    }

    MenuItem setImage(URI uri) {
        image = new Image(uri.toString(), Menu.menuPrefWidth,
                Menu.menuPrefWidth, true, true);
        return this;
    }

    MenuItem setImage(String path) {
        image = new Image(App.class.getResource(path).toString(), Menu.menuPrefWidth,
                Menu.menuPrefWidth, true, true);
        return this;
    }

    MenuItem setPrice(float val) {
        price = val;
        return this;
    }

    MenuItem setType(Type val) {
        type = val;
        return this;
    }

    MenuItem setIngredients(String[] val) {
        ingredients = val;
        return this;
    }

    MenuItem setIngredients(String val) {
        ingredients = val.split(",");
        for (String str : ingredients) {
            str = str.trim();
        }
        return this;
    }

    MenuItem setPrepareTime(int val) {
        prepareTime = val;
        return this;
    }
}
