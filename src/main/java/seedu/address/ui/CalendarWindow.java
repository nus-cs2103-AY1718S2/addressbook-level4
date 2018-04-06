//@@author ifalluphill

package seedu.address.ui;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * The CalendarWindow Window. Provides the basic window generation,
 * content population, and rendering.
 */
public class CalendarWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ErrorsWindow.class);
    private static final String FXML = "CalendarWindow.fxml";

    @FXML
    private WebView browser;


    /**
     * Creates a new CalendarWindow (Overload).
     *
     * @param root Stage to use as the root of the CalendarWindow.
     */
    public CalendarWindow(Stage root, Logic logic) throws IOException {
        super(FXML, root);
        WebEngine engine = browser.getEngine();

        if (logic.hasLoggedIn()) {
            String googleCalendarLink = "https://calendar.google.com/calendar/r";
            URI uri = URI.create(googleCalendarLink);
            Map<String, List<String>> headers = new LinkedHashMap<>();
            headers.put("Set-Cookie", Arrays.asList("name=value"));
            java.net.CookieHandler.getDefault().put(uri, headers);
            engine.setUserAgent(engine.getUserAgent().replace("Macintosh; ", ""));
            engine.load(googleCalendarLink);
        } else {
            browser.getEngine().loadContent("<html>You must be logged in to use the calendar feature.</html>",
                    "text/Html");
        }

    }

    /**
     * Creates a new CalendarWindow.
     */
    public CalendarWindow(Logic logic) throws IOException {
        this(new Stage(), logic);
    }

    /**
     * Shows the calendar window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing calendar window.");
        getRoot().show();
    }
}

//@@author

