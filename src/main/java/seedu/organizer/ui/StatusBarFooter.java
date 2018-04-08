package seedu.organizer.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.organizer.commons.core.LogsCenter;
import seedu.organizer.commons.events.model.OrganizerChangedEvent;
import seedu.organizer.model.user.User;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";
    public static final String CURRENT_USER_STATUS_UPDATED = "Logged in as: %s";
    public static final String CURRENT_USER_STATUS_INITIAL = "Not logged in";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;
    @FXML
    private StatusBar currentUserStatus;

    public StatusBarFooter(String saveLocation) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        setCurrentUserStatus(CURRENT_USER_STATUS_INITIAL);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> this.saveLocationStatus.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    private void setCurrentUserStatus(String status) {
        Platform.runLater(() -> this.currentUserStatus.setText(status));
    }

    @Subscribe
    public void handleOrganizerChangedEvent(OrganizerChangedEvent oce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(oce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        User currentUser = oce.data.getCurrentLoggedInUser();
        if (currentUser == null) {
            setCurrentUserStatus(CURRENT_USER_STATUS_INITIAL);
        } else {
            setCurrentUserStatus(String.format(CURRENT_USER_STATUS_UPDATED, currentUser.username));
        }
    }
}
