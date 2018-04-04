package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.commons.events.ui.GoogleMapsEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.TimeTableEvent;
import seedu.address.commons.events.ui.VenueTableEvent;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

/**
 * Container for both browser panel and person information panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private Logic logic;


    private BrowserPanel browserPanel;
    private BirthdayList birthdayList;
    private VenueTable venueTable;
    private TimeTablePanel timeTablePanel;
    private GoogleMapsDisplay mapsDisplay;
    private PersonDetailsCard personDetailsCard;

    @FXML
    private StackPane browserPlaceholder;
    @FXML
    private StackPane birthdayPlaceholder;
    @FXML
    private StackPane venuePlaceholder;
    @FXML
    private StackPane userDetailsPlaceholder;
    @FXML
    private StackPane mapsPlaceholder;


    public InfoPanel(Logic logic) {
        super(FXML);

        this.logic = logic;
        fillInnerParts();

        venueTable = new VenueTable(null);

        mapsDisplay = new GoogleMapsDisplay(null);

        browserPlaceholder.toFront();
        registerAsAnEventHandler(this);
    }

    public void freeResources() {
        browserPanel.freeResources();
    }

    /**
     * Helper method to fill UI placeholders
     */
    public void fillInnerParts() {
        personDetailsCard = new PersonDetailsCard();
        userDetailsPlaceholder.getChildren().add(personDetailsCard.getRoot());

        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        birthdayList = new BirthdayList();
        birthdayPlaceholder.getChildren().add(birthdayList.getRoot());

    }

    //@@author AzuraAiR
    @Subscribe
    private void handleBirthdayListEvent(BirthdayListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        birthdayList.loadList(event.getBirthdayList());
        birthdayPlaceholder.toFront();
    }
    //@@author

    //@@author jingyinno
    @Subscribe
    private void handleVenueTableEvent(VenueTableEvent event) {
        venuePlaceholder.getChildren().removeAll();
        venueTable = new VenueTable(event.getSchedule());
        venuePlaceholder.getChildren().add(venueTable.getRoot());
        venuePlaceholder.toFront();
        venueTable.setStyle();
    }

    @Subscribe
    private void handleGoogleMapsDisplayEvent(GoogleMapsEvent event) {
        mapsPlaceholder.getChildren().removeAll();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mapsDisplay = new GoogleMapsDisplay(event.getLocations());
        mapsPlaceholder.getChildren().add(mapsDisplay.getRoot());
        if (event.getIsOneLocationEvent()) {
            mapsDisplay.loadMapPage();
        } else {
            mapsDisplay.loadMapDirections();
        }
        mapsPlaceholder.toFront();
    }
    //@@author

    //@@author yeggasd
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person person = event.getNewSelection().person;

        personDetailsCard.update(person);
        userDetailsPlaceholder.toFront();
    }

    @Subscribe
    private void handleTimeTableEvent(TimeTableEvent event) {
        userDetailsPlaceholder.getChildren().removeAll();
        timeTablePanel = new TimeTablePanel(event.getTimeTable());
        userDetailsPlaceholder.getChildren().add(timeTablePanel.getRoot());
        userDetailsPlaceholder.toFront();
        timeTablePanel.setStyle();
    }
    //@@author
}
