package restaurant;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MenuItemInput extends VBox implements Item {
    private final TextField nameInput = new TextField();
    private final Button openImageChooserBtn = new Button("Choose an image");
    private final TextField descriptInput = new TextField();
    private final TextField ingredInput = new TextField();
    private final TextField priceInput = new TextField();
    private final Button createMenuItemBtn = new Button("Create Item");

    public MenuItemInput() {
        super(5);
        build();
    }

    private void build() {
        setAlignment(Pos.CENTER);
        nameInput.setPromptText("name of item...");
        descriptInput.setPromptText("description of item...");
        ingredInput.setPromptText("comma seperated ingredients...");
        priceInput.setPromptText("price of item...");
        getChildren().setAll(
                nameInput,
                openImageChooserBtn,
                descriptInput,
                ingredInput,
                priceInput,
                createMenuItemBtn);
    }

    public void setOnCreateMenuItem(EventHandler<? super MouseEvent> val) {
        createMenuItemBtn.setOnMouseClicked(val);
    }

    public void setOnOpenImageChooser(EventHandler<? super MouseEvent> val) {
        openImageChooserBtn.setOnMouseClicked(val);
    }

    public String getName() {
        return nameInput.getText();
    }

    public String getDescription() {
        return descriptInput.getText();
    }

    public String getIngredients() {
        return ingredInput.getText();
    }

    public float getPrice() {
        try {
            return Float.parseFloat(priceInput.getText());
        } catch (NumberFormatException e) {
            return Float.NaN;
        }
    }
}
