package restaurant.createAccount;

import javafx.scene.Parent;
import restaurant.Controller;

public class CreateAccountController extends Controller {

    private CreateAccountView createAccount;

    public CreateAccountController() {
    }

    @Override
    public void build() {
        // TODO Auto-generated method stub
        createAccount = new CreateAccountView();
    }

    @Override
    public void update(double maxWidth, double maxHeight) {
        // TODO Auto-generated method stub
        createAccount.update(maxWidth, maxHeight);
    }

    @Override
    public Parent getRoot() {
        // TODO Auto-generated method stub
        return createAccount;
    }

}
