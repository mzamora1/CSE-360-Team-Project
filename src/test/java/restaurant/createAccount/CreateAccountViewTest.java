package restaurant.createAccount;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import restaurant.App;
import restaurant.SceneEvent;
import restaurant.login.LoginController;
import restaurant.users.Customer;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountViewTest extends ApplicationTest {

    private static Scene scene;
    private static CreateAccountView view;
    @Mock
    private EventHandler<SceneEvent> onChangeScenes;

    @Override
    public void start(Stage stage) throws Exception {
        view = new CreateAccountView();
        scene = new Scene(view);
        scene.addEventFilter(SceneEvent.CHANGE_SCENE, onChangeScenes);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp() throws Exception {
        view = new CreateAccountView();
        view.update(scene.getWidth(), scene.getHeight());
        scene.setRoot(view);
    }

    @After
    @SuppressWarnings(value = "unchecked")
    public void tearDown() throws Exception {
        App.user = null;
        reset(onChangeScenes);
        FxToolkit.hideStage();
    }

    @Test
    public void testEmptyAccount() {
        var changeSceneEvent = ArgumentCaptor.forClass(SceneEvent.class);
        clickOn("#submit");
        verify(onChangeScenes, times(0)).handle(changeSceneEvent.capture());
    }

    @Test
    public void testValidAccount() {
        String email = "e", password = "p", cardNumber = "1",
                ccv = "1", expiration = "1";
        var changeSceneEvent = ArgumentCaptor.forClass(SceneEvent.class);
        clickOn("#email");
        write(email);
        clickOn("#password");
        write(password);
        clickOn("#cardNumber");
        write(cardNumber);
        clickOn("#ccv");
        write(ccv);
        clickOn("#expiration");
        write(expiration);
        clickOn("#submit");
        verify(onChangeScenes, times(1)).handle(changeSceneEvent.capture());
        assertEquals(changeSceneEvent.getValue().controller.getClass(), LoginController.class);
        assertEquals(App.user.getName(), email);
        assertEquals(App.user.getPass(), password);
        assertEquals(App.user.getClass(), Customer.class);
        Customer user = (Customer) App.user;
        assertEquals(user.getCardNum(), cardNumber);
        assertEquals(user.getCardCCV(), ccv);
        assertEquals(user.getCardExp(), expiration);
    }

}
