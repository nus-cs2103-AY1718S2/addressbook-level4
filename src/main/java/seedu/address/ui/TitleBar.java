package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.MaximizeAppRequestEvent;
import seedu.address.commons.events.ui.MinimizeAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

/**
 * UI for the title bar that is displayed at the top of the application.
 */
public class TitleBar extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(TitleBar.class);
    private static final String FXML = "TitleBar.fxml";

    @FXML
    private Label topStatusMessage;
    @FXML
    private Label topStatusFile;

    @FXML
    private Pane controlHelp;


    public TitleBar(String saveLocation) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        TitleBar.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> this.topStatusFile.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.topStatusMessage.setText(status));
    }

    /**
     * Return {@code controlHelp} pane, it will show help window when clicked.
     * @return {@code controlHelp} pane, to be used to bind accelerator by {@link MainWindow}
     */
    protected Pane getControlHelp() {
        return controlHelp;
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp(MouseEvent event) {
        if (MouseButton.PRIMARY.equals(event.getButton())) {
            HelpWindow helpWindow = new HelpWindow();
            helpWindow.show();
        }
    }

    /**
     * Minimizes the application.
     */
    @FXML
    private void handleMinimize(MouseEvent event) {
        if (MouseButton.PRIMARY.equals(event.getButton())) {
            raise(new MinimizeAppRequestEvent());
        }
    }

    /**
     * Maximizes the application.
     */
    @FXML
    private void handleMaximize(MouseEvent event) {
        if (MouseButton.PRIMARY.equals(event.getButton())) {
            raise(new MaximizeAppRequestEvent());
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit(MouseEvent event) {
        if (MouseButton.PRIMARY.equals(event.getButton())) {
            raise(new ExitAppRequestEvent());
        }
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }
}
