package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.patient.Patient;
import seedu.address.ui.PatientCard;

/**
 * Provides a handle for {@code PatientListPanel} containing the list of {@code PatientCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<PatientCard>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    private Optional<PatientCard> lastRememberedSelectedPersonCard;

    public PersonListPanelHandle(ListView<PatientCard> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PatientCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PatientCardHandle getHandleToSelectedCard() {
        List<PatientCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() != 1) {
            throw new AssertionError("Patient list size expected 1.");
        }

        return new PatientCardHandle(personList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<PatientCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the patient.
     */
    public void navigateToCard(Patient patient) {
        List<PatientCard> cards = getRootNode().getItems();
        Optional<PatientCard> matchingCard = cards.stream().filter(card -> card.patient.equals(patient)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Patient does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the patient card handle of a patient associated with the {@code index} in the list.
     */
    public PatientCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(getRootNode().getItems().get(index).patient);
    }

    /**
     * Returns the {@code PatientCardHandle} of the specified {@code patient} in the list.
     */
    public PatientCardHandle getPersonCardHandle(Patient patient) {
        Optional<PatientCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.patient.equals(patient))
                .map(card -> new PatientCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Patient does not exist."));
    }

    /**
     * Selects the {@code PatientCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PatientCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<PatientCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PatientCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<PatientCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
