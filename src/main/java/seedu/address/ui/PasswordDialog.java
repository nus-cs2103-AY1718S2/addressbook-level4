package seedu.address.ui;
//@@author crizyli
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Pop-up dialog to prompt user to enter unlock password.
 */
public class PasswordDialog extends Dialog<String> {

    private PasswordField passwordField;

    public PasswordDialog() {

        setTitle("Unlock ET");
        setHeaderText("Please Enter Unlock Password");
        setHeight(200.0);
        setWidth(350.0);

        ButtonType unlockButton = new ButtonType("Unlock", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(unlockButton);

        passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.setMinWidth(350.0);

        HBox hBox = new HBox();
        hBox.getChildren().add(passwordField);
        hBox.setMinHeight(200.0);
        hBox.setMaxWidth(350.0);


        HBox.setHgrow(passwordField, Priority.ALWAYS);

        getDialogPane().setContent(hBox);

        Platform.runLater(() -> passwordField.requestFocus());
        setResultConverter(dialogButton -> {
            if (dialogButton == unlockButton) {
                return passwordField.getText();
            }
            return null;
        });
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }
}
