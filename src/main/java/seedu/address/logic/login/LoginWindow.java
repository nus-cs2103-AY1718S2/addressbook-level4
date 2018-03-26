package seedu.address.logic.login;

import javafx.application.Application;
import javafx.stage.Stage;

public class LoginWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Window");

        primaryStage.show();
    }
}
