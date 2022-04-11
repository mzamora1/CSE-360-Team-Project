package restaurant.checkout;

import javafx.scene.Parent;
import restaurant.Controller;

public class CheckoutController extends Controller {

    private CheckoutView checkout;

    @Override
    public void build() {
        // TODO Auto-generated method stub
        checkout = new CheckoutView();
    }

    @Override
    public void update(double maxWidth, double maxHeight) {
        // TODO Auto-generated method stub
        checkout.update(maxWidth, maxHeight);
    }

    @Override
    public Parent getRoot() {
        // TODO Auto-generated method stub
        return checkout;
    }

}
