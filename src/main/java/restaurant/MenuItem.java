package restaurant;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    String name;
    Image image;
    float price;
    Type type;
    String[] ingredients;
    int prepareTime;

    MenuItem() {
        super(10);
    }

    MenuItem(String iname, String path, float iprice, Type itype,
            String[] ingred, int ptime) {
        super(10);
        name = iname;
        image = new Image(path);
        price = iprice;
        type = itype;
        ingredients = ingred;
        prepareTime = ptime;
        build();
    }

    MenuItem build() {
        setAlignment(Pos.CENTER);
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button addToCartBtn = new Button("Add To Cart");

        addToCartBtn.setOnMouseClicked(event -> {
            event.consume();
            System.out.println(this);
            Menu.cartItems.add(new CartItem(name, price, 1));
        });
        Button removeBtn = new Button("Remove");
        removeBtn.setOnMouseClicked(event -> {
            event.consume();
            System.out.println(this);
            Menu.menuItems.remove(this);
        });
        buttons.getChildren().addAll(addToCartBtn, removeBtn);
        getChildren().addAll(
                new Label(name),
                new ImageView(image),
                new Label("Price: $" + price),
                new Label("Ingredients: " + ingredients),
                buttons);
        return this;
    }

    MenuItem setName(String val) {
        name = val;
        return this;
    }

    MenuItem setImage(Image img) {
        image = img;
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

    MenuItem setPrepareTime(int val) {
        prepareTime = val;
        return this;
    }
}
