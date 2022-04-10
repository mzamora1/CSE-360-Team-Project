package restaurant;

import javafx.scene.Parent;

public class CreateAccountController extends Controller {

    private CreateAccountView createAccount;

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
