package restaurant.login;

import javafx.scene.Parent;
import restaurant.Controller;

public class LoginController extends Controller {
    private LoginView login;

    public LoginController() {
    }

    @Override
    public void build() {
        login = new LoginView();

    }

    @Override
    public void update(double maxWidth, double maxHeight) {
        login.update(maxWidth, maxHeight);
    }

    @Override
    public Parent getRoot() {
        return login;
    }

}
