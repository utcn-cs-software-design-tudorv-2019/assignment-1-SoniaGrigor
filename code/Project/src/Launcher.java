import javafx.application.Application;
import javafx.stage.Stage;
import presentation.controller.LoginView;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentFactory componentFactory = ComponentFactory.getInstance(false);
        new LoginView(componentFactory.getAuthenticationService(), componentFactory.getCourseService(), componentFactory.getStudentService(), componentFactory.getUserService());
    }
}
