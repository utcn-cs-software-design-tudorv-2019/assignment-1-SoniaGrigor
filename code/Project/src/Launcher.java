import controller.LoginController;
import javafx.application.Application;
import view.LoginView;

import static javafx.application.Application.launch;

public class Launcher {
    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.getInstance(false);
        new LoginController(new LoginView(), componentFactory.getAuthenticationService());
    }
}
