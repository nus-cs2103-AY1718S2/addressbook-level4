package seedu.recipe.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

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

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    //@@author RyanAngJY
    private final String APP_ID = "177615459696708";
    private final String DOMAIN = "https://www.facebook.com/connect/login_success.html";

    // https://www.facebook.com/v2.12/dialog/oauth?client_id=615711762098255&redirect_uri=https://meusicate.herokuapp.com/&state={}
    private final String AUTH_URL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + APP_ID + "&redirect_uri=" + DOMAIN + "&scope=user_about_me,"
            + "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,user_education_history,"
            + "user_events,user_photos,user_friends,user_games_activity,user_hometown,user_likes,user_location,user_photos,user_relationship_details,"
            + "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
            + "manage_pages,publish_actions,read_insights,read_page_mailboxes,rsvp_event,publish_actions";

    private String accessToken = null;
    private Recipe recipeToShare;
    //@@author

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
        loadPage(AUTH_URL);
        recipeToShare = event.getTargetRecipe();
        System.out.println("recipe to share: " + recipeToShare.getName());
        if (accessToken != null) {
            postRecipeOnFacebook();
        }
    }

    private void setUpBrowserUrlListener() {
        WebEngine browserEngine = browser.getEngine();
        browserEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    String url = browserEngine.getLocation();
                    System.out.println("Url: " + url); //NEW URL

                    if (url.contains("#access_token=")) {
                        accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
                        System.out.println("Access Token: " + accessToken);
                        postRecipeOnFacebook();
                    }
                }
            }
        });
    }

    /**
     * Posts a recipe directly onto Facebook.
     */
    private void postRecipeOnFacebook() {
        System.out.println("In PostRecipeOnFacebook recipe to share: " + recipeToShare.getName());
        Version apiVersion = Version.VERSION_2_12;
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, apiVersion);
        User me = fbClient.fetchObject("me", User.class);

        FacebookType post =  fbClient.publish("me/feed", FacebookType.class,
                Parameter.with("message", recipeToShare.getName().toString()));

        System.out.println(me.getName());
    }
    //@@author
}
