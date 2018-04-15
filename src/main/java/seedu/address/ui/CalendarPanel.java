package seedu.address.ui;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;

//@@author jaronchan
/**
 * The Person Details Panel of the App.
 * To be UPDATED
 */
public class CalendarPanel extends UiPart<Region> {

    public static final String CALENDAR_URL = "https://calendar.google.com/calendar/r";
    public static final String LOGOUT_URL = "https://accounts.google.com/Logout?&continue=" + CALENDAR_URL;

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public CalendarPanel() throws IOException {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    //@@author ifalluphill
    /**
     * Loads the Google Calendar WebView within the appropriate scene.
     */
    private void loadDefaultPage() throws IOException {
        WebEngine engine = browser.getEngine();
        URI uri = URI.create(CALENDAR_URL);
        Map<String, List<String>> headers = new LinkedHashMap<>();
        headers.put("Set-Cookie", Arrays.asList("name=value"));
        java.net.CookieHandler.getDefault().put(uri, headers);
        engine.setUserAgent(engine.getUserAgent().replace("Macintosh; ", ""));
        Platform.runLater(() -> browser.getEngine().load(CALENDAR_URL));
    }

    /**
     * Resets the Google Calendar WebView scene.
     */
    public void reloadDefaultPage() throws IOException {
        WebEngine engine = browser.getEngine();
        URI uri = URI.create(LOGOUT_URL);
        Map<String, List<String>> headers = new LinkedHashMap<>();
        headers.put("Set-Cookie", Arrays.asList("name=value"));
        java.net.CookieHandler.getDefault().put(uri, headers);
        engine.setUserAgent(engine.getUserAgent().replace("Macintosh; ", ""));
        Platform.runLater(() -> browser.getEngine().load(LOGOUT_URL));
    }

    //@@author jaronchan

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }
}
