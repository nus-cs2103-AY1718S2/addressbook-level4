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
import seedu.address.commons.events.ui.ShowMapRequestEvent;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String ATM_SEARCH_PAGE_URL = "https://www.google.com.sg/maps/search/atm+near+me/";
    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String ADDRESS_SEARCH_PAGE_URL = "https://www.google.com.sg/maps/search/";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadAtmSearchPage();
        registerAsAnEventHandler(this);
    }

    //@@author Articho28
    public void loadAtmSearchPage() {
        loadPage(ATM_SEARCH_PAGE_URL);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }
    private void loadPersonAddress(Person person) {
        loadPage (ADDRESS_SEARCH_PAGE_URL + person.getAddress().value);
    }
    //@@author
    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }


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
        loadPersonAddress(event.getNewSelection().person);
    }
    //@@author Articho28
    @Subscribe
    private void handleShowMapRequestEvent(ShowMapRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadAtmSearchPage();
    }

}
