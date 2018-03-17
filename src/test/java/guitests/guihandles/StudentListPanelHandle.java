package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.student.Student;
import seedu.address.ui.StudentCard;

/**
 * Provides a handle for {@code StudentListPanel} containing the list of {@code StudentCard}.
 */
public class StudentListPanelHandle extends NodeHandle<ListView<StudentCard>> {
    public static final String STUDENT_LIST_VIEW_ID = "#studentListView";

    private Optional<StudentCard> lastRememberedSelectedStudentCard;

    public StudentListPanelHandle(ListView<StudentCard> studentListPanelNode) {
        super(studentListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code StudentCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public StudentCardHandle getHandleToSelectedCard() {
        List<StudentCard> studentList = getRootNode().getSelectionModel().getSelectedItems();

        if (studentList.size() != 1) {
            throw new AssertionError("Student list size expected 1.");
        }

        return new StudentCardHandle(studentList.get(0).getRoot());
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
        List<StudentCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the student.
     */
    public void navigateToCard(Student student) {
        List<StudentCard> cards = getRootNode().getItems();
        Optional<StudentCard> matchingCard = cards.stream().filter(card -> card.student.equals(student)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Student does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the student card handle of a student associated with the {@code index} in the list.
     */
    public StudentCardHandle getStudentCardHandle(int index) {
        return getStudentCardHandle(getRootNode().getItems().get(index).student);
    }

    /**
     * Returns the {@code StudentCardHandle} of the specified {@code student} in the list.
     */
    public StudentCardHandle getStudentCardHandle(Student student) {
        Optional<StudentCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.student.equals(student))
                .map(card -> new StudentCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Student does not exist."));
    }

    /**
     * Selects the {@code StudentCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code StudentCard} in the list.
     */
    public void rememberSelectedStudentCard() {
        List<StudentCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedStudentCard = Optional.empty();
        } else {
            lastRememberedSelectedStudentCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code StudentCard} is different from the value remembered by the most recent
     * {@code rememberSelectedStudentCard()} call.
     */
    public boolean isSelectedStudentCardChanged() {
        List<StudentCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedStudentCard.isPresent();
        } else {
            return !lastRememberedSelectedStudentCard.isPresent()
                    || !lastRememberedSelectedStudentCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
