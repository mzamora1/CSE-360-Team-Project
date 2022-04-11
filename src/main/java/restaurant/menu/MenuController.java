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
        // TODO Auto-generated method stub
        menu = new MenuView();
    }

    @Override
    public void update(double maxWidth, double maxHeight) {
        // TODO Auto-generated method stub
        menu.update(maxWidth, maxHeight);
    }

    @Override
    public Parent getRoot() {
        // TODO Auto-generated method stub
        if (App.user.isAdmin())
            menu.addAdminAbilities();
        else
            menu.removeAdminAbilities();
        return menu;
    }

}
