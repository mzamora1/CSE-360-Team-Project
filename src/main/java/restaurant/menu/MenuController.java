package restaurant.menu;

import javafx.scene.Parent;
import restaurant.App;
import restaurant.Controller;

public class MenuController extends Controller {
    private MenuView menu;

    public MenuController() {
    }

    @Override
    public void build() {
        menu = new MenuView();
    }

    @Override
    public void update(double maxWidth, double maxHeight) {
        menu.update(maxWidth, maxHeight);
    }

    @Override
    public Parent getRoot() {
        if (App.user.isAdmin())
            menu.addAdminAbilities();
        else
            menu.removeAdminAbilities();
        return menu;
    }

}
