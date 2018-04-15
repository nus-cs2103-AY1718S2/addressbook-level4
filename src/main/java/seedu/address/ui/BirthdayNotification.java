package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BirthdayNotificationEvent;

//@@author AzuraAiR
/**
 * A ui for the notification dialog that is displayed at the start of the application or
 * after `birthdays today` is called.
 */
public class BirthdayNotification extends UiPart<Region> {

    public static final String NOTIFICATION_TITLE = "Birthdays Today";
    public static final String NOTIFICATION_HEADER_TEXT = "Birthday(s) on ";

    private static final String FXML = "BirthdayList.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public BirthdayNotification() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleBirthdayNotificationEvent(BirthdayNotificationEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.setTitle(NOTIFICATION_TITLE);
        alert.setHeaderText(NOTIFICATION_HEADER_TEXT + dtf.format(event.getCurrentDate()));
        alert.setContentText(event.getBirthdayList());
        alert.getDialogPane().setId("birthdayDialogPane");
        alert.showAndWait();
    }
}
