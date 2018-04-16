package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

//@@author x3tsunayh

/**
 * The Browser Panel of the App.
 * The default page is Google's home page, but can also be used to
 * google for contacts selected by the Select Command.
 */
public class BrowserPanel extends UiPart<Stage> {

    public static final String GOOGLE_URL = "https://www.google.com.sg/";
    public static final String SEARCH_PAGE_URL =
            "https://www.google.com.sg/search?q=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    /**
     * Creates a new Google Search page.
     *
     * @param root Stage to use as the root of the GoogleSearch.
     */
    public BrowserPanel(Stage root, Person person) {
        super(FXML, root);
        if (person != null) {
            this.loadPersonPage(person);
        } else {
            this.loadPage(GOOGLE_URL);
        }
        registerAsAnEventHandler(this);
    }

    public BrowserPanel(Person person) {
        this(new Stage(), person);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    public void loadPage(String url) {
        browser.getEngine().load(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    /**
     * Shows the GoogleSearch window.
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
        logger.fine("Showing Google Search page.");
        getRoot().show();
    }
}
