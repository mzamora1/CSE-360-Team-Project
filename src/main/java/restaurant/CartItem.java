package restaurant;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class CartItem extends HBox {
    final Label nameLabel = new Label();
    final Label priceLabel = new Label();
    final Label quantityLabel = new Label();
    // float price;
    // int quantity;

    public CartItem(double maxWidth, String iname, float iprice, int iquant) {
        super(5);
        name(iname);
        price(iprice);
        quantity(iquant);
        build(maxWidth);
    }

    public CartItem build(double maxWidth) {
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(maxWidth * .50);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        priceLabel.setWrapText(true);
        priceLabel.setMaxWidth(maxWidth * .25);
        priceLabel.setTextAlignment(TextAlignment.CENTER);
        quantityLabel.setWrapText(true);
        quantityLabel.setMaxWidth(maxWidth * .25);
        quantityLabel.setTextAlignment(TextAlignment.CENTER);

        getChildren().setAll(
                new Group(nameLabel), new Group(priceLabel), quantityLabel);
        return this;
    }

    public String name() {
        return nameLabel.getText();
    }

    public void name(String iname) {
        nameLabel.setText(iname);
    }

    public float price() {
        return Float.parseFloat(priceLabel.getText());
    }

    public void price(float iprice) {
        priceLabel.setText(Float.toString(iprice));
    }

    public int quantity() {
        return Integer.parseInt(quantityLabel.getText());
    }

    public void quantity(int iquantity) {
        quantityLabel.setText(Integer.toString(iquantity));
    }

    public void updateQuantity(int changeAmount) {
        quantity(quantity() + changeAmount);
    }

}
