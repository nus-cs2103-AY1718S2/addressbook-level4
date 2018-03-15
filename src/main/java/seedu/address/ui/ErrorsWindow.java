//@@author {ifalluphill}

package seedu.address.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * The ErrorWindow Window. Provides the basic window generation,
 * layout, content population, and rendering.
 */
public class ErrorsWindow extends UiPart<Stage> {

    private static final String ERROR_LOG_FILE_PATH = "./addressbook.log.0";
    private static final Logger logger = LogsCenter.getLogger(ErrorsWindow.class);
    private static final String FXML = "ErrorsWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new ErrorsWindow (Overload).
     *
     * @param root Stage to use as the root of the ErrorsWindow.
     */
    public ErrorsWindow(Stage root) {
        super(FXML, root);

        String errorLog = getErrorLogAsHtmlString();
        String errorWindowHtml = createErrorLogPageAsHtmlString(errorLog);
        browser.getEngine().loadContent(errorWindowHtml, "text/Html");
    }

    /**
     * Creates a new ErrorsWindow.
     */
    public ErrorsWindow() {
        this(new Stage());
    }

    /**
     * Formats the error log for use in an Html page.
     */

    private String getErrorLogAsHtmlString() {
        StringBuilder errorLog = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(ERROR_LOG_FILE_PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorLog.append("<p>").append(line).append("</p>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return errorLog.toString();
    }

    /**
     * Generates the Html page content for the errors window.
     */

    private String createErrorLogPageAsHtmlString(String errorLog) {
        return  "<HTML>"
                    + "<style>"
                        + "body { background: #eee; }"
                        + "p { margin: 0; }"
                    + "</style>"
                    + "<body>"
                        + "<div>" + errorLog + "</div>"
                    + "</body>"
                + "</HTML>";
    }

    /**
     * Shows the error window.
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
        logger.fine("Showing error log within the application.");
        getRoot().show();
    }
}

//@@author
