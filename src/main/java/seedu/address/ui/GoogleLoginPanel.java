package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoadGoogleLoginEvent;
import seedu.address.commons.events.ui.LoadGoogleLoginRedirectEvent;

//@@author KevinCJH

/**
 * The Google Login Panel of the App.
 */
public class GoogleLoginPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "https://www.google.com/";

    private static final String FXML = "GoogleLoginPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView loginbrowser;

    public GoogleLoginPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> loginbrowser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        loginbrowser = null;
    }

    @Subscribe
    private void loadLoginUrl(LoadGoogleLoginEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(event.getAuthenticationUrl());
    }

    @Subscribe
    private void getRedirectUrlEvent (LoadGoogleLoginRedirectEvent event) {
        logger.info((LogsCenter.getEventHandlingLogMessage(event)));
        event.setRedirectUrl(loginbrowser.getEngine().getLocation());
    }
}
