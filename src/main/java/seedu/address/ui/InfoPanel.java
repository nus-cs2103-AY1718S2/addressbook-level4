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
import seedu.address.commons.events.ui.VenueTableEvent;
import seedu.address.model.person.Person;

/**
 * Container for both browser panel and person information panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private BirthdayList birthdayList;
    private VenueTable venueTable;
    private GoogleMapsDisplay mapsDisplay;
    private PersonDetailsCard personDetailsCard;

    @FXML
    private StackPane placeholder;
    @FXML
    private StackPane birthdayPlaceholder;
    @FXML
    private StackPane venuePlaceholder;
    @FXML
    private StackPane userDetailsPlaceholder;
    @FXML
    private StackPane mapsPlaceholder;


    public InfoPanel() {
        super(FXML);

        venueTable = new VenueTable();

        mapsDisplay = new GoogleMapsDisplay();

        personDetailsCard = new PersonDetailsCard();
        userDetailsPlaceholder.getChildren().add(personDetailsCard.getRoot());

        birthdayList = new BirthdayList();
        birthdayPlaceholder.getChildren().add(birthdayList.getRoot());

        placeholder.toFront();
        registerAsAnEventHandler(this);
    }

    public void freeResources() {
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
        venuePlaceholder.toBack();
        mapsPlaceholder.toBack();
        birthdayPlaceholder.toBack();
        Person person = event.getNewSelection().person;
        int oddEvenIndex = event.getOddEvenIndex();

        personDetailsCard.update(person, oddEvenIndex);
        userDetailsPlaceholder.toFront();
    }
    //@@author
    /*
    @Subscribe
    private void handleTimeTableEvent(TimeTableEvent event) {

        userDetailsPlaceholder.getChildren().removeAll();
        timeTablePanel = new TimeTablePanel(event.getTimeTable());
        userDetailsPlaceholder.getChildren().add(timeTablePanel.getRoot());
        userDetailsPlaceholder.toFront();
        timeTablePanel.setStyle();

    }
    */
}
