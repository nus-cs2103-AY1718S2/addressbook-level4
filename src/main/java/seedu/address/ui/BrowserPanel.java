package seedu.address.ui;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.handleCalendarViewChangedEvent;
import seedu.address.model.calendar.GoogleCalendar;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String CALENDAR_PAGE_URL = "https://calendar.google.com/calendar/embed?src=";

    private static final String FXML =
            "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Load Google Calendar View
     */
    private void loadDefaultPage() {
        GoogleCalendar googleCalendar = new GoogleCalendar();
        try {
            String calendarId = googleCalendar.getCalendarId();
            loadPage(CALENDAR_PAGE_URL + calendarId);
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be creating new google calendar");
            URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
            loadPage(defaultPage.toExternalForm());
        }
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }
    @Subscribe
    private void handleCalenderViewChangedEvent(handleCalendarViewChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDefaultPage();
    }
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDefaultPage();
        //loadPersonPage(event.getNewSelection().person);
    }
}
