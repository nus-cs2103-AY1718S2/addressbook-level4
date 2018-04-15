package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CoinBookChangedEvent;
import seedu.address.commons.events.ui.FilterChangedEvent;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";
    public static final String ITEM_COUNT_STATUS = "%d item(s) total";
    public static final String FIND_COMMAND_STATUS = "Current filter: %s";

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
    private StatusBar itemCountStatus;
    @FXML
    private StatusBar filterStatus;


    public StatusBarFooter(int numItems, String saveLocation) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setItemCountStatus(numItems);
        setSaveLocation("./" + saveLocation);
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

    private void setItemCountStatus(int numItems) {
        Platform.runLater(() -> this.itemCountStatus.setText(String.format(ITEM_COUNT_STATUS, numItems)));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    private void setFindCommandStatus(String status) {
        Platform.runLater(() -> this.filterStatus.setText(String.format(FIND_COMMAND_STATUS, status)));
    }

    @Subscribe
    public void handleCoinBookChangedEvent(CoinBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setItemCountStatus(abce.data.getCoinList().size());
    }

    @Subscribe
    public void handleFilterChangedEvent(FilterChangedEvent fce) {
        logger.info(LogsCenter.getEventHandlingLogMessage(fce, "Setting filter info..."));
        setFindCommandStatus(fce.status);
    }
}
