module restaurant {
    requires javafx.graphics; // had to add this line to get rid of warning on Scene object
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens restaurant to javafx.fxml;

    exports restaurant;
    exports restaurant.createAccount;
    exports restaurant.login;
    exports restaurant.menu;

    // allows Mock annotations to work in testing
    // opens restaurant;
    opens restaurant.menu;
    opens restaurant.createAccount;
    opens restaurant.login;
    opens restaurant.checkout;
}
