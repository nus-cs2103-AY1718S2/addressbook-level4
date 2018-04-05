# jaronchan
###### /resources/view/MainWindow.fxml
``` fxml
          <TabPane fx:id="featuresTabPane" tabClosingPolicy="UNAVAILABLE" xmlns:fx="http://javafx.com/fxml/1"
                   xmlns="http://javafx.com/javafx/9.0.4">
            <tabs>
              <Tab fx:id="detailsTab" text="Details">
                <content>
                  <StackPane fx:id="personDetailsPlaceholder" prefWidth="340">
                    <padding>
                      <Insets right="10" left="10"/>
                    </padding>
                  </StackPane>
                </content>
              </Tab>
              <Tab fx:id="calendarTab" text="Calendar">
                <content>
                  <StackPane fx:id="calendarPlaceholder" prefWidth="340">
                    <padding>
                      <Insets top="10" right="10" bottom="10" left="10"/>
                    </padding>
                  </StackPane>
                </content>
              </Tab>
              <Tab fx:id="dailySchedulerTab" text="Daily Scheduler">
                <content>
                  <StackPane fx:id="dailySchedulerPlaceholder" prefWidth="340">
                    <padding>
                      <Insets top="10" right="10" bottom="10" left="10"/>
                    </padding>
                  </StackPane>
                </content>
              </Tab>
            </tabs>
          </TabPane>
```
###### /resources/view/DailySchedulerPanel.fxml
``` fxml

<?import javafx.scene.control.TabPane?>
<?import javafx.scene.control.Tab?>
<StackPane xmlns="http://javafx.com/javafx/9.0.4" xmlns:fx="http://javafx.com/fxml/1">
  <children>
    <SplitPane dividerPositions="0.5">
      <items>
        <StackPane>
          <padding>
            <Insets right="5.0" />
          </padding>
          <children>
            <VBox prefHeight="-1.0" prefWidth="-1.0">
              <padding>
                <Insets top="10" right="10" bottom="10" left="10"/>
              </padding>
              <children>
                <Label text="Scheduled Session for DATE">
                  <padding>
                    <Insets bottom="5" left="5"/>
                  </padding>
                </Label>
                <ListView prefHeight="-1.0" prefWidth="-1.0" />
              </children>
            </VBox>
          </children>
        </StackPane>
        <StackPane prefHeight="150.0" prefWidth="200.0">
          <padding>
            <Insets left="5.0" />
          </padding>
          <children>
            <TabPane prefHeight="200.0" prefWidth="200.0" side="RIGHT" tabClosingPolicy="UNAVAILABLE">
              <tabs>
                <Tab text="  ">
                  <content>
                    <StackPane prefHeight="-1.0" prefWidth="-1.0">
                      <children>
                        <StackPane fx:id="directionPanelPlaceholder" prefWidth="-1.0">
                          <padding>
                            <Insets right="10" left="10"/>
                          </padding>
                        </StackPane>
                      </children>
                    </StackPane>
                  </content>
                </Tab>
              </tabs>
            </TabPane>
          </children>
        </StackPane>
      </items>
    </SplitPane>
  </children>
</StackPane>
```
###### /resources/view/MapPanel.fxml
``` fxml

<StackPane xmlns="http://javafx.com/javafx/9.0.4" xmlns:fx="http://javafx.com/fxml/1">
  <children>
    <GoogleMapView fx:id="mapView" prefHeight="-1.0" prefWidth="-1.0" />
    <Pane fx:id="invalidAddressOverlay" prefHeight="200.0" prefWidth="300.0" stylesheets="@Extensions.css">
      <StackPane.margin>
        <Insets bottom="20.0" left="20.0" right="20.0" top="20.0" />
      </StackPane.margin>
      <children>
        <VBox alignment="CENTER">
          <children>
            <Label id="invalidAddresseeName" fx:id="invalidAddressPersonName" alignment="CENTER" minHeight="-Infinity" minWidth="-Infinity" stylesheets="@Extensions.css" text="NAME" textAlignment="CENTER" />
            <Label id="invalidAddressMsg" alignment="CENTER" contentDisplay="CENTER" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" stylesheets="@Extensions.css" text="address cannot be found on Google Maps" wrapText="true">
              <font>
                <Font size="8.0" />
              </font>
            </Label>
          </children>
        </VBox>
      </children>
    </Pane>
  </children>
</StackPane>
```
###### /resources/view/PersonDetailsPanel.fxml
``` fxml

<StackPane xmlns="http://javafx.com/javafx/9.0.4" xmlns:fx="http://javafx.com/fxml/1">
  <children>
    <SplitPane dividerPositions="0.5">
      <items>
        <StackPane>
          <children>
            <SplitPane dividerPositions="0.5" orientation="VERTICAL" prefHeight="200.0" prefWidth="160.0">
              <items>
                <VBox prefHeight="200.0" prefWidth="100.0">
                  <children>
                    <GridPane gridLinesVisible="true">
                      <columnConstraints>
                        <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                        <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                      </columnConstraints>
                      <rowConstraints>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                      </rowConstraints>
                      <padding>
                        <Insets bottom="10.0" left="10.0" right="5.0" top="10.0" />
                      </padding>
                      <children>
                        <Label text="Name" />
                        <Label text="Phone Number" GridPane.rowIndex="1" />
                        <Label text="Email" GridPane.rowIndex="2" />
                        <Label text="Label" GridPane.columnIndex="1" />
                        <Label text="Label" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                        <Label text="Label" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                        <Label text="Address" GridPane.rowIndex="3" />
                        <Label text="Label" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                        <Label text="Condition" GridPane.rowIndex="4" />
                        <Label text="Label" GridPane.columnIndex="1" GridPane.rowIndex="4" />
                        <Label text="Priority" GridPane.rowIndex="5" />
                        <Label text="Label" GridPane.columnIndex="1" GridPane.rowIndex="5" />
                      </children>
                    </GridPane>
                  </children>
                </VBox>
                <StackPane prefHeight="-1.0" prefWidth="-1.0">
                  <children>
                    <StackPane fx:id="mapPanelPlaceholder" prefWidth="-1.0">
                      <padding>
                        <Insets right="10" left="10"/>
                      </padding>
                    </StackPane>
                  </children>
                </StackPane>
              </items>
            </SplitPane>
          </children>
          <padding>
            <Insets right="5.0" />
          </padding>
        </StackPane>
        <StackPane prefHeight="150.0" prefWidth="200.0">
          <padding>
            <Insets left="5.0" />
          </padding>
          <children>
            <VBox prefHeight="200.0" prefWidth="100.0">
              <children>
                <Label text="Session Reports">
                  <padding>
                    <Insets bottom="5.0" left="5.0" top="5.0" />
                  </padding>
                </Label>
                <ListView prefHeight="200.0" prefWidth="200.0">
                  <VBox.margin>
                    <Insets bottom="5.0" right="5.0" />
                  </VBox.margin>
                </ListView>
              </children>
            </VBox>
          </children>
        </StackPane>
      </items>
    </SplitPane>
  </children>
</StackPane>
```
###### /resources/view/CalendarPanel.fxml
``` fxml

<StackPane xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="browser"/>
</StackPane>

```
###### /java/seedu/address/ui/PersonDetailsPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;

