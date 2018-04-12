# jaronchan
###### /resources/view/MainWindow.fxml
``` fxml
          <TabPane fx:id="featuresTabPane" tabClosingPolicy="UNAVAILABLE" xmlns:fx="http://javafx.com/fxml/1"
                   xmlns="http://javafx.com/javafx/9.0.4">
            <tabs>
              <Tab id="details" fx:id="detailsTab" text="Details">
                <content>
                  <StackPane fx:id="personDetailsPlaceholder" prefWidth="340">
                    <padding>
                      <Insets right="10" left="10"/>
                    </padding>
                  </StackPane>
                </content>
              </Tab>
              <Tab id="calendar" fx:id="calendarTab" text="Calendar">
                <content>
                  <StackPane fx:id="calendarPlaceholder" prefWidth="340">
                    <padding>
                      <Insets top="10" right="10" bottom="10" left="10"/>
                    </padding>
                  </StackPane>
                </content>
              </Tab>
              <Tab id="scheduler" fx:id="dailySchedulerTab" text="Daily Scheduler">
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

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

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
                <Insets bottom="10" left="10" right="10" top="10" />
              </padding>
              <children>
                <Label text="Scheduled Session for DATE">
                  <padding>
                    <Insets bottom="5" left="5" />
                  </padding>
                </Label>
                  <ScrollPane prefHeight="-1.0" prefWidth="-1.0">
                    <content>
                      <VBox fx:id="eventsListStack" prefHeight="-1.0" prefWidth="-1.0" />
                    </content>
                  </ScrollPane>
              </children>
            </VBox>
          </children>
        </StackPane>
        <StackPane prefHeight="150.0" prefWidth="200.0">
          <padding>
            <Insets left="5.0" />
          </padding>
          <children>
            <HBox>
              <children>
                <StackPane fx:id="directionPanelPlaceholder" prefWidth="-1.0">
                  <HBox.margin>
                    <Insets bottom="10.0" left="10.0" top="10.0" />
                  </HBox.margin>
                </StackPane>
                <VBox fx:id="buttonStack" minWidth="50.0" spacing="10.0">
                  <HBox.margin>
                    <Insets left="10.0" right="5.0" top="10.0" />
                  </HBox.margin></VBox>
              </children>
            </HBox>
          </children>
        </StackPane>
      </items>
    </SplitPane>
  </children>
</StackPane>
```
###### /resources/view/MapPanel.fxml
``` fxml
<?import com.lynden.gmapsfx.GoogleMapView?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

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

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<StackPane xmlns="http://javafx.com/javafx/9.0.4" xmlns:fx="http://javafx.com/fxml/1">
  <children>
    <SplitPane dividerPositions="0.5">
      <items>
        <StackPane>
          <children>
            <SplitPane dividerPositions="0.5" orientation="VERTICAL" prefHeight="200.0" prefWidth="160.0">
              <items>
                <VBox prefHeight="200.0" prefWidth="100.0" styleClass="v-box">
                  <children>
                    <GridPane gridLinesVisible="false" styleClass="grid-pane">
                      <columnConstraints>
                        <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="125.0" minWidth="125.0" prefWidth="125.0" />
                        <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="250.0" />
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
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-odd" />
                        <Label styleClass="label-bright" stylesheets="@DarkTheme.css" text="Name" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-odd" GridPane.columnIndex="1" />
                        <Label fx:id="nameLabel" styleClass="label-bright" stylesheets="@DarkTheme.css" GridPane.columnIndex="1" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-even" GridPane.rowIndex="1" />
                        <Label styleClass="label-bright" stylesheets="@DarkTheme.css" text="Phone Number" GridPane.rowIndex="1" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-even" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                        <Label fx:id="phoneNumberLabel" styleClass="label-bright" stylesheets="@DarkTheme.css" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-odd" GridPane.rowIndex="2" />
                        <Label styleClass="label-bright" stylesheets="@DarkTheme.css" text="Email" GridPane.rowIndex="2" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-odd" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                        <Label fx:id="emailLabel" styleClass="label-bright" stylesheets="@DarkTheme.css" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-even" GridPane.rowIndex="3" />
                        <Label styleClass="label-bright" stylesheets="@DarkTheme.css" text="Address" GridPane.rowIndex="3" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-even" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                        <Label fx:id="addressLabel" styleClass="label-bright" stylesheets="@DarkTheme.css" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-odd" GridPane.rowIndex="4" />
                        <Label styleClass="label-bright" stylesheets="@DarkTheme.css" text="Condition" GridPane.rowIndex="4" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-odd" GridPane.columnIndex="1" GridPane.rowIndex="4" />
                        <Label fx:id="conditionLabel" styleClass="label-bright" stylesheets="@DarkTheme.css" GridPane.columnIndex="1" GridPane.rowIndex="4" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-even" GridPane.rowIndex="5" />
                        <Label styleClass="label-bright" stylesheets="@DarkTheme.css" text="Priority" GridPane.rowIndex="5" />
                        <Pane prefHeight="-1.0" prefWidth="-1.0" styleClass="pane-even" GridPane.columnIndex="1" GridPane.rowIndex="5" />
                        <Label fx:id="priorityLabel" styleClass="label-bright" stylesheets="@DarkTheme.css" GridPane.columnIndex="1" GridPane.rowIndex="5" />
                      </children>
                    </GridPane>
                  </children>
                </VBox>
                <StackPane prefHeight="-1.0" prefWidth="-1.0">
                  <children>
                    <StackPane fx:id="mapPanelPlaceholder" prefWidth="-1.0">
                      <padding>
                        <Insets left="10" right="10" />
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
        <StackPane prefHeight="-1.0" prefWidth="-1.0">
          <padding>
            <Insets left="5.0" />
          </padding>
          <children>
            <VBox prefHeight="-1.0" prefWidth="-1.0">
              <children>
                <Label text="Session Reports">
                  <padding>
                    <Insets bottom="5.0" left="5.0" top="5.0" />
                  </padding>
                </Label>
                <ScrollPane prefHeight="-1.0" prefWidth="-1.0">
                  <content>
                    <TextArea fx:id="sessionLogPanel" editable="false" prefHeight="600" maxWidth="400" wrapText="true" />
                  </content>
                </ScrollPane>
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
###### /resources/view/ScheduledEventCard.fxml
``` fxml

