package restaurant;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

public class CartItem extends HBox {
    private final Label nameLabel = new Label();
    private final Label priceLabel = new Label();
    private final Label quantityLabel = new Label();

    public CartItem(double maxWidth, String iname, float iprice, int iquant) {
        super(5);
        name(iname);
        price(iprice);
        quantity(iquant);
        build(maxWidth);
    }

    public CartItem build(double maxWidth) {
        setMaxWidth(maxWidth);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-border-color: black");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(maxWidth * .50);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setStyle("-fx-border-color: blue");
        priceLabel.setWrapText(true);
        priceLabel.setMaxWidth(maxWidth * .25);
        priceLabel.setTextAlignment(TextAlignment.CENTER);
        priceLabel.setStyle("-fx-border-color: red");
        HBox.setHgrow(priceLabel, Priority.ALWAYS);
        quantityLabel.setWrapText(true);
        quantityLabel.setMaxWidth(maxWidth * .25);
        quantityLabel.setAlignment(Pos.TOP_CENTER);
        quantityLabel.setTextAlignment(TextAlignment.CENTER);
        quantityLabel.setStyle("-fx-border-color: green");
        HBox.setHgrow(quantityLabel, Priority.ALWAYS);
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