/**
 * The UI component that handles the display of beneficiary details, location on map
 * and session reports.
 */
public class PersonDetailsPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private MapPanel mapPanel;

    @FXML
    private StackPane mapPanelPlaceholder;


    public PersonDetailsPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.

        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        loadMapPanel();
    }

    /**
     * Loads a map to the allocated stack pane.
     */
    public void loadMapPanel() {
        if (mapPanel == null) {
            mapPanel = new MapPanel("MapPanel.fxml");
            mapPanelPlaceholder.getChildren().add(mapPanel.getRoot());
        }
    }

    /**
     * Removes a map from the allocated stack pane.
     */
    public void removeMapPanel() {

        if (mapPanel != null && mapPanelPlaceholder.getChildren().contains(mapPanel.getRoot())) {
            mapPanel.resetMap();
            mapPanelPlaceholder.getChildren().remove(mapPanel.getRoot());
            mapPanel = null;
        }
    }

    /**
     * Frees resources allocated to the map panel if map panel is not empty.
     */
    public void freeResources() {
        if (mapPanel != null && mapPanelPlaceholder.getChildren().contains(mapPanel.getRoot())) {
            mapPanel.freeResources();
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mapPanel.loadAddress(event.getNewSelection().person.getAddress().toString());
    }

    @Subscribe
    private void handleShowInvalidAddressOverlayEvent(ShowInvalidAddressOverlayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mapPanel.showInvalidAddressOverlay(event.getAddressValidity());
    }

    @Subscribe
    private void handleLoadMapPanelEvent(LoadMapPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getFeatureTarget().equals("details")) {
            loadMapPanel();
        }
    }

    @Subscribe
    private void handleRemoveMapPanelEvent(RemoveMapPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!event.getFeatureTarget().equals("details")) {
            removeMapPanel();
        }
    }
}
```
###### /java/seedu/address/ui/CalendarPanel.java
``` java
/**
 * The Person Details Panel of the App.
 * To be UPDATED
 */
