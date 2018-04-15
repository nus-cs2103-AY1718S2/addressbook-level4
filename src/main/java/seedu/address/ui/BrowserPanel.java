package seedu.address.ui;

import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.SearchPersonEvent;
import seedu.address.commons.events.ui.ShowLoginDialogRequestEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;
import seedu.address.model.smplatform.Facebook;
import seedu.address.model.smplatform.Link;
import seedu.address.model.smplatform.Twitter;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String LOADING_PAGE_URL = "https://cs2103jan2018-f12-b3.github.io/main/LoadingPage.html";
    public static final String FACEBOOK_SEARCH_PAGE_URL =
            "https://www.facebook.com/search/people?q=";
    public static final String TWITTER_SEARCH_PAGE_URL =
            "https://twitter.com/search?f=users&vertical=news&q=";

    private static final String SUCCESS_URL = "https://www.facebook.com/connect/login_success.html";

    private static final String FXML = "BrowserPanel.fxml";
    private static final String FACEBOOK_TAB_ID = "facebookTab";
    private static final String TWITTER_TAB_ID = "twitterTab";
    private static final String FUNCTION_ADD = "add";
    private static final String FUNCTION_REMOVE = "remove";

    private URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + "default.html");

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;

    private Set<String> openTabIdSet;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab facebookTab;

    @FXML
    private Tab twitterTab;

    @FXML
    private WebView facebookBrowser;

    @FXML
    private WebView twitterBrowser;

    public BrowserPanel(Logic logic) {
        super(FXML);
        this.logic = logic;

        //@@author Nethergale
        openTabIdSet = tabPane.getTabs().stream().map(tab -> tab.getId()).collect(Collectors.toSet());

        //@@author
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    //@@author Nethergale
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

        default:
            // Do nothing
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

            default:
                // Do nothing
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

        default:
            //Do nothing
        }
    }

    /**
     * Returns the given {@code url} with a protocol and subdomain if unspecified.
     */
    public static String parseUrl(String url) {
        if (!url.contains("://")) {
            if (!url.contains("www")) {
                return "https://www." + url;
            }
            return "https://" + url;
        } else {
            if (!url.contains("www")) {
                String[] splitUrl = url.split("://");
                return "https://www." + splitUrl[1];
            }
        }

        return url;
    }

    /**
     * Loads the Facebook profile page for the browser on the Facebook tab if it exists.
     */
    private void loadFacebookBrowserProfilePage(Person person) {
        if (person.getSocialMediaPlatformMap().containsKey(Link.FACEBOOK_LINK_TYPE)) {
            updateBrowserTabs(FUNCTION_ADD, FACEBOOK_TAB_ID);
            String url = person.getSocialMediaPlatformMap().get(Link.FACEBOOK_LINK_TYPE).getLink().value;
            loadFacebookBrowserPage(parseUrl(url));
        } else {
            updateBrowserTabs(FUNCTION_REMOVE, FACEBOOK_TAB_ID);
            loadFacebookBrowserPage(defaultPage.toExternalForm());
        }
    }

    /**
     * Loads the Twitter profile page for the browser on the Twitter tab if it exists.
     */
    private void loadTwitterBrowserProfilePage(Person person) {
        if (person.getSocialMediaPlatformMap().containsKey(Link.TWITTER_LINK_TYPE)) {
            updateBrowserTabs(FUNCTION_ADD, TWITTER_TAB_ID);
            String url = person.getSocialMediaPlatformMap().get(Link.TWITTER_LINK_TYPE).getLink().value;
            loadTwitterBrowserPage(parseUrl(url));
        } else {
            updateBrowserTabs(FUNCTION_REMOVE, TWITTER_TAB_ID);
            loadTwitterBrowserPage(defaultPage.toExternalForm());
        }
    }

    //@@author KevinChuangCH
    private void loadBrowserSearchPage(String searchName) {
        loadFacebookBrowserPage(FACEBOOK_SEARCH_PAGE_URL + searchName);
    }
    private void loadBrowser1SearchPage(String searchName) {
        loadTwitterBrowserPage(TWITTER_SEARCH_PAGE_URL + searchName);
    }

    //@@author
    public void loadFacebookBrowserPage(String url) {
        Platform.runLater(() -> facebookBrowser.getEngine().load(url));
    }
    public void loadTwitterBrowserPage(String url) {
        Platform.runLater(() -> twitterBrowser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadFacebookBrowserPage(defaultPage.toExternalForm());
        loadTwitterBrowserPage(defaultPage.toExternalForm());
    }

    /**
     * Passes a verification code when the login is successful.
     */
    private void passVerificationCode() {
        facebookBrowser.getEngine().getLoadWorker().stateProperty().addListener((
                ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
            if (newValue != Worker.State.SUCCEEDED) {
                return;
            }

            String currentUrl = facebookBrowser.getEngine().getLocation();

            if (currentUrl.endsWith(DEFAULT_PAGE)) {
            } else if (currentUrl.startsWith(SUCCESS_URL)) {
                int pos = currentUrl.indexOf("code=");
                logic.passVerificationCode(currentUrl.substring(pos + "code=".length()));
            }
        });
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        facebookBrowser = null;
        twitterBrowser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadFacebookBrowserProfilePage(event.getNewSelection().person);
        loadTwitterBrowserProfilePage(event.getNewSelection().person);
    }

    //@@author KevinChuangCH
    @Subscribe
    private void handleSearchPersonEvent(SearchPersonEvent event) {

        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        String platformToSearch = event.getPlatform();

        if (StringUtil.containsWordIgnoreCase(platformToSearch, Facebook.PLATFORM_KEYWORD)
                || StringUtil.containsWordIgnoreCase(platformToSearch, Facebook.PLATFORM_ALIAS)) {
            updateBrowserTabs(FUNCTION_ADD, FACEBOOK_TAB_ID);
            updateBrowserTabs(FUNCTION_REMOVE, TWITTER_TAB_ID);
            loadBrowserSearchPage(event.getSearchName());
            loadTwitterBrowserPage(defaultPage.toExternalForm());
        } else if (StringUtil.containsWordIgnoreCase(platformToSearch, Twitter.PLATFORM_KEYWORD)
                || StringUtil.containsWordIgnoreCase(platformToSearch, Twitter.PLATFORM_ALIAS)) {
            updateBrowserTabs(FUNCTION_ADD, TWITTER_TAB_ID);
            updateBrowserTabs(FUNCTION_REMOVE, FACEBOOK_TAB_ID);
            loadFacebookBrowserPage(defaultPage.toExternalForm());
            loadBrowser1SearchPage(event.getSearchName());
        } else {
            updateBrowserTabs(FUNCTION_ADD, FACEBOOK_TAB_ID);
            updateBrowserTabs(FUNCTION_ADD, TWITTER_TAB_ID);
            loadBrowserSearchPage(event.getSearchName());
            loadBrowser1SearchPage(event.getSearchName());
        }
    }

    @Subscribe
    private void handleShowLoginDialogRequestEvent(ShowLoginDialogRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadFacebookBrowserPage(event.loadUrl);
        passVerificationCode();
    }
}
