package restaurant;

import javafx.scene.Parent;

public class MenuController extends Controller {
    private MenuView menu;

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