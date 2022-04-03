package restaurant;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

public class CartItem extends HBox {
    String name;
    float price;
    int quantity;

    CartItem(String iname, float iprice, int iquant) {
        super(5);
        name = iname;
        price = iprice;
        quantity = iquant;
        build();
    }

    CartItem build() {
        // setAlignment(Pos.CENTER);
        Label nameElm = new Label(name),
                priceElm = new Label(Float.toString(price)),
                quantElm = new Label(Integer.toString(quantity));
        var maxWidth = Menu.cartPrefWidth;
        nameElm.setWrapText(true);
        nameElm.setMaxWidth(maxWidth * .50);
        nameElm.setTextAlignment(TextAlignment.CENTER);
        nameElm.setAlignment(Pos.CENTER);
        priceElm.setWrapText(true);
        priceElm.setMaxWidth(maxWidth * .25);
        // priceElm.setTextAlignment(TextAlignment.CENTER);
        priceElm.setAlignment(Pos.CENTER);
        quantElm.setWrapText(true);
        quantElm.setMaxWidth(maxWidth * .25);
        // quantElm.setTextAlignment(TextAlignment.CENTER);
        quantElm.setAlignment(Pos.CENTER);
        var group = new Group(nameElm);

        getChildren().setAll(new Group(nameElm), new Group(priceElm), quantElm);
        return this;
    }

}
