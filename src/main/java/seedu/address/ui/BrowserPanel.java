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
import seedu.address.commons.events.ui.SearchPersonOnAllPlatformEvent;
import seedu.address.commons.events.ui.SearchPersonOnTwitterEvent;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String FACEBOOK_PROFILE_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String TWITTER_PROFILE_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String FACEBOOK_SEARCH_PAGE_URL =
            //"https://www.facebook.com/search/people?q=";
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String TWITTER_SEARCH_PAGE_URL =
            //"https://twitter.com/search?f=users&vertical=news&q=";
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    @FXML
    private WebView browser1;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadBrowserProfilePage(Person person) {
        loadBrowserPage(FACEBOOK_PROFILE_PAGE_URL + person.getName().fullName);
    }
    private void loadBrowser1ProfilePage(Person person) {
        loadBrowser1Page(TWITTER_PROFILE_PAGE_URL + person.getName().fullName);
    }

    private void loadBrowserSearchPage(String searchName) {
        loadBrowserPage(FACEBOOK_SEARCH_PAGE_URL + searchName);
    }
    private void loadBrowser1SearchPage(String searchName) {
        loadBrowser1Page(TWITTER_SEARCH_PAGE_URL + searchName);
    }

    public void loadBrowserPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }
    public void loadBrowser1Page(String url) {
        Platform.runLater(() -> browser1.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadBrowserPage(defaultPage.toExternalForm());
        loadBrowser1Page(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
        browser1 = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadBrowserProfilePage(event.getNewSelection().person);
        loadBrowser1ProfilePage(event.getNewSelection().person);
    }

    @Subscribe
    private void handleSearchPersonOnAllPlatformEvent(SearchPersonOnAllPlatformEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadBrowserSearchPage(event.getSearchName());
        loadBrowser1SearchPage(event.getSearchName());
    }

    @Subscribe
    private void handleSearchPersonOnTwitterEvent(SearchPersonOnTwitterEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadBrowserPage(defaultPage.toExternalForm());
        loadBrowser1SearchPage(event.getSearchName());
    }
}
