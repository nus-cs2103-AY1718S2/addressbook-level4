package seedu.progresschecker.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.progresschecker.MainApp;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.ui.LoadBarEvent;

/**
 * The second Browser Panel of the App.
 */
public class Browser2Panel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

    private static final String FXML = "Browser2Panel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser2;

    public Browser2Panel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser2.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    //@@author EdwardKSG
    /**
     * Loads the HTML file which contains task information.
     */
    public void loadBarPage(String content) {
        loadPageViaString(content);
    }

    public void loadPageViaString(String content) {
        Platform.runLater(() -> browser2.getEngine().loadContent(content));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser2 = null;
    }

    @Subscribe
    private void handleLoadBarEvent(LoadBarEvent event)  {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadBarPage(event.getContent());
    }
    //@@author
}

