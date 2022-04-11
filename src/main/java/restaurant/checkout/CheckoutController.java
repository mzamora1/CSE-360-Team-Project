package restaurant.checkout;

import javafx.scene.Parent;
import restaurant.Controller;

public class CheckoutController extends Controller {

    private CheckoutView checkout;

    @Override
    public void build() {
        checkout = new CheckoutView();
    }

    @Override
    public void update(double maxWidth, double maxHeight) {
        checkout.update(maxWidth, maxHeight);
    }

    @Override
    public Parent getRoot() {
        return checkout;
    }

}
