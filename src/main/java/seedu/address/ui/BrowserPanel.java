package seedu.address.ui;

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
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    /*public static final String CALCULATOR_PREFIX_URL = "http://www.calculator.net/calorie-calculator.html?ctype=metric";

    public static final String CALCULATOR_AGE_PREFIX = "&cage=";

    public static final String CALCULATOR_GENDER_PREFIX = "&csex=";

    public static final String CALCULATOR_HEIGHT_PREFIX = "&cheightfeet=5&cheightinch=10&cpound=160&cheightmeter=";

    public static final String CALCULATOR_WEIGHT_PREFIX = "&ckg=";

    public static final String CALCULATOR_ACTIVITY_LEVEL_PREFIX = "&cactivity=";

    public static final String CALCULATOR_SUFFIX_URL = "&printit=0";*/

    private static final String FXML = "BrowserPanel.fxml";

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

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /*private void loadCalculatorPage(Person person){ loadPage(CALCULATOR_PREFIX_URL + CALCULATOR_AGE_PREFIX + "22" +
    CALCULATOR_GENDER_PREFIX + person.getGender() + CALCULATOR_HEIGHT_PREFIX + person.getHeight()
            + CALCULATOR_WEIGHT_PREFIX +
    person.getWeight() + CALCULATOR_ACTIVITY_LEVEL_PREFIX + "1.375" + CALCULATOR_SUFFIX_URL);}*/

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
