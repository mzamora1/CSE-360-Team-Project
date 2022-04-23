package restaurant.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import restaurant.AppEvent;
import restaurant.Item;

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
        setId("menuItemInput");
        nameInput.setPromptText("name of item...");
        nameInput.setId("name");
        descriptInput.setPromptText("description of item...");
        descriptInput.setId("description");
        ingredInput.setPromptText("comma seperated ingredients...");
        ingredInput.setId("ingredients");
        priceInput.setPromptText("price of item...");
        priceInput.setId("price");
        openImageChooserBtn.setOnAction(this::onOpenImageChooser);
        openImageChooserBtn.setId("openImageChooserBtn");
        createMenuItemBtn.setOnAction(AppEvent.firer(new MenuEvent(MenuEvent.ADD_NEW_ITEM), this));
        createMenuItemBtn.setId("createMenuItem");
        getChildren().setAll(
                nameInput,
                openImageChooserBtn,
                descriptInput,
                ingredInput,
                priceInput,
                createMenuItemBtn);
    }

    public String getName() {
        return nameInput.getText();
    }

    public String getDescription() {
        return descriptInput.getText();
    }

    File choosenFile;

    private <T extends Event> void onOpenImageChooser(T event) {
        event.consume();
        var chooser = new FileChooser();
        List<String> validExts = new ArrayList<>(List.of("*.bmp", "*.gif", "*.jpeg", "*.png"));
        validExts.addAll(validExts.stream().map(String::toUpperCase)
                .collect(Collectors.toList()));

        chooser.getExtensionFilters().add(
                new ExtensionFilter("Image files(bmp, gif, jpeg, png)", validExts));
        choosenFile = chooser.showOpenDialog(null);
    }

    void removeOnOpenImageChooser() {
        openImageChooserBtn.setOnAction(event -> {
        });
    }

    public Optional<File> getFile() {
        return Optional.ofNullable(choosenFile);
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
