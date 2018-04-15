package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a browser page
 */
public class BrowserWindow extends UiPart<Stage> {

    public static final String USERGUIDE_FILE_PATH = "/docs/UserGuide.html";

    private static final Logger logger = LogsCenter.getLogger(BrowserWindow.class);
    private static final Image HELP_ICON = new Image("/images/help_icon.png");
    private static final Image BROWSER_ICON = new Image("/images/address_book_32.png");
    private static final String HELP_TITLE = "Help";
    private static final String BROWSER_TITLE = "Browser";
    private static final String FXML = "BrowserWindow.fxml";
    private static final int ICON_INDEX = 0;

    @FXML
    private WebView browser;

    /**
     * Creates a new BrowserWindow.
     *
     * @param root Stage to use as the root of the BrowserWindow.
     */
    public BrowserWindow(Stage root, String url) {
        super(FXML, root);
        String loadUrl;
        setBrowserTopBar(url);
        browser.getEngine().load(getBrowserUrl(url));
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

    /**
     * Sets the the top bar of the BrowserWindow based on the URL
     * @param url input link
     */
    private void setBrowserTopBar(String url) {
        if (url == USERGUIDE_FILE_PATH) {
            this.getRoot().getIcons().add(ICON_INDEX, HELP_ICON);
            this.getRoot().setTitle(HELP_TITLE);
        } else {
            this.getRoot().getIcons().add(ICON_INDEX, BROWSER_ICON);
            this.getRoot().setTitle(BROWSER_TITLE);
        }
    }

    /**
     * @param url input link
     * @return The URL link that the browser should open
     */
    private String getBrowserUrl(String url) {
        if (url == USERGUIDE_FILE_PATH) {
            return getClass().getResource(USERGUIDE_FILE_PATH).toString();
        } else {
            return url;
        }
    }
}
