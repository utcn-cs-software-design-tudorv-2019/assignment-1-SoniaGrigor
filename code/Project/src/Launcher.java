import controller.LoginController;
import view.LoginView;
import view.UserView;

public class Launcher {
    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.getInstance(false);
        new LoginController(new LoginView(), new UserView(), componentFactory.getAuthenticationService());
    }
}
