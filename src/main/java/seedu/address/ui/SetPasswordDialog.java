package seedu.address.ui;
//@@author crizyli
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SetPasswordDialog extends Dialog<String> {

    private PasswordField oldPsw;

    private PasswordField newPsw;

    public SetPasswordDialog() {

        setTitle("Set New Password");
        setHeaderText("Please Enter old Password and new password below");
        setHeight(200.0);
        setWidth(350.0);

        ButtonType setButton = new ButtonType("set", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(setButton);

        oldPsw = new PasswordField();
        oldPsw.setPromptText("old password");
        oldPsw.setMinWidth(350.0);

        newPsw = new PasswordField();
        newPsw.setPromptText("new password");
        newPsw.setMinWidth(350.0);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(oldPsw, newPsw);
        hBox.setMinHeight(200.0);
        hBox.setMaxWidth(350.0);


        HBox.setHgrow(oldPsw, Priority.ALWAYS);

        getDialogPane().setContent(hBox);

        Platform.runLater(() -> newPsw.requestFocus());
        setResultConverter(dialogButton -> {
            if (dialogButton == setButton) {
                String oldp = oldPsw.getText();
                String newp = newPsw.getText();
                if (oldp.isEmpty() || newp.isEmpty()) {
                    return "incomplete";
                }
                String toReturn = oldp.concat(",").concat(newp);
                return toReturn;
            }
            return null;
        });
    }
}
