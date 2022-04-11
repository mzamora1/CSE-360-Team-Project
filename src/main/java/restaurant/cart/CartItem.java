package restaurant.cart;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import restaurant.Item;

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

    private CartItem build() {
        // setAlignment(Pos.TOP_CENTER);
        // setStyle("-fx-border-color: black");
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setAlignment(Pos.CENTER);
        // nameLabel.setStyle("-fx-border-color: blue");
        priceLabel.setWrapText(true);
        priceLabel.setAlignment(Pos.TOP_CENTER);
        priceLabel.setTextAlignment(TextAlignment.CENTER);
        // priceLabel.setStyle("-fx-border-color: red");
        // HBox.setHgrow(priceLabel, Priority.ALWAYS);
        quantityLabel.setWrapText(true);
        quantityLabel.setAlignment(Pos.TOP_CENTER);
        quantityLabel.setTextAlignment(TextAlignment.CENTER);
        // quantityLabel.setStyle("-fx-border-color: green");
        // HBox.setHgrow(quantityLabel, Priority.ALWAYS);
        getChildren().setAll(
                new Group(nameLabel), new Group(priceLabel), quantityLabel);
        return this;
    }

    private double nameWidth(double maxWidth) {
        return maxWidth * 0.5;
    }

    private double priceWidth(double maxWidth) {
        return maxWidth * 0.25;
    }

    private double quantityWidth(double maxWidth) {
        return maxWidth * 0.25;
    }

    public void update(double maxWidth) {
        setMaxWidth(maxWidth);
        nameLabel.setMaxWidth(nameWidth(maxWidth));
        nameLabel.setPrefWidth(nameWidth(maxWidth));
        priceLabel.setMaxWidth(priceWidth(maxWidth));
        priceLabel.setPrefWidth(priceWidth(maxWidth));

        quantityLabel.setMaxWidth(quantityWidth(maxWidth));
        quantityLabel.setPrefWidth(quantityWidth(maxWidth));
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
