package restaurant.createAccount;

import javafx.scene.Parent;
import restaurant.Controller;

public class CreateAccountController extends Controller {

    private CreateAccountView createAccount;

    public CreateAccountController() {
    }

    @Override
    public void build() {
        createAccount = new CreateAccountView();
    }

    @Override
    public void update(double maxWidth, double maxHeight) {
        createAccount.update(maxWidth, maxHeight);
    }

    @Override
    public Parent getRoot() {
        return createAccount;
    }

}
