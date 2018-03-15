package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class BrowserWindow extends UiPart<Stage> {

    public static final String USERGUIDE_FILE_PATH = "/docs/UserGuide.html";

    private static final Logger logger = LogsCenter.getLogger(BrowserWindow.class);
    private static final String FXML = "BrowserWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new BrowserWindow.
     *
     * @param root Stage to use as the root of the BrowserWindow.
     */
    public BrowserWindow(Stage root, String url) {
        super(FXML, root);

        browser.getEngine().load(url);
    }

    /**
     * Creates a new BrowserWindow.
     */
    public BrowserWindow(String url) {
        this(new Stage(), url);
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing help page about the application.");
        getRoot().show();
    }
}
