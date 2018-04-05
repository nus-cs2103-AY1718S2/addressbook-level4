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
import seedu.address.commons.events.ui.PersonPanelPathChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_MAP_SEARCH_PAGE =
            "https://www.google.com.sg/maps/search/";
    public static final String GOOGLE_MAP_PATH_SEARCH_PAGE =
            "https://www.google.com.sg/maps/dir/";

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
    //@@author ZhangYijiong
    private void loadGoogleMapAddressPage(Person person) {
        loadPage(GOOGLE_MAP_SEARCH_PAGE + person.getAddress().getGoogleMapSearchForm());
    }
    //@@author ZhangYijiong
    private void loadGoogleMapPathPage(Person person) {
        loadPage(GOOGLE_MAP_PATH_SEARCH_PAGE + Address.ADDRESS_USER_OWN
                + "/" + person.getAddress().getGoogleMapSearchForm());
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

    //@@author ZhangYijiong
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGoogleMapAddressPage(event.getNewSelection().person);
    }

    //@@author ZhangYijiong
    @Subscribe
    private void handlePersonPanelPathChangedEvent(PersonPanelPathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGoogleMapPathPage(event.getNewSelection().person);
    }
}
