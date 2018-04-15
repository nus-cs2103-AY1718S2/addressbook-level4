package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowCalendarEvent;

//@@author wayneong95

/**
 * Controller for a Calendar Window.
 */
public class CalendarWindow extends UiPart<Region> {

    public static final String CALENDAR_PAGE_URL =
            "https://calendar.google.com/calendar";

    private static final Logger logger = LogsCenter.getLogger(CalendarWindow.class);
    private static final String FXML = "CalendarWindow.fxml";

    @FXML
    private WebView calendar;

    /**
     * Creates a new Calendar Window.
     */

    public CalendarWindow() {
        super(FXML);
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadCalendarPage();
        registerAsAnEventHandler(this);
    }

    private void loadCalendarPage() {
        loadPage(CALENDAR_PAGE_URL);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> calendar.getEngine().load(url));
    }

    /**
     * Frees resources allocated to the Google calendar.
     */
    public void freeResources() {
        calendar = null;
    }

    @Subscribe
    private void showCalendarEvent (ShowCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(CALENDAR_PAGE_URL);
    }
}
