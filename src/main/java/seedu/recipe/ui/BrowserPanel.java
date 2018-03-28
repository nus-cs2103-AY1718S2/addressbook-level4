package seedu.recipe.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.recipe.MainApp;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.events.ui.RecipePanelSelectionChangedEvent;
import seedu.recipe.commons.events.ui.ShareRecipeEvent;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.util.CloudStorageUtil;
import seedu.recipe.ui.util.FacebookHandler;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    private static final String FXML = "BrowserPanel.fxml";

    private Recipe recipeToShare;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);

        setUpBrowserUrlListener();
    }

    private void loadRecipePage(Recipe recipe) {
        loadPage(recipe.getUrl().toString());
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
    private void handleRecipePanelSelectionChangedEvent(RecipePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadRecipePage(event.getNewSelection().recipe);
    }

    //@@author RyanAngJY
    @Subscribe
    private void handleShareRecipeEvent(ShareRecipeEvent event) {
        loadPage(FacebookHandler.getAuthenticationUrl());
        recipeToShare = event.getTargetRecipe();
        if (FacebookHandler.hasAccessToken()) {
            FacebookHandler.postRecipeOnFacebook(recipeToShare);
        }
    }

    /**
     * Sets up a URL listener on the browser to watch for access token.
     */
    private void setUpBrowserUrlListener() {
        WebEngine browserEngine = browser.getEngine();
        browserEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    String url = browserEngine.getLocation();

                    if (FacebookHandler.checkAndSetAccessToken(url)) {
                        FacebookHandler.postRecipeOnFacebook(recipeToShare);
                    }
                }
            }
        });
    }
    //@@author
/*
    //@@author nicholasangcx
    @Subscribe
    private void handleDbAuthorizationEvent(DbAuthorizationEvent event) {
        loadPage(CloudStorageUtil.getAuthorizationUrl())
    }
    */
}
