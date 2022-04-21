package restaurant.login;

import static org.junit.Assert.assertTrue;
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
import restaurant.menu.MenuController;
import restaurant.users.Customer;

@RunWith(MockitoJUnitRunner.class)
public class LoginViewTest extends ApplicationTest {
    private static Scene scene;
    private static LoginView login;
    @Mock
    private EventHandler<SceneEvent> onChangeScenes;

    @Override
    public void start(Stage stage) throws Exception {
        login = new LoginView();
        scene = new Scene(login);
        scene.addEventFilter(SceneEvent.CHANGE_SCENE, onChangeScenes);
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
    @SuppressWarnings(value = "unchecked")
    public void tearDown() throws Exception {
        App.user = null;
        reset(onChangeScenes);
        FxToolkit.hideStage();
    }

    @Test
    public void testEmptyLogin() {
        var changeSceneEvent = ArgumentCaptor.forClass(SceneEvent.class);
        clickOn(login.lookup("#loginBtn"));
        assertTrue("User login without account" + App.user, App.user == null);
        verify(onChangeScenes, times(0)).handle(changeSceneEvent.capture());
    }

    @Test
    public void testValidLogin() {
        String username = "some user", password = "password";
        App.user = new Customer(username, password, true);
        var changeSceneEvent = ArgumentCaptor.forClass(SceneEvent.class);
        clickOn(login.lookup("#username"));
        write(username);
        clickOn(login.lookup("#password"));
        write(password);
        clickOn(login.lookup("#loginBtn"));
        verify(onChangeScenes, times(1)).handle(changeSceneEvent.capture());
        assertTrue(changeSceneEvent.getValue().controller instanceof MenuController);
    }

}
