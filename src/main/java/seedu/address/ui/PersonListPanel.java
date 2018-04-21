package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.UiUtil;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private static final double MAX_ANIMATION_TIME_MS = 150;

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> listPersons;

    private final Logic logic;
    private boolean animated;
    private ArrayList<Person> currentPersonList = new ArrayList<>();

    public PersonListPanel(ObservableList<Person> personList, Logic logic, boolean animated) {
        super(FXML);
        setConnections(personList);
        this.logic = logic;
        this.animated = animated;

        registerAsAnEventHandler(this);
    }

    /**
     * Bind the connection between the filtered person list and the list view
     * @param personList to be bind with
     */
    private void setConnections(ObservableList<Person> personList) {
        // Listener for upcoming changes
        personList.addListener((ListChangeListener<Person>) changes -> {
            ObservableList<Person> newList = FXCollections.observableArrayList();
            while (changes.next()) {
                for (int i = changes.getFrom(); i < changes.getTo(); i++) {
                    newList.add(changes.getList().get(i));
                }
            }
            handleListChange(newList);
        });

        // Init mapped list
        handleListChange(personList);

        listPersons.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Fade out and prepare for list changes
     * @param personList of the latest filtered list to be shown
     */
    private void handleListChange(ObservableList<Person> personList) {
        ObservableList<PersonCard> mappedList = FXCollections.observableArrayList();
        ArrayList<Person> newList = new ArrayList<>(personList);

        int index = 1;
        for (Person person : personList) {
            mappedList.add(new PersonCard(person, index));
            index++;
        }

        if (animated) {
            Animation fadeOut = UiUtil.fadeNode(listPersons, false, 1, MAX_ANIMATION_TIME_MS,
                e -> postHandleListChange(personList, mappedList));
            fadeOut.play();
        } else {
            postHandleListChange(personList, mappedList);
        }

        currentPersonList = newList;
    }

    /**
     * Update person card list when new contents / updated contents came in
     * @param personList of the latest filtered list to be shown
     * @param mappedList as {@code PersonCard} of the latest filtered list to be shown
     */
    private void postHandleListChange(ObservableList<Person> personList, ObservableList<PersonCard> mappedList) {
        PersonCard selectedCard = listPersons.getSelectionModel().getSelectedItem();
        Person selectedPerson = (selectedCard == null) ? null : selectedCard.getPerson();

        // Set item
        listPersons.setItems(null);
        listPersons.setItems(mappedList);

        // Select previously selected person
        int selectedIndex = personList.indexOf(selectedPerson);
        if (selectedIndex != -1) {
            listPersons.getSelectionModel().select(selectedIndex);
        } else {
            listPersons.getSelectionModel().clearSelection();
        }

        // Disable focus if list is empty
        listPersons.setFocusTraversable(mappedList.size() != 0);

        // Show list
        listPersons.setOpacity(1);
        if (animated) {
            mappedList.forEach(PersonCard::play);
        } else {
            mappedList.forEach(PersonCard::show);
        }
    }

    private void setEventHandlerForSelectionChangeEvent() {
        listPersons.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    logger.fine("Selection in person list panel changed to : '" + selected + "'");
                    logic.setSelectedPerson(selected == null ? null : selected.getPerson());
                    raise(new PersonPanelSelectionChangedEvent(selected));
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            listPersons.scrollTo(index);
            listPersons.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
