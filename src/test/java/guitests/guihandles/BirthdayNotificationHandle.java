package guitests.guihandles;

import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

//@@author AzuraAiR
/**
 * A handle for the {@code AlertDialog} of the UI.
 */
public class BirthdayNotificationHandle extends StageHandle {
    private final DialogPane dialogPane;

    public BirthdayNotificationHandle(Stage stage) {
        super(stage);

        this.dialogPane = getChildNode("#" + "birthdayDialogPane");
    }

    /**
     * Returns the text of the header in the {@code AlertDialog}.
     */
    public String getHeaderText() {
        return dialogPane.getHeaderText();
    }

    /**
     * Returns the text of the content in the {@code AlertDialog}.
     */
    public String getContentText() {
        return dialogPane.getContentText();
    }
}