<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.StackPane?>

<StackPane xmlns="http://javafx.com/javafx/9.0.4" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <TextArea fx:id="eventInfo" editable="false" minHeight="215" prefWidth="350" />
   </children>
</StackPane>
```
###### /java/seedu/address/ui/ScheduledEventCard.java
``` java
package seedu.address.ui;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.logic.OAuthManager;

/**
 * An UI component that displays information of a {@code Event}.
 */

public class ScheduledEventCard extends UiPart<Region> {

    private static final String FXML = "ScheduledEventCard.fxml";
    private static final String EVENT_NUM_HEADER = "EVENT NUM: ";
    private static final String EVENT_TITLE_HEADER = "TITLE: ";
    private static final String EVENT_TIMING_HEADER = "TIME: ";
    private static final String EVENT_LOCATION_HEADER = "LOCATION: ";
    private static final String EVENT_PERSON_HEADER = "NAME: ";
    private static final String EVENT_CONDITION_HEADER = "CONDITION: ";
    private static final String EVENT_MOBILE_HEADER = "MOBILE: ";
    private static final String FUTURE_IMPLEMENTATION = "TO BE IMPLEMENTED IN 2.0";
    private static final String EVENT_DIVIDER = "================================ \n";

    public final Event event;

    private String formattedScheduledEvent;

    @FXML
    private TextArea eventInfo;


    public ScheduledEventCard(Event event, int eventIndex) {
        super(FXML);
        this.event = event;

        formattedScheduledEvent = scheduledEventFormatter(this.event, eventIndex);
        eventInfo.setWrapText(true);
        eventInfo.setText(formattedScheduledEvent);
    }

