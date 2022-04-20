package restaurant.login;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import restaurant.App;
import restaurant.users.Customer;

public class LoginViewTest extends ApplicationTest {
    private static Scene scene;
    private static LoginView login;

    @Override
    public void start(Stage stage) throws Exception {
        login = new LoginView();
        scene = new Scene(login);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp() throws Exception {
        login = new LoginView();
        login.update(scene.getWidth(), scene.getHeight());
        scene.setRoot(login);
    }

    @After
    public void tearDown() throws Exception {
        App.user = null;
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }

    @Test
    public void testEmptyLogin() {
        clickOn(login.lookup("#loginBtn"));
        assertTrue("User login without account", App.user == null);
    }

    @Test
    public void testValidLogin() {
        String username = "some user", password = "password";
        App.user = new Customer(username, password, true);
        clickOn(login.lookup("#username"));
        write(username);
        clickOn(login.lookup("#password"));
        write(password);
        // we need to add dependency injection for the App for this test to work
        // or replace App references with AppEvents
        clickOn(login.lookup("#loginBtn"));

    }

}
