package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.AliasListEvent;
import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.commons.events.ui.GoogleMapsEvent;
import seedu.address.commons.events.ui.InfoPanelEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.TimeTableEvent;
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
    private AliasList aliasList;
    private GoogleMapsDisplay mapsDisplay;
    private PersonDetailsCard personDetailsCard;
    private TimetableUnionPanel timetableUnionPanel;

    @FXML
    private StackPane placeholder;
    @FXML
    private StackPane birthdayPlaceholder;
    @FXML
    private StackPane venuePlaceholder;
    @FXML
    private StackPane aliasListPlaceholder;
    @FXML
    private StackPane userDetailsPlaceholder;
    @FXML
    private StackPane timetableUnionPlaceholder;
    @FXML
    private StackPane mapsPlaceholder;


    public InfoPanel() {
        super(FXML);

        personDetailsCard = new PersonDetailsCard();
        userDetailsPlaceholder.getChildren().add(personDetailsCard.getRoot());
        venueTable = new VenueTable();
        venuePlaceholder.getChildren().add(venueTable.getRoot());
        aliasList = new AliasList();
        aliasListPlaceholder.getChildren().add(aliasList.getRoot());
        mapsDisplay = new GoogleMapsDisplay();
        mapsPlaceholder.getChildren().add(mapsDisplay.getRoot());
        birthdayList = new BirthdayList();
        birthdayPlaceholder.getChildren().add(birthdayList.getRoot());
        timetableUnionPanel = new TimetableUnionPanel();
        timetableUnionPlaceholder.getChildren().add(timetableUnionPanel.getRoot());
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
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        venuePlaceholder.getChildren().removeAll();
        venueTable = new VenueTable(event.getSchedule());
        venuePlaceholder.getChildren().add(venueTable.getRoot());
        venuePlaceholder.toFront();
        venueTable.setStyle();
    }

    @Subscribe
    private void handleGoogleMapsDisplayEvent(GoogleMapsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getIsOneLocationEvent()) {
            mapsDisplay.loadMapPage(event.getLocations());
        } else {
            mapsDisplay.loadMapDirections(event.getLocations());
        }
        mapsPlaceholder.toFront();
    }

    @Subscribe
    private void handleAliasListEvent(AliasListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        aliasListPlaceholder.getChildren().removeAll();
        aliasList.init(event.getAliases());
        aliasListPlaceholder.toFront();
        aliasList.setStyle();
    }

    @Subscribe
    private void handleInfoPanelEvent(InfoPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            hideAllPanel();
        });
    }
    //@@author

    //@@author yeggasd
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        hideAllPanel();

        Person person = event.getNewSelection().person;
        int oddEvenIndex = event.getOddEvenIndex();

        personDetailsCard.update(person, oddEvenIndex);
        userDetailsPlaceholder.toFront();
    }

    /**
     * Hides all the panels
     */
    private void hideAllPanel() {
        userDetailsPlaceholder.toBack();
        venuePlaceholder.toBack();
        mapsPlaceholder.toBack();
        birthdayPlaceholder.toBack();
        timetableUnionPlaceholder.toBack();
        aliasListPlaceholder.toBack();
    }
    //@@author

    @Subscribe
    private void handleTimeTableUnionEvent(TimeTableEvent event) {

        userDetailsPlaceholder.getChildren().removeAll();
        timetableUnionPanel = new TimetableUnionPanel(event.getTimeTable());
        timetableUnionPlaceholder.getChildren().add(timetableUnionPanel.getRoot());
        timetableUnionPlaceholder.toFront();
        timetableUnionPanel.setStyle();
    }
    //@@author
}