    /**
     * A method to format the scheduled event information of a {@code Event}.
     */
    private String scheduledEventFormatter(Event event, int eventIndex) {
        String title = event.getSummary();
        DateTime startAsDateTime = event.getStart().getDateTime();
        DateTime endAsDateTime = event.getEnd().getDateTime();
        String location = event.getLocation();

        String start = OAuthManager.getDateTimeAsHumanReadable(startAsDateTime);
        String end = OAuthManager.getDateTimeAsHumanReadable(endAsDateTime);

        if (start == null) {
            start = "Unable to retrieve start time";
        }
        if (end == null) {
            end = "Unable to retrieve end time";
        }
        if (location == null) {
            location = "No Location Specified";
        }
        String eventAsString = EVENT_NUM_HEADER + eventIndex + "\n"
                + EVENT_TITLE_HEADER + title + "\n"
                + EVENT_TIMING_HEADER + start + " - " + end + "\n"
                + EVENT_DIVIDER
                + EVENT_LOCATION_HEADER + location + "\n"
                + EVENT_PERSON_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_MOBILE_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_CONDITION_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_DIVIDER + EVENT_DIVIDER;
        System.out.printf(eventAsString);

        return eventAsString;
    }
}
```
###### /java/seedu/address/ui/PersonDetailsPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;
import seedu.address.commons.events.ui.ShowUpdatedSessionLogEvent;
import seedu.address.model.person.Person;

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

    @FXML
    private TextArea sessionLogPanel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private Label priorityLabel;

    public PersonDetailsPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        showSelectedPersonDetails(null);
        loadMapPanel();
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showSelectedPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            nameLabel.setText(person.getName().toString());
            phoneNumberLabel.setText(person.getPhone().toString());
            emailLabel.setText(person.getEmail().toString());
            addressLabel.setText(person.getAddress().toString());
            conditionLabel.setText("TO BE IMPLEMENTED IN 2.0");
            priorityLabel.setText("TO BE IMPLEMENTED IN 2.0");
            String sessionLogToDisplay = person.getSessionLogs().toString();
            loadSessionLogs(sessionLogToDisplay);
        } else {
            // Person is null, remove all the text.
            nameLabel.setText("");
            phoneNumberLabel.setText("");
            emailLabel.setText("");
            addressLabel.setText("");
            conditionLabel.setText("");
            priorityLabel.setText("");
            sessionLogPanel.setText("Select a Person to display content.");
        }
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
     * Loads the session logs stored to {@code Person}.
     */
    private void loadSessionLogs(String sessionLogToDisplay) {
        if (sessionLogToDisplay.equals("")) {
            sessionLogPanel.setText("No session logs has been added to this person yet!");
        } else {
            sessionLogPanel.setText(sessionLogToDisplay);
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
        if (mapPanel != null && mapPanelPlaceholder.getChildren().contains(mapPanel.getRoot())) {
            mapPanel.loadAddress(event.getNewSelection().person.getAddress().toString());
        }
        showSelectedPersonDetails(event.getNewSelection().person);
    }

    @Subscribe
    private void handleShowUpdatedSessionLogEvent(ShowUpdatedSessionLogEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadSessionLogs(event.getTargetPerson().getSessionLogs().toString());
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
     * Disables the selection of tabs and persons cards by mouse click.
     */

    private void disableSelection() {
        featuresTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                EventsCenter.getInstance().post(new RemoveMapPanelEvent(newValue.getId().toLowerCase()));
                EventsCenter.getInstance().post(new LoadMapPanelEvent(newValue.getId().toLowerCase()));
            }
        });
    }

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
```
###### /java/seedu/address/ui/DailySchedulerPanel.java
``` java
package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.api.services.calendar.model.Event;
import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DailyScheduleShownChangedEvent;
import seedu.address.commons.events.ui.LoadDirectionsEvent;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.ResetDirectionsEvent;
import seedu.address.commons.events.ui.UpdateNumberOfButtonsEvent;

/**
 * The UI component that handles the display of daily schedules and directions between locations.
 */
public class DailySchedulerPanel extends UiPart<Region> {

    private static final String FXML = "DailySchedulerPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private MapPanel directionPanel;

    @FXML
    private VBox eventsListStack;

    @FXML
    private StackPane directionPanelPlaceholder;

    @FXML
    private VBox buttonStack;

    @FXML
    private ToggleButton button;

    public DailySchedulerPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Fills schedule panel to with scheduled events for the specified date.
     * If the day has no events, a placeholder text will be shown.
     *
     */
    private void showPlannedEvents(List<Event> dailyEventsList) {

        int numOfEvents = dailyEventsList.size();
        if (numOfEvents != 0) {
            for (int i = 0; i < numOfEvents; i++) {
                ScheduledEventCard card = new ScheduledEventCard(dailyEventsList.get(i), i + 1);
                eventsListStack.getChildren().add(card.getRoot());
            }
        }
    }

    /**
     * Resets schedule panel.
     */
    private void removePlannedEvents() {
        eventsListStack.getChildren().clear();
    }

    /**
     * Buttons depending on how many trips to be made.
     */
    public void addButtons(int numOfInstances) {
        for (int i = 0; i < numOfInstances; i++) {
            buttonStack.getChildren().add(new ToggleButton(" "));
        }
    }
    /**
     * Removes existing buttons.
     */
    public void removeButtons() {
        buttonStack.getChildren().clear();
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
     * Resets the directions on the map.
     */
    public void resetDirections() {
        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
            directionPanel.resetDirections();
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
    private void handleDailyScheduleShownChangedEvent(DailyScheduleShownChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        removePlannedEvents();
        showPlannedEvents(event.getDailyEventsList());

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
        updateDirections(event.getAddressOrigin(), event.getGetAddressDestination());
    }

    @Subscribe
    private void handleResetDirectionsEvent(ResetDirectionsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetDirections();
    }

    @Subscribe
    private void handleUpdateNumberOfButtonsEvent(UpdateNumberOfButtonsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        removeButtons();
        addButtons(event.getNumOfInstances());
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
import seedu.address.logic.map.MapManager;

/**
 * The UI component that handles the display of maps.
 */
public class MapPanel extends UiPart<Region>
        implements Initializable, MapComponentInitializedListener {


    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private MapManager mapManager;

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
        mapManager = new MapManager(map, mapView.getDirec());
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
        mapManager.setMapMarkerFromAddress(map, address);
    }
    public void showInvalidAddressOverlay(Boolean show) {
        invalidAddressOverlay.setVisible(show);
    }

    /**
     * Load the directions to display.
     */
    public void loadDirections(String addressOrigin, String addressDestination) {
        mapManager.setDirectionsOnMap(addressOrigin, addressDestination);
    }

    /**
     * Resets the map to remove pre-existing directions from previous schedule.
     */
    public void resetDirections() {
        mapManager.resetDirectionsMap();
        resetMap();
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
###### /java/seedu/address/commons/events/ui/UpdateNumberOfButtonsEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event that updates the number of Scheduler Map Buttons to display.
 */
public class UpdateNumberOfButtonsEvent extends BaseEvent {

    private final int numOfInstances;

    public UpdateNumberOfButtonsEvent(int numOfInstances) {
        this.numOfInstances = numOfInstances;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public int getNumOfInstances() {
        if (numOfInstances < 1) {
            return 0;
        }
        return numOfInstances;
    }
}
```
###### /java/seedu/address/commons/events/ui/DailyScheduleShownChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import java.util.List;

