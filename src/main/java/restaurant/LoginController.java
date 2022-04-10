package restaurant;

import javafx.scene.Parent;

public class LoginController extends Controller {
    private LoginView login;

    @Override
    public void build() {
        // TODO Auto-generated method stub
        login = new LoginView();

    }

    @Override
    public void update(double maxWidth, double maxHeight) {
        // TODO Auto-generated method stub
        login.update(maxWidth, maxHeight);
    }

    @Override
    public Parent getRoot() {
        // TODO Auto-generated method stub
        return login;
    }

}
