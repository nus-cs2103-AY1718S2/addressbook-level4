package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;
import java.util.ResourceBundle;


import com.google.common.eventbus.Subscribe;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

//@@author jaronchan
/**
 * The Person Details Panel of the App.
 * To be UPDATED
 */
public class PersonDetailsPanel extends UiPart<Region> implements Initializable, MapComponentInitializedListener {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "PersonDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    public PersonDetailsPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

//        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

//    private void loadPersonPage(Person person) {
//        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
//    }
//
//    public void loadPage(String url) {
//        Platform.runLater(() -> browser.getEngine().load(url));
//    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);
    }

//    /**
//     * Loads a default HTML file with a background that matches the general theme.
//     */
//    private void loadDefaultPage() {
//        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
//        loadPage(defaultPage.toExternalForm());
//    }
//
    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        map = null;
    }
//
//    @Subscribe
//    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        loadPersonPage(event.getNewSelection().person);
//    }
}
