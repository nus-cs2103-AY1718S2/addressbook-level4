package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.HomeRequestEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.LocateRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.customer.Customer;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> personListView;

    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Person> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        personListView.setItems(mappedList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and display the location on Google Map.
     */
    //@@author zhangriqi
    private void locate(int index) {
        Platform.runLater(()-> {
            personListView.scrollTo(index);
        });
    }
    //@@author

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    //@@author zhangriqi
    @Subscribe
    private void handleLocateRequestEvent(LocateRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        locate(event.target);
    }
    //@@author
    //@@author jonleeyz
    /**
     * Handles the event where the Esc key is pressed or "home" is input to the CommandBox.
     * {@code HomeRequestEvent}.
     */
    @Subscribe
    private void handleHomeRequestEvent(HomeRequestEvent event) {
        //@TODO to be implemented
    }
    //@@author
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);
            //@@author melvintzw
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
                setStyle("    -fx-label-padding: 0 0 0 0;"
                        + "    -fx-graphic-text-gap : 0;"
                        + "    -fx-padding: 0 0 0 0;"
                        + "    -fx-background-color: derive(-main-colour, 0%);");
            } else {
                if (person.person instanceof Customer) {
                    setGraphic(person.getRoot());
                    setStyle("    -fx-label-padding: 0 0 0 0;"
                            + "    -fx-graphic-text-gap : 0;"
                            + "    -fx-padding: 0 0 0 0;"
                            + "    -fx-background-color: derive(-main-colour, 0%);");
                } else {
                    setGraphic(person.getRoot());
                    setStyle("    -fx-label-padding: 0 0 0 0;"
                            + "    -fx-graphic-text-gap : 0;"
                            + "    -fx-padding: 0 0 0 0;"
                            + "    -fx-background-color: derive(-main-colour, 50%);");
                }
            }
            //@@author
        }
    }

}
