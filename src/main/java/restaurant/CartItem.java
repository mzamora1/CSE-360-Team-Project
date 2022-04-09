package restaurant;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

public class CartItem extends HBox implements Item {
    private final Label nameLabel = new Label();
    private final Label priceLabel = new Label();
    private final Label quantityLabel = new Label();

    public CartItem(double maxWidth, String iname, float iprice, int iquant) {
        super(5);
        setName(iname);
        setPrice(iprice);
        setQuantity(iquant);
        build();
        update(maxWidth);
    }

    public CartItem build() {
        setAlignment(Pos.TOP_CENTER);
        // setStyle("-fx-border-color: black");
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        // nameLabel.setStyle("-fx-border-color: blue");
        priceLabel.setWrapText(true);
        priceLabel.setTextAlignment(TextAlignment.CENTER);
        // priceLabel.setStyle("-fx-border-color: red");
        HBox.setHgrow(priceLabel, Priority.ALWAYS);
        quantityLabel.setWrapText(true);
        quantityLabel.setAlignment(Pos.TOP_CENTER);
        quantityLabel.setTextAlignment(TextAlignment.CENTER);
        // quantityLabel.setStyle("-fx-border-color: green");
        HBox.setHgrow(quantityLabel, Priority.ALWAYS);
        getChildren().setAll(
                new Group(nameLabel), new Group(priceLabel), quantityLabel);
        return this;
    }

    public void update(double maxWidth) {
        setMaxWidth(maxWidth);
        nameLabel.setMaxWidth(maxWidth * .50);
        priceLabel.setMaxWidth(maxWidth * .25);
        quantityLabel.setMaxWidth(maxWidth * .25);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public CartItem setName(String iname) {
        nameLabel.setText(iname);
        return this;
    }

    public float getPrice() {
        return Float.parseFloat(priceLabel.getText());
    }

    public CartItem setPrice(float iprice) {
        priceLabel.setText(Float.toString(iprice));
        return this;
    }

    public int getQuantity() {
        return Integer.parseInt(quantityLabel.getText());
    }

    public void setQuantity(int iquantity) {
        quantityLabel.setText(Integer.toString(iquantity));
    }

    public void changeQuantityBy(int changeAmount) {
        setQuantity(getQuantity() + changeAmount);
    }

}
