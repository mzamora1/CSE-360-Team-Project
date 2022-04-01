package restaurant;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    double price;
    Type type;
    String[] ingredients;

    MenuItem() {
        super();
    }

    MenuItem(String iname, String path, double iprice, Type itype, String[] ingred) {
        super();
        name = iname;
        image = new Image(path);
        price = iprice;
        type = itype;
        ingredients = ingred;
        build();
    }

    MenuItem build() {
        // var children = getChildren();
        // if(name.isEmpty())
        // children.add(new Label(name));
        // String[] validSuffixes = { "bmp", "gif", "jpeg", "png" };
        // boolean isValid = false;
        // for (var suffix : validSuffixes)
        // if (imgPath.endsWith(suffix)) {
        // isValid = true;
        // break;
        // }
        // if (!isValid)
        // throw new IllegalArgumentException(
        // "Supplied image is not one of these formats:\n" + validSuffixes);

        // System.out.println("Image path: " + imgPath);
        setAlignment(Pos.CENTER);
        getChildren().addAll(
                new Label(name),
                new ImageView(image),
                new Label("Price: $" + price),
                new Label("Ingredients: " + ingredients));
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

    MenuItem setPrice(double val) {
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
}
