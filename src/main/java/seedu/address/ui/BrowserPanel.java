package seedu.address.ui;

import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.smplatform.Link;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String FACEBOOK_SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String TWITTER_SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";
    private static final String FACEBOOK_TAB_ID = "facebookTab";
    private static final String TWITTER_TAB_ID = "twitterTab";
    private static final String FUNCTION_ADD = "add";
    private static final String FUNCTION_REMOVE = "remove";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Set<String> openTabIdSet;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab facebookTab;

    @FXML
    private Tab twitterTab;

    @FXML
    private WebView browser;

    @FXML
    private WebView browser1;

    public BrowserPanel() {
        super(FXML);

        openTabIdSet = tabPane.getTabs().stream().map(tab -> tab.getId()).collect(Collectors.toSet());

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Updates the display of social media browser tabs.
     *
     * @param function defines an add or remove function
     * @param tabId id of the fxml tab
     */
    private void updateBrowserTabs(String function, String tabId) {
        switch (function) {
            case FUNCTION_ADD:
                addBrowserTab(tabId);
                break;
            case FUNCTION_REMOVE:
                removeBrowserTab(tabId);
                break;
        }
    }

    /**
     * Adds the specified browser tab to the UI using the {@code tabId} if it is not open;
     */
    private void addBrowserTab(String tabId) {
        if (!openTabIdSet.contains(tabId)) {
            openTabIdSet.add(tabId);
            switch (tabId) {
                case FACEBOOK_TAB_ID:
                    tabPane.getTabs().add(0, facebookTab);
                    break;
                case TWITTER_TAB_ID:
                    tabPane.getTabs().add(twitterTab);
                    break;
            }
        }
    }

    /**
     * Removes the specified browser tab from the UI using the {@code tabId};
     */
    private void removeBrowserTab(String tabId) {
        openTabIdSet.remove(tabId);
        switch (tabId) {
            case FACEBOOK_TAB_ID:
                tabPane.getTabs().remove(facebookTab);
                break;
            case TWITTER_TAB_ID:
                tabPane.getTabs().remove(twitterTab);
                break;
        }
    }

    /**
     * Returns the given {@code url} with a protocol if unspecified.
     */
    private String parseUrl(String url) {
        if (!url.contains("://")) {
            return "https://" + url;
        }

        return url;
    }

    private void loadBrowserPersonPage(Person person) {
        if (person.getSocialMediaPlatformMap().containsKey(Link.FACEBOOK_LINK_TYPE)) {
            updateBrowserTabs(FUNCTION_ADD, FACEBOOK_TAB_ID);
            String url = person.getSocialMediaPlatformMap().get(Link.FACEBOOK_LINK_TYPE).getLink().value;
            loadBrowserPage(parseUrl(url));
        } else {
            updateBrowserTabs(FUNCTION_REMOVE, FACEBOOK_TAB_ID);
            loadBrowserPage(FACEBOOK_SEARCH_PAGE_URL + person.getName().fullName);
        }
    }

    private void loadBrowser1PersonPage(Person person) {
        if (person.getSocialMediaPlatformMap().containsKey(Link.TWITTER_LINK_TYPE)) {
            updateBrowserTabs(FUNCTION_ADD, TWITTER_TAB_ID);
            String url = person.getSocialMediaPlatformMap().get(Link.TWITTER_LINK_TYPE).getLink().value;
            loadBrowser1Page(parseUrl(url));
            System.out.println("Link: " + browser1.getEngine().getLocation());
        } else {
            updateBrowserTabs(FUNCTION_REMOVE, TWITTER_TAB_ID);
            loadBrowser1Page(TWITTER_SEARCH_PAGE_URL + person.getName().fullName);
        }
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
        loadBrowserPersonPage(event.getNewSelection().person);
        loadBrowser1PersonPage(event.getNewSelection().person);
    }
}
