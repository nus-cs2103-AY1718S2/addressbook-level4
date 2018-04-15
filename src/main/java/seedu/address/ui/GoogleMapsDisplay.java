package seedu.address.ui;

import java.net.URL;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;

//@@author jingyinno
/**
 * A ui for the info panel that is displayed when the map command is called.
 */

public class GoogleMapsDisplay extends UiPart<Region> {
    public static final String DEFAULT_PAGE = "default.html";
    public static final String MAP_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/";
    public static final String MAP_DIRECTIONS_URL_PREFIX = "https://www.google.com/maps/dir/";
    private static final String FXML = "GoogleMapsDisplay.fxml";

    @FXML
    private WebView maps;

    public GoogleMapsDisplay() {
        this(null);
    }

    public GoogleMapsDisplay(String locations) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultPage();
    }

    /**
     * Loads the Google Maps page with the location.
     */
    public void loadMapPage(String location) {
        String address = MAP_SEARCH_URL_PREFIX + location;
        loadPage(address);
    }

    /**
     * Loads the Google Maps page with the directions between locations.
     */
    public void loadMapDirections(String locations) {
        String address = MAP_DIRECTIONS_URL_PREFIX + locations;
        loadPage(address);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> maps.getEngine().load(url));
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
        maps = null;
    }
}