public class CalendarPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String CALENDAR_URL = "https://calendar.google.com/calendar/r";

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public CalendarPanel() throws IOException {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

```
###### /java/seedu/address/ui/CalendarPanel.java
``` java

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @FXML
    private StackPane personDetailsPlaceholder;

    @FXML
    private StackPane calendarPlaceholder;

    @FXML
    private StackPane dailySchedulerPlaceholder;

    @FXML
    private TabPane featuresTabPane;

    @FXML
    private Tab detailsTab;

    @FXML
    private Tab calendarTab;

    @FXML
    private Tab dailySchedulerTab;

```
###### /java/seedu/address/ui/MainWindow.java
``` java

    /**
     * Handle event of feature tab switching.
     */
    @Subscribe
    private void handleFeatureSwitch(SwitchFeatureEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.getFeatureTarget()) {
        case "details":
            featuresTabPane.getSelectionModel().select(detailsTab);
            break;

        case "calendar":
            featuresTabPane.getSelectionModel().select(calendarTab);
            break;

        case "scheduler":
            featuresTabPane.getSelectionModel().select(dailySchedulerTab);
            break;

        default:
            break;
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    void releaseResources() {
        personDetailsPanel.freeResources();
        calendarPanel.freeResources();
        dailySchedulerPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

```
###### /java/seedu/address/ui/DailySchedulerPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoadDirectionsEvent;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;

/**
 * The UI component that handles the display of daily schedules and directions between locations.
 */
public class DailySchedulerPanel extends UiPart<Region> {

    private static final String FXML = "DailySchedulerPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private MapPanel directionPanel;

    @FXML
    private StackPane directionPanelPlaceholder;



    public DailySchedulerPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
    }

    /**
     * Loads a map with directional information to the allocated stack pane.
     */
    public void loadDirectionPanel() {
        if (directionPanel == null) {
            directionPanel = new MapPanel("MapPanel.fxml");
            directionPanelPlaceholder.getChildren().add(directionPanel.getRoot());
        }
    }

    /**
     * Removes the map with directional information.
     */
    public void removeDirectionPanel() {

        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
            directionPanel.resetMap();
            directionPanelPlaceholder.getChildren().remove(directionPanel.getRoot());
            directionPanel = null;
        }
    }


    /**
     * Updates the directions on the map.
     */
    public void updateDirections(String addressOrigin, String addressDestination) {
        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
            directionPanel.loadDirections(addressOrigin, addressDestination);
        }
    }

    /**
     * Frees resources allocated to the direction panel if direction panel is not empty.
     */
    public void freeResources() {
        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
            directionPanel.freeResources();
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    private void handleLoadMapPanelEvent(LoadMapPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getFeatureTarget().equals("scheduler")) {
            loadDirectionPanel();
        }
    }

    @Subscribe
    private void handleRemoveMapPanelEvent(RemoveMapPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!event.getFeatureTarget().equals("scheduler")) {
            removeDirectionPanel();
        }
    }

    @Subscribe
    private void handleLoadDirectionsEvent(LoadDirectionsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updateDirections("Blk 138, Potong Pasir Ave 3", "342 Pasir Panjang");
    }
}
```
###### /java/seedu/address/ui/MapPanel.java
``` java
package seedu.address.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.MapManager;

/**
 * The UI component that handles the display of maps.
 */
public class MapPanel extends UiPart<Region>
        implements Initializable, MapComponentInitializedListener {


    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GoogleMapView mapView;

    @FXML
    private Pane invalidAddressOverlay;
    private GoogleMap map;

    public MapPanel(String fxmlUrl) {
        super(fxmlUrl);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);

    }

    /**
     * Set the initial properties of the map.
     */
    @Override
    public void mapInitialized() {

        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(1.3521, 103.8198))
                .mapType(MapTypeIdEnum.ROADMAP)
                .mapTypeControl(false)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoom(10);

        map = mapView.createMap(mapOptions);
        invalidAddressOverlay.setVisible(false);

    }

    /**
     * Resets the map to default view .
     */
    public void resetMap() {
        if (map != null) {
            map.clearMarkers();
            map.setCenter(new LatLong(1.3521, 103.8198));
            map.setZoom(10);
            map.setMapType(MapTypeIdEnum.ROADMAP);
        }
    }
    public void loadAddress(String address) {
        MapManager.GeocodeUtil.setMapMarkerFromAddress(map, address);
    }
    public void showInvalidAddressOverlay(Boolean show) {
        invalidAddressOverlay.setVisible(show);
    }
    public void loadDirections(String addressOrigin, String addressDestination) {
        MapManager.DirectionsUtil.setDirectionsOnMap(map, mapView.getDirec(), addressOrigin, addressDestination);
    }
    public void freeResources() {
        map = null;
    }

}
```
###### /java/seedu/address/commons/events/ui/LoadMapPanelEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event that triggers the loading of map panel of specified feature UI.
 */
public class LoadMapPanelEvent extends BaseEvent {

    private final String featureTarget;

    public LoadMapPanelEvent(String feature) {
        this.featureTarget = feature;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getFeatureTarget() {
        return featureTarget;
    }

}
```
###### /java/seedu/address/commons/events/ui/RemoveMapPanelEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event that triggers the removal of map panel of specified feature UI.
 */
public class RemoveMapPanelEvent extends BaseEvent {

    private final String featureTarget;

    public RemoveMapPanelEvent(String feature) {
        this.featureTarget = feature;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getFeatureTarget() {
        return featureTarget;
    }

}
```
###### /java/seedu/address/commons/events/ui/SwitchFeatureEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to switch the feature user interface.
 */
public class SwitchFeatureEvent extends BaseEvent {

    private final String featureTarget;

    public SwitchFeatureEvent(String feature) {
        this.featureTarget = feature;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getFeatureTarget() {
        return featureTarget;
    }

}
```
###### /java/seedu/address/commons/events/ui/ShowInvalidAddressOverlayEvent.java
``` java

package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event to show the invalid address overlay over Google Map user interface.
 */

public class ShowInvalidAddressOverlayEvent extends BaseEvent {

    private final boolean isInvalidAddress;

    public ShowInvalidAddressOverlayEvent(boolean isInvalidAddress) {
        this.isInvalidAddress = isInvalidAddress;
    }

    public boolean getAddressValidity() {
        return isInvalidAddress;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String feature}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code feature} is invalid.
     */
    public static String parseFeature(String feature) throws IllegalValueException {
        requireNonNull(feature);
        String trimmedFeature = feature.trim();
        if (!(trimmedFeature.equals("details")
                || trimmedFeature.equals("calendar")
                || trimmedFeature.equals("scheduler"))) {
            throw new IllegalValueException("Feature should only be either \"details\", \"calendar\" or \"scheduler\"");
        }
        return trimmedFeature;
    }

    /**
     * Parses a {@code String username} into an {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Username>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Username> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String password} into an {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws IllegalValueException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code Optional<String> password} into an {@code Optional<Password>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Password> parsePassword(Optional<String> password) throws IllegalValueException {
        requireNonNull(password);
        return password.isPresent() ? Optional.of(parsePassword(password.get())) : Optional.empty();
    }
}
```
###### /java/seedu/address/logic/parser/SwitchCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwitchCommand object
 */
public class SwitchCommandParser implements Parser<SwitchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwitchCommand parse(String args) throws ParseException {
        try {
            String feature = ParserUtil.parseFeature(args);
            return new SwitchCommand(feature);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/MapManager.java
``` java
package seedu.address.logic;

import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;

//import seedu.address.commons.core.EventsCenter;
//import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;

/**
 * Handles changes to any Map user interfaces.
 */

public class MapManager {
    /**
     * Helps with retrieving the geocode from given address.
     */
    public static class GeocodeUtil {

        private static GeocodingService geocodingService = new GeocodingService();

        /**
         *  Retrieves geocode of specified person address and updates map accordingly.
         */
        public static class MyGeocodingServiceCallback implements GeocodingServiceCallback {
            private GoogleMap map;
            public MyGeocodingServiceCallback(GoogleMap map) {
                this.map = map;
            }
            @Override
            public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
                LatLong geocode;
                if (status == GeocoderStatus.ZERO_RESULTS) {
                    EventsCenter.getInstance().post(new ShowInvalidAddressOverlayEvent(true));
                    geocode = null;

                } else if (results.length > 1) {
                    EventsCenter.getInstance().post(new ShowInvalidAddressOverlayEvent(false));
                    geocode = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                            results[0].getGeometry().getLocation().getLongitude());
                } else {
                    EventsCenter.getInstance().post(new ShowInvalidAddressOverlayEvent(false));
                    geocode = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                            results[0].getGeometry().getLocation().getLongitude());
                }

                if (geocode != null) {
                    map.setZoom(17);
                    map.setCenter(geocode);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(geocode);

                    Marker marker = new Marker(markerOptions);
                    map.addMarker(marker);
                } else {
                    map.setZoom(10);
                    map.setCenter(new LatLong(1.3521, 103.8198));
                    map.clearMarkers();
                }
            }
        }

        /**
         *  Calls on geocodingService to update geocode and set map maker of specified person.
         */
        public static void setMapMarkerFromAddress(GoogleMap map, String address) {
            geocodingService.geocode(address, new MyGeocodingServiceCallback(map));
        }
    }
    /**
     * Helps with retrieving the directions between two given addresses.
     */
    public static class DirectionsUtil {
        /**
         *  Creates the required DirectionsServiceCallback by passing specified {@GoogleMap map}.
         */
        public static class MyDirectionsServiceCallback implements DirectionsServiceCallback {
            private GoogleMap map;
            public MyDirectionsServiceCallback(GoogleMap map) {
                this.map = map;
            }
            @Override
            public void directionsReceived(DirectionsResult results, DirectionStatus status) {
            }
        }
        /**
         *  Sets the directions between addresses on the map.
         */
        public static void setDirectionsOnMap(GoogleMap map, DirectionsPane directionsPane,
                String addressOrigin, String addressDestination) {
            DirectionsService directionsService = new DirectionsService();
            DirectionsRequest request = new DirectionsRequest(addressOrigin, addressDestination, TravelModes.DRIVING);
            DirectionsRenderer directionsRenderer = new DirectionsRenderer(true, map, directionsPane);
            directionsService.getRoute(request, new MyDirectionsServiceCallback(map), directionsRenderer);
        }
    }
}






```
###### /java/seedu/address/logic/commands/NavigateCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoadDirectionsEvent;

/**
 * Displays directions between locations scheduled for the day based on specified events of the day.
 */
public class NavigateCommand extends Command {

    public static final String COMMAND_WORD = "navigate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates Daily Scheduler map to display navigation between locations.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Displaying directions between...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new LoadDirectionsEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    //    @Override
    //    public boolean equals(Object other) {
    //        return other == this // short circuit if same object
    //                || (other instanceof SwitchCommand // instanceof handles nulls
    //                && this.featureTarget.equals(((SwitchCommand) other).featureTarget)); // state check
    //    }
}
```
###### /java/seedu/address/logic/commands/SwitchCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.SwitchFeatureEvent;

/**
 * Switches user interface to the feature requested.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to the user interface feature identified by the user.\n"
            + "Parameters: FEATURE (must be either \"details\", \"calendar\" or \"scheduler\")\n"
            + "Example: " + COMMAND_WORD + " calendar";

    public static final String MESSAGE_SUCCESS = "Switched to %1$s tab";

    private final String featureTarget;

    public SwitchCommand(String featureTarget) {
        this.featureTarget = featureTarget;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new SwitchFeatureEvent(featureTarget));
        EventsCenter.getInstance().post(new RemoveMapPanelEvent(featureTarget));
        EventsCenter.getInstance().post(new LoadMapPanelEvent(featureTarget));
        return new CommandResult(String.format(MESSAGE_SUCCESS, featureTarget));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand // instanceof handles nulls
                && this.featureTarget.equals(((SwitchCommand) other).featureTarget)); // state check
    }

}
```
