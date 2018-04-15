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
import seedu.address.commons.core.CoinSubredditList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CoinPanelSelectionChangedEvent;
import seedu.address.model.coin.Coin;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SUBREDDIT_NOT_FOUND = "SubredditNotFound.html";
    public static final String USER_AGENT_STRING = "Mozilla/5.0 (Linux; Android 4.4.2; Nexus 4 Build/KOT49H) "
            + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.114 Mobile Safari/537.36";

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

        browser.getEngine().setUserAgent(USER_AGENT_STRING);
    }

    /**
     * Loads the respective {@code coin} subreddit page. Loads a default page if there is not one
     * @param coin specifies the subreddit page to load
     */
    private void loadCoinPage(Coin coin) {
        String url;
        if (CoinSubredditList.isRecognized(coin)) {
            url = CoinSubredditList.getRedditUrl(coin);
        } else {
            url = MainApp.class.getResource(FXML_FILE_FOLDER + SUBREDDIT_NOT_FOUND).toExternalForm();
        }
        loadPage(url);
    }

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
    private void handleCoinPanelSelectionChangedEvent(CoinPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCoinPage(event.getNewSelection().coin);
    }
}
