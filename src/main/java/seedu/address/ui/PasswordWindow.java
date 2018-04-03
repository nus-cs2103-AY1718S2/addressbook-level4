package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

//@@author yeggasd
/**
 * The Password Window. Provides the basic application layout containing
 * space where other JavaFX elements can be placed.
 */
public class PasswordWindow extends UiPart<Stage> {
    private static final String PASSWORDBOX_TITLE = "Key In Password";
    private static final String FXML = "PasswordWindow.fxml";

    private final Storage storage;
    private final Model model;

    private Stage primaryStage;

    @FXML
    private StackPane passwordBoxPlaceholder;

    public PasswordWindow(Stage primaryStage, Model model, Storage storage) {
        super(FXML, primaryStage);
        // Set dependencies
        this.primaryStage = primaryStage;
        this.storage = storage;
        this.model = model;

        setTitle(PASSWORDBOX_TITLE);
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        PasswordBox passwordBox = new PasswordBox(storage, model);
        passwordBoxPlaceholder.getChildren().add(passwordBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    void releaseResources() {
    }
}