import com.google.api.services.calendar.model.Event;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Scheduled Events shown.
 */
public class DailyScheduleShownChangedEvent extends BaseEvent {


    private final List<Event> dailyEventsList;

    public DailyScheduleShownChangedEvent(List<Event> dailyEventsList) {
        this.dailyEventsList = dailyEventsList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public List<Event> getDailyEventsList() {
        return dailyEventsList;
    }
}
```
###### /java/seedu/address/commons/events/ui/ShowUpdatedSessionLogEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Signals an updated session log that needs to be reloaded.
 */

public class ShowUpdatedSessionLogEvent extends BaseEvent {

    private final Person targetPerson;
    public ShowUpdatedSessionLogEvent(Person targetPerson) {
        this.targetPerson = targetPerson;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getTargetPerson() {
        return targetPerson;
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
###### /java/seedu/address/commons/events/ui/LoadDirectionsEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to load the direction between two addresses.
 */
public class LoadDirectionsEvent extends BaseEvent {

    private final String addressOrigin;

    private final String addressDestination;

    public LoadDirectionsEvent(String addressOrigin, String addressDestination) {
        this.addressOrigin = addressOrigin;
        this.addressDestination = addressDestination;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getAddressOrigin() {
        return addressOrigin;
    }

    public String getGetAddressDestination() {
        return addressDestination;
    }
}
```
###### /java/seedu/address/commons/events/ui/ResetDirectionsEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Signals a reset of direction map.
 */

public class ResetDirectionsEvent extends BaseEvent {

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

```
###### /java/seedu/address/logic/parser/NavigateCommandParser.java
``` java

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(ive.getMessage()));
        } catch (InvalidCalendarEventCountException e) {
            throw new ParseException(
                    String.format(NavigateCommand.MESSAGE_NO_EVENT));
        }
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
###### /java/seedu/address/logic/OAuthManager.java
``` java
    /**
     * Gets the specified event pair by index (offset by 1 due to array indexing) according to a user's input.
     * @param index
     * @return List
     */
    public static List<Event> getEventByIndexPairFromDailyList(int index)
            throws InvalidCalendarEventCountException, IllegalValueException {
        List<Event> eventPair = new ArrayList<>();
        if (index < 1 || index > dailyEventsList.size() - 1) {
            throw new IllegalValueException(NavigateCommand.MESSAGE_INVALID_RANGE);
        } else if (dailyEventsList.isEmpty() && dailyEventsList.size() < 2) {
            throw new InvalidCalendarEventCountException();
        } else {
            eventPair.add(dailyEventsList.get(index - 1));
            eventPair.add(dailyEventsList.get(index));
        }

        return eventPair;
    }
```
###### /java/seedu/address/logic/map/MyDirectionsServiceCallback.java
``` java
package seedu.address.logic.map;

import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;

/**
 *  Creates the required DirectionsServiceCallback by passing specified {@GoogleMap map}.
 */

public class MyDirectionsServiceCallback implements DirectionsServiceCallback {
    private GoogleMap map;
    public MyDirectionsServiceCallback(GoogleMap map) {
        this.map = map;
    }
    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
    }
}
```
###### /java/seedu/address/logic/map/MyGeocodingServiceCallback.java
``` java
package seedu.address.logic.map;

import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;

/**
 *  Retrieves geocode of specified person address and updates map accordingly.
 */

public class MyGeocodingServiceCallback implements GeocodingServiceCallback {
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
```
###### /java/seedu/address/logic/map/MapManager.java
``` java
package seedu.address.logic.map;

import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

/**
 * Handles changes to any Map user interfaces.
 */

public class MapManager {

    private GoogleMap map;
    private GeocodingService geocodingService;

    private DirectionsService directionsService;
    private DirectionsRequest directionsRequest;
    private DirectionsRenderer directionsRenderer;
    private DirectionsPane directionsPane;

    public MapManager(GoogleMap map, DirectionsPane directionsPane) {
        this.map = map;
        geocodingService = new GeocodingService();
        directionsService = new DirectionsService();
        this.directionsPane = directionsPane;
        directionsRenderer = new DirectionsRenderer(true, map, directionsPane);
    }

    /**
     *  Calls on geocodingService to update geocode and set map maker of specified person.
     */

    public void setMapMarkerFromAddress(GoogleMap map, String address) {
        geocodingService.geocode(address, new MyGeocodingServiceCallback(map));
    }

    /**
     *  Sets the directions between addresses on the map.
     */
    public void setDirectionsOnMap(String addressOrigin, String addressDestination) {
        directionsRenderer.clearDirections();
        directionsRequest = new DirectionsRequest(addressOrigin, addressDestination, TravelModes.DRIVING);
        directionsRenderer = new DirectionsRenderer(true, map, directionsPane);
        directionsService.getRoute(directionsRequest, new MyDirectionsServiceCallback(map), directionsRenderer);
    }

    public void resetDirectionsMap() {
        directionsRenderer.clearDirections();
    }
}






```
###### /java/seedu/address/logic/commands/NavigateCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import com.google.api.services.calendar.model.Event;

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
    public static final String MESSAGE_INVALID_RANGE = "The INDEX provided is invalid.\n"
            + "INDEX must more than ZERO and less than the number of planned events for the day.";
    public static final String MESSAGE_NO_EVENT = "There is either zero or one event planned for the day.\n"
            + "No directions will be listed.";
    private final List<Event> eventPair;

```
###### /java/seedu/address/logic/commands/NavigateCommand.java
``` java
    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(
            new LoadDirectionsEvent(
                this.eventPair.get(0).getLocation(),
                this.eventPair.get(1).getLocation()
            )
        );
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
```
###### /java/seedu/address/logic/commands/ShowScheduleCommand.java
``` java

            List<Event> dailyEventsList = OAuthManager.getDailyEvents(user, localDate);
            EventsCenter.getInstance().post(new DailyScheduleShownChangedEvent(dailyEventsList));
            EventsCenter.getInstance().post(new ResetDirectionsEvent());
            EventsCenter.getInstance().post(new UpdateNumberOfButtonsEvent(dailyEvents.size() - 1));

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
