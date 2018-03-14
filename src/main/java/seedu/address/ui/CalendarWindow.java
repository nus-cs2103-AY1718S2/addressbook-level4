//@@author {ifalluphill}

package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;


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
    public CalendarWindow(Stage root) {
        super(FXML, root);

        browser.getEngine().loadContent("<html><h1>Hello World</h1></html>", "text/Html");
    }

    /**
     * Creates a new CalendarWindow.
     */
    public CalendarWindow() {
        this(new Stage());
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
        logger.fine("Showing calendar window within the application.");
        getRoot().show();
    }
}

//@@author

