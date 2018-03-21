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
import seedu.recipe.ui.util.FacebookHandler;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String REDIRECT_DOMAIN = "https://www.facebook.com/connect/login_success.html";
    private static final String FXML = "BrowserPanel.fxml";

    //@@author RyanAngJY
    private final String APP_ID = "177615459696708";
    private final String ACCESS_RIGHTS = "publish_actions";


    /*  private final String AUTH_URL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + APP_ID + "&redirect_uri=" + DOMAIN + "&scope=user_about_me,"
            + "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,user_education_history,"
            + "user_events,user_photos,user_friends,user_games_activity,user_hometown,user_likes,user_location,user_photos,user_relationship_details,"
            + "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
            + "manage_pages,publish_actions,read_insights,read_page_mailboxes,rsvp_event";
*/

    private final String AUTHENTICATION_URL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="
            + APP_ID + "&redirect_uri=" + REDIRECT_DOMAIN + "&scope=" + ACCESS_RIGHTS;
    // https://www.facebook.com/v2.12/dialog/oauth?client_id=615711762098255&redirect_uri=https://meusicate.herokuapp.com/&state={}

    private Recipe recipeToShare;
    //@@author

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final FacebookHandler facebookHandler = new FacebookHandler();

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
        loadPage(AUTHENTICATION_URL);
        recipeToShare = event.getTargetRecipe();
        if (facebookHandler.hasAccessToken()) {
            facebookHandler.postRecipeOnFacebook(recipeToShare);
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

                    if (facebookHandler.checkAndSetAccessToken(url)) {
                        facebookHandler.postRecipeOnFacebook(recipeToShare);
                    }
                }
            }
        });
    }
    //@@author
}
