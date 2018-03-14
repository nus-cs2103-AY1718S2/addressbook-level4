package seedu.address.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI for Login.  */
    void startLogin(Stage primaryStage);

    /** Starts the UI (and the App).  */
    void start();

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI for the App. */
    void stop();

}
