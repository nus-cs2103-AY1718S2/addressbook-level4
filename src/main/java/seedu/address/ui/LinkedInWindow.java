package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

//@@author x3tsunayh

/**
 * Controller for a LinkedIn page
 */
public class LinkedInWindow extends UiPart<Stage> {

    public static final String LINKEDIN_URL = "https://www.linkedin.com/";

    private static final Logger logger = LogsCenter.getLogger(LinkedInWindow.class);
    private static final String FXML = "LinkedInWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new LinkedIn page.
     *
     * @param root Stage to use as the root of the LinkedIn.
     */
    public LinkedInWindow(Stage root) {
        super(FXML, root);
        browser.getEngine().load(LINKEDIN_URL);
    }

    /**
     * Creates a new LinkedInWindow.
     */
    public LinkedInWindow() {
        this(new Stage());
    }

    /**
     * Shows the LinkedIn window.
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
        logger.fine("Showing the LinkedIn page.");
        getRoot().show();
    }
}
