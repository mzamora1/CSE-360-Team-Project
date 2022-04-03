package restaurant;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CartItem extends HBox {
    String name;
    float price;
    int quantity;

    CartItem(String iname, float iprice, int iquant) {
        super(10);
        name = iname;
        price = iprice;
        quantity = iquant;
        build();
    }

    CartItem build() {
        setAlignment(Pos.CENTER);
        Node nameElm = new Label(name),
                priceElm = new Label(Float.toString(price)),
                quantElm = new Label(Integer.toString(quantity));
        getChildren().addAll(nameElm, priceElm, quantElm);
        return this;
    }
}
