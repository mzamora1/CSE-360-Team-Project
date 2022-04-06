module restaurant {
    requires javafx.graphics; // had to add this line to get rid of warning on Scene object
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens restaurant to javafx.fxml;

    exports restaurant;
}
