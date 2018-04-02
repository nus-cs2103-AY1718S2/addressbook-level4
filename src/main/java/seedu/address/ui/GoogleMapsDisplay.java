package seedu.address.ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import seedu.address.MainApp;

import java.net.URL;

public class GoogleMapsDisplay extends UiPart<Region> {
    public static final String DEFAULT_PAGE = "default.html";
    public static final String MAP_URL_PREFIX = "https://www.google.com/maps/dir/";
    private static final String FXML = "GoogleMapsDisplay.fxml";

    @FXML
    private WebView maps;

    public GoogleMapsDisplay() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
    }

    public void loadMapPage(String locations) {
        loadPage(MAP_URL_PREFIX + locations);
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
